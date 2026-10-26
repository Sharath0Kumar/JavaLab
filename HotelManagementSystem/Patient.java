package HotelManagementSystem;

public class Patient {
    private int patientId;
    private String patientName;
    private String dob;
    private String phone;
    private String email;

    public Patient(int patientId, String patientName,
                   String dob, String phone, String email) {
        this.patientId = patientId;
        this.patientName = patientName;
        this.dob = dob;
        this.phone = phone;
        this.email = email;
    }

    public int getPatientId() {
        return patientId;
    }

    public String getPatientName() {
        return patientName;
    }

    public String getDob() {
        return dob;
    }

    public String getPhone() {
        return phone;
    }

    public String getEmail() {
        return email;
    }
}
