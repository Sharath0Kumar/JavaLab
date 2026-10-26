CREATE DATABASE LibraryDB;
USE LibraryDB;

CREATE TABLE Book (
                      Book_ID INT PRIMARY KEY AUTO_INCREMENT,
                      ISBN VARCHAR(20) UNIQUE,
                      Title VARCHAR(100) NOT NULL,
                      Author VARCHAR(100),
                      Total_Copies INT NOT NULL,
                      Available_Copies INT NOT NULL
);

CREATE TABLE Member (
                        Member_ID INT PRIMARY KEY AUTO_INCREMENT,
                        Member_Name VARCHAR(100) NOT NULL,
                        Email VARCHAR(100),
                        Phone VARCHAR(15)
);

CREATE TABLE Borrowing (
                           Borrow_ID INT PRIMARY KEY AUTO_INCREMENT,
                           Book_ID INT NOT NULL,
                           Member_ID INT NOT NULL,
                           Issue_Date DATE NOT NULL,
                           Due_Date DATE NOT NULL,
                           Return_Date DATE DEFAULT NULL,
                           FOREIGN KEY (Book_ID) REFERENCES Book(Book_ID),
                           FOREIGN KEY (Member_ID) REFERENCES Member(Member_ID)
);

CREATE TABLE ActivityLog (
                             Log_ID INT PRIMARY KEY AUTO_INCREMENT,
                             Activity VARCHAR(255) NOT NULL,
                             Activity_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

Verify that the tables were created:
SHOW TABLES;


To see the columns of a table:
DESC Book;
DESC Member;
DESC Borrowing;
DESC ActivityLog;

Insert sample records:
INSERT INTO Book
(ISBN, Title, Author, Total_Copies, Available_Copies)
VALUES
('9780135166307', 'Java Programming', 'James Gosling', 5, 5),
('9780132350884', 'Clean Code', 'Robert Martin', 3, 3);

INSERT INTO Member (Member_Name, Email, Phone)
VALUES
    ('Rahul', 'rahul@gmail.com', '9876543210'),
    ('Priya', 'priya@gmail.com', '9876543211');

Check the records:
SELECT * FROM Book;
SELECT * FROM Member;


