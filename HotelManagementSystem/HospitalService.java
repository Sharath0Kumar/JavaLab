package HotelManagementSystem;

import java.sql.*;

public class HospitalService {

    // Record each activity in Activity_Log.
    private void log(Connection con, String activity,
                     String details) throws SQLException {

        String sql = "INSERT INTO Activity_Log " +
                "(Activity, Details) VALUES (?, ?)";

        try (PreparedStatement ps = con.prepareStatement(sql)) {
            ps.setString(1, activity);
            ps.setString(2, details);
            ps.executeUpdate();
        }
    }

    // Register a patient.
    public void registerPatient(String name, String dob,
                                String phone, String email)
            throws SQLException {

        String sql = "INSERT INTO Patient " +
                "(Patient_Name, DOB, Phone, Email) VALUES (?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, name);
                    ps.setDate(2, Date.valueOf(dob));
                    ps.setString(3, phone);
                    ps.setString(4, email);
                    ps.executeUpdate();
                }

                log(con, "REGISTER_PATIENT",
                        "Registered patient: " + name);

                con.commit();
                System.out.println("Patient registered successfully!");

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    // Register a doctor.
    public void registerDoctor(String name, String specialization,
                               String licenseNo)
            throws SQLException {

        String sql = "INSERT INTO Doctor " +
                "(Doctor_Name, Specialization, License_No) " +
                "VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setString(1, name);
                    ps.setString(2, specialization);
                    ps.setString(3, licenseNo);
                    ps.executeUpdate();
                }

                log(con, "REGISTER_DOCTOR",
                        "Registered doctor: " + name);

                con.commit();
                System.out.println("Doctor registered successfully!");

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    // Schedule an appointment.
    public void scheduleAppointment(int patientId, int doctorId,
                                    String dateTime)
            throws SQLException {

        String sql = "INSERT INTO Appointment " +
                "(Patient_ID, Doctor_ID, Appointment_Datetime, Status) " +
                "VALUES (?, ?, ?, 'Scheduled')";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, patientId);
                    ps.setInt(2, doctorId);
                    ps.setTimestamp(3, Timestamp.valueOf(dateTime));
                    ps.executeUpdate();
                }

                log(con, "SCHEDULE_APPOINTMENT",
                        "Patient ID: " + patientId +
                                ", Doctor ID: " + doctorId +
                                ", Date: " + dateTime);

                con.commit();
                System.out.println("Appointment scheduled successfully!");

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    // Reschedule an existing appointment.
    public void rescheduleAppointment(int appointmentId,
                                      String newDateTime)
            throws SQLException {

        String sql = "UPDATE Appointment " +
                "SET Appointment_Datetime = ? " +
                "WHERE Appointment_ID = ? AND Status = 'Scheduled'";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                int rows;

                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setTimestamp(1, Timestamp.valueOf(newDateTime));
                    ps.setInt(2, appointmentId);
                    rows = ps.executeUpdate();
                }

                if (rows == 0) {
                    con.rollback();
                    System.out.println(
                            "Scheduled appointment not found.");
                    return;
                }

                log(con, "RESCHEDULE_APPOINTMENT",
                        "Rescheduled appointment ID: " + appointmentId +
                                " to " + newDateTime);

                con.commit();
                System.out.println("Appointment rescheduled successfully!");

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    // Cancel an appointment.
    public void cancelAppointment(int appointmentId)
            throws SQLException {

        String sql = "UPDATE Appointment SET Status = 'Cancelled' " +
                "WHERE Appointment_ID = ? AND Status = 'Scheduled'";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                int rows;

                try (PreparedStatement ps = con.prepareStatement(sql)) {
                    ps.setInt(1, appointmentId);
                    rows = ps.executeUpdate();
                }

                if (rows == 0) {
                    con.rollback();
                    System.out.println(
                            "Scheduled appointment not found.");
                    return;
                }

                log(con, "CANCEL_APPOINTMENT",
                        "Cancelled appointment ID: " + appointmentId);

                con.commit();
                System.out.println("Appointment cancelled successfully!");

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    // Retrieve appointments for a patient using JOIN.
    public void viewByPatient(int patientId) throws SQLException {

        String sql = "SELECT a.Appointment_ID, p.Patient_Name, " +
                "d.Doctor_Name, d.Specialization, " +
                "a.Appointment_Datetime, a.Status " +
                "FROM Appointment a " +
                "JOIN Patient p ON a.Patient_ID = p.Patient_ID " +
                "JOIN Doctor d ON a.Doctor_ID = d.Doctor_ID " +
                "WHERE a.Patient_ID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, patientId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    displayAppointment(rs);
                }

                if (!found) {
                    System.out.println("No appointments found.");
                }
            }

            log(con, "VIEW_PATIENT_APPOINTMENTS",
                    "Viewed appointments for patient ID: " + patientId);
        }
    }

    // Retrieve appointments for a doctor using JOIN.
    public void viewByDoctor(int doctorId) throws SQLException {

        String sql = "SELECT a.Appointment_ID, p.Patient_Name, " +
                "d.Doctor_Name, d.Specialization, " +
                "a.Appointment_Datetime, a.Status " +
                "FROM Appointment a " +
                "JOIN Patient p ON a.Patient_ID = p.Patient_ID " +
                "JOIN Doctor d ON a.Doctor_ID = d.Doctor_ID " +
                "WHERE a.Doctor_ID = ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, doctorId);

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    displayAppointment(rs);
                }

                if (!found) {
                    System.out.println("No appointments found.");
                }
            }

            log(con, "VIEW_DOCTOR_APPOINTMENTS",
                    "Viewed appointments for doctor ID: " + doctorId);
        }
    }

    // Display appointment details.
    private void displayAppointment(ResultSet rs)
            throws SQLException {

        System.out.println("\nAppointment ID: " +
                rs.getInt("Appointment_ID"));
        System.out.println("Patient: " +
                rs.getString("Patient_Name"));
        System.out.println("Doctor: " +
                rs.getString("Doctor_Name"));
        System.out.println("Specialization: " +
                rs.getString("Specialization"));
        System.out.println("Date and Time: " +
                rs.getTimestamp("Appointment_Datetime"));
        System.out.println("Status: " +
                rs.getString("Status"));
        System.out.println("-----------------------------");
    }

    // Display the activity log.
    public void showLogs() throws SQLException {

        String sql = "SELECT * FROM Activity_Log " +
                "ORDER BY Log_ID DESC";

        try (Connection con = DBConnection.getConnection();
             Statement st = con.createStatement();
             ResultSet rs = st.executeQuery(sql)) {

            while (rs.next()) {
                System.out.println(
                        rs.getInt("Log_ID") + " | " +
                                rs.getString("Activity") + " | " +
                                rs.getString("Details") + " | " +
                                rs.getTimestamp("Activity_Time")
                );
            }
        }
    }
}
