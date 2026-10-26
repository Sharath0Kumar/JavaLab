package HotelManagementSystem;

public class Appointment {
    private int appointmentId;
    private int patientId;
    private int doctorId;
    private String appointmentDatetime;
    private String status;

    public Appointment(int appointmentId, int patientId,
                       int doctorId, String appointmentDatetime,
                       String status) {
        this.appointmentId = appointmentId;
        this.patientId = patientId;
        this.doctorId = doctorId;
        this.appointmentDatetime = appointmentDatetime;
        this.status = status;
    }

    public int getAppointmentId() {
        return appointmentId;
    }

    public int getPatientId() {
        return patientId;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getAppointmentDatetime() {
        return appointmentDatetime;
    }

    public String getStatus() {
        return status;
    }
}
