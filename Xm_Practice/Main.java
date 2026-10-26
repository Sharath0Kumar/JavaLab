package Xm_Practice;

import java.sql.SQLException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        LibraryService service = new LibraryService();

        while (true) {
            System.out.println("\n===== LIBRARY MANAGEMENT SYSTEM =====");
            System.out.println("1. Add Book");
            System.out.println("2. Modify Book");
            System.out.println("3. Search Book");
            System.out.println("4. Add Member");
            System.out.println("5. Search Member");
            System.out.println("6. Issue Book");
            System.out.println("7. Return Book");
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
                        System.out.print("ISBN: ");
                        String isbn = sc.nextLine();

                        System.out.print("Book title: ");
                        String title = sc.nextLine();

                        System.out.print("Author: ");
                        String author = sc.nextLine();

                        System.out.print("Total copies: ");
                        int copies = Integer.parseInt(sc.nextLine());

                        service.addBook(isbn, title, author, copies);
                        break;

                    case 2:
                        System.out.print("Book ID: ");
                        int id = Integer.parseInt(sc.nextLine());

                        System.out.print("New title: ");
                        String newTitle = sc.nextLine();

                        System.out.print("New author: ");
                        String newAuthor = sc.nextLine();

                        service.modifyBook(id, newTitle, newAuthor);
                        break;

                    case 3:
                        System.out.print("Enter title to search: ");
                        service.searchBook(sc.nextLine());
                        break;

                    case 4:
                        System.out.print("Member name: ");
                        String name = sc.nextLine();

                        System.out.print("Email: ");
                        String email = sc.nextLine();

                        System.out.print("Phone: ");
                        String phone = sc.nextLine();

                        service.addMember(name, email, phone);
                        break;

                    case 5:
                        System.out.print("Enter member name: ");
                        service.searchMember(sc.nextLine());
                        break;

                    case 6:
                        System.out.print("Book ID: ");
                        int bId = Integer.parseInt(sc.nextLine());

                        System.out.print("Member ID: ");
                        int mId = Integer.parseInt(sc.nextLine());

                        service.issueBook(bId, mId);
                        break;

                    case 7:
                        System.out.print("Borrowing ID: ");
                        int borrowId = Integer.parseInt(sc.nextLine());

                        service.returnBook(borrowId);
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

            } catch (SQLException | NumberFormatException e) {
                System.out.println("Operation failed: " + e.getMessage());
            }
        }
    }
}
