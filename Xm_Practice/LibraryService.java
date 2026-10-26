package Xm_Practice;

import java.sql.*;
import java.time.LocalDate;

public class LibraryService {

    // Record each operation in the Activity_Log table.
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

    // Add a new book.
    public void addBook(String isbn, String title,
                        String author, int copies)
            throws SQLException {

        if (copies <= 0) {
            System.out.println("Copies must be positive.");
            return;
        }

        String sql = "INSERT INTO Book " +
                "(ISBN, Title, Author, Total_Copies, Available_Copies) " +
                "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                int bookId;

                try (PreparedStatement ps =
                             con.prepareStatement(sql,
                                     Statement.RETURN_GENERATED_KEYS)) {

                    ps.setString(1, isbn);
                    ps.setString(2, title);
                    ps.setString(3, author);
                    ps.setInt(4, copies);
                    ps.setInt(5, copies);
                    ps.executeUpdate();

                    try (ResultSet rs = ps.getGeneratedKeys()) {
                        rs.next();
                        bookId = rs.getInt(1);
                    }
                }

                log(con, "ADD_BOOK",
                        "Added book ID " + bookId + ": " + title);

                con.commit();
                System.out.println("Book added successfully!");

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    // Search books by title.
    public void searchBook(String title) throws SQLException {

        String sql = "SELECT * FROM Book WHERE Title LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + title + "%");

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    System.out.println(
                            "ID: " + rs.getInt("Book_ID") +
                                    " | Title: " + rs.getString("Title") +
                                    " | Author: " + rs.getString("Author") +
                                    " | Total: " + rs.getInt("Total_Copies") +
                                    " | Available: " +
                                    rs.getInt("Available_Copies")
                    );
                }

                if (!found) {
                    System.out.println("No books found.");
                }
            }
        }
    }

    // Modify a book's title and author.
    public void modifyBook(int id, String title,
                           String author) throws SQLException {

        String sql = "UPDATE Book SET Title = ?, Author = ? " +
                "WHERE Book_ID = ?";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                int rows;

                try (PreparedStatement ps =
                             con.prepareStatement(sql)) {
                    ps.setString(1, title);
                    ps.setString(2, author);
                    ps.setInt(3, id);
                    rows = ps.executeUpdate();
                }

                if (rows == 0) {
                    con.rollback();
                    System.out.println("Book ID not found.");
                    return;
                }

                log(con, "MODIFY_BOOK",
                        "Modified book ID " + id);

                con.commit();
                System.out.println("Book updated successfully!");

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    // Add a member.
    public void addMember(String name, String email,
                          String phone) throws SQLException {

        String sql = "INSERT INTO Member " +
                "(Member_Name, Email, Phone) VALUES (?, ?, ?)";

        try (Connection con = DBConnection.getConnection()) {
            con.setAutoCommit(false);

            try {
                try (PreparedStatement ps =
                             con.prepareStatement(sql)) {
                    ps.setString(1, name);
                    ps.setString(2, email);
                    ps.setString(3, phone);
                    ps.executeUpdate();
                }

                log(con, "ADD_MEMBER", "Added member: " + name);
                con.commit();
                System.out.println("Member added successfully!");

            } catch (SQLException e) {
                con.rollback();
                throw e;
            }
        }
    }

    // Search members by name.
    public void searchMember(String name) throws SQLException {

        String sql = "SELECT * FROM Member " +
                "WHERE Member_Name LIKE ?";

        try (Connection con = DBConnection.getConnection();
             PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, "%" + name + "%");

            try (ResultSet rs = ps.executeQuery()) {
                boolean found = false;

                while (rs.next()) {
                    found = true;
                    System.out.println(
                            "ID: " + rs.getInt("Member_ID") +
                                    " | Name: " + rs.getString("Member_Name") +
                                    " | Email: " + rs.getString("Email") +
                                    " | Phone: " + rs.getString("Phone")
                    );
                }

                if (!found) {
                    System.out.println("No members found.");
                }
            }
        }
    }
