CREATE DATABASE HospitalDB;
USE HospitalDB;

CREATE TABLE Patient (
                         Patient_ID INT PRIMARY KEY AUTO_INCREMENT,
                         Patient_Name VARCHAR(100) NOT NULL,
                         DOB DATE,
                         Phone VARCHAR(15),
                         Email VARCHAR(100)
);

CREATE TABLE Doctor (
                        Doctor_ID INT PRIMARY KEY AUTO_INCREMENT,
                        Doctor_Name VARCHAR(100) NOT NULL,
                        Specialization VARCHAR(100),
                        License_No VARCHAR(50) UNIQUE
);

CREATE TABLE Appointment (
                             Appointment_ID INT PRIMARY KEY AUTO_INCREMENT,
                             Patient_ID INT NOT NULL,
                             Doctor_ID INT NOT NULL,
                             Appointment_Datetime DATETIME NOT NULL,
                             Status VARCHAR(20) DEFAULT 'Scheduled',
                             FOREIGN KEY (Patient_ID) REFERENCES Patient(Patient_ID),
                             FOREIGN KEY (Doctor_ID) REFERENCES Doctor(Doctor_ID)
);

CREATE TABLE Activity_Log (
                              Log_ID INT PRIMARY KEY AUTO_INCREMENT,
                              Activity VARCHAR(100) NOT NULL,
                              Details VARCHAR(255),
                              Activity_Time TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);