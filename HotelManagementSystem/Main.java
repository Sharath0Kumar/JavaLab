package HotelManagementSystem;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        HospitalService service = new HospitalService();

        while (true) {
            System.out.println("\n===== HOSPITAL MANAGEMENT SYSTEM =====");
            System.out.println("1. Register Patient");
            System.out.println("2. Register Doctor");
            System.out.println("3. Schedule Appointment");
            System.out.println("4. Reschedule Appointment");
            System.out.println("5. Cancel Appointment");
            System.out.println("6. View Appointments by Patient");
            System.out.println("7. View Appointments by Doctor");
            System.out.println("8. View Activity Log");
            System.out.println("9. Exit");
            System.out.print("Enter your choice: ");

            int choice;

            try {
                choice = Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Enter a valid menu number.");
                continue;
            }

            try {
                switch (choice) {

                    case 1:
                        System.out.print("Patient name: ");
                        String name = sc.nextLine();

                        System.out.print("DOB (YYYY-MM-DD): ");
                        String dob = sc.nextLine();

                        System.out.print("Phone: ");
                        String phone = sc.nextLine();

                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        service.registerPatient(
                                name, dob, phone, email);
                        break;

                    case 2:
                        System.out.print("Doctor name: ");
                        String doctorName = sc.nextLine();

                        System.out.print("Specialization: ");
                        String specialization = sc.nextLine();

                        System.out.print("License number: ");
                        String license = sc.nextLine();

                        service.registerDoctor(
                                doctorName, specialization, license);
                        break;

                    case 3:
                        System.out.print("Patient ID: ");
                        int patientId =
                                Integer.parseInt(sc.nextLine());

                        System.out.print("Doctor ID: ");
                        int doctorId =
                                Integer.parseInt(sc.nextLine());

                        System.out.print(
                                "Date/time (YYYY-MM-DD HH:MM:SS): ");
                        String dateTime = sc.nextLine();

                        service.scheduleAppointment(
                                patientId, doctorId, dateTime);
                        break;

                    case 4:
                        System.out.print("Appointment ID: ");
                        int appointmentId =
                                Integer.parseInt(sc.nextLine());

                        System.out.print(
                                "New date/time (YYYY-MM-DD HH:MM:SS): ");
                        String newDateTime = sc.nextLine();

                        service.rescheduleAppointment(
                                appointmentId, newDateTime);
                        break;

                    case 5:
                        System.out.print("Appointment ID: ");
                        int cancelId =
                                Integer.parseInt(sc.nextLine());

                        service.cancelAppointment(cancelId);
                        break;

                    case 6:
                        System.out.print("Patient ID: ");
                        int pId = Integer.parseInt(sc.nextLine());

                        service.viewByPatient(pId);
                        break;

                    case 7:
                        System.out.print("Doctor ID: ");
                        int dId = Integer.parseInt(sc.nextLine());

                        service.viewByDoctor(dId);
                        break;

                    case 8:
                        service.showLogs();
                        break;

                    case 9:
                        System.out.println("Exiting application.");
                        sc.close();
                        return;

                    default:
                        System.out.println("Invalid choice.");
                }

            } catch (SQLException | IllegalArgumentException e) {
                System.out.println(
                        "Operation failed: " + e.getMessage());
            }
        }
    }
}
