package HotelManagementSystem;

public class Doctor {
    private int doctorId;
    private String doctorName;
    private String specialization;
    private String licenseNo;

    public Doctor(int doctorId, String doctorName,
                  String specialization, String licenseNo) {
        this.doctorId = doctorId;
        this.doctorName = doctorName;
        this.specialization = specialization;
        this.licenseNo = licenseNo;
    }

    public int getDoctorId() {
        return doctorId;
    }

    public String getDoctorName() {
        return doctorName;
    }

    public String getSpecialization() {
        return specialization;
    }

    public String getLicenseNo() {
        return licenseNo;
    }
}
