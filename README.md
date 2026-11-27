# Student Health and Medical Services Portal

A simple web application for booking and managing student medical appointments, built with Java 17, NetBeans 25, GlassFish 7.0.1, and Java DB (Derby).

## 1. Download and Open the Project
1. Download the project folder as a **ZIP** file from GitHub and unzip it.
2. Move the unzipped project folder into your **NetBeansProjects** folder.
3. Open **Apache NetBeans 25**, click **File > Open Project**, and select the project folder.
4. Right-click the project, select **Properties > Libraries**, and make sure **Java Platform** is set to **JDK 17** (or **Zulu 17**).
5. Under **Properties > Run**, confirm the server is set to **GlassFish Server 7.0.1**.

## 2. Set Up the Database
1. In NetBeans, open the **Services** tab on the left.
2. Expand **Databases**, right-click **Java DB**, and click **Start Server**.
3. Right-click **Java DB** again and click **Create Database**.
4. Enter the following details and click **OK**:
   * **Database Name:** `health_portal_db`
   * **User Name:** `app`
   * **Password:** `app`
5. Right-click the new `jdbc:derby://localhost:1527/health_portal_db` connection and click **Connect**.
6. Right-click that same connection, click **Execute Command...**, paste the SQL below, and click the green **Run SQL** button:

```sql
CREATE TABLE users (
    user_id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    username VARCHAR(50) UNIQUE NOT NULL,
    password_hash VARCHAR(64) NOT NULL,
    full_name VARCHAR(100) NOT NULL,
    role VARCHAR(20) NOT NULL
);

CREATE TABLE appointments (
    appointment_id INT NOT NULL GENERATED ALWAYS AS IDENTITY PRIMARY KEY,
    patient_username VARCHAR(50) NOT NULL,
    department VARCHAR(100) NOT NULL,
    appointment_date VARCHAR(20) NOT NULL,
    symptoms VARCHAR(500) NOT NULL,
    status VARCHAR(20) DEFAULT 'PENDING',
    FOREIGN KEY (patient_username) REFERENCES users(username)
);

INSERT INTO users (username, password_hash, full_name, role) VALUES 
('student1', '9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c', 'AHMED AL MANSOORI', 'PATIENT'),
('doctor1', '9b8769a4a742959a2d0298c36fb70623f2dfacda8436237df08d8dfd5b37374c', 'DR. SARA KHALID', 'STAFF');

INSERT INTO appointments (patient_username, department, appointment_date, symptoms, status) VALUES 
('student1', 'General Medicine', '2026-11-28', 'Mild fever and headache', 'PENDING');
