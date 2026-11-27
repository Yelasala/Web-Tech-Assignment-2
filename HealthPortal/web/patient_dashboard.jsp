<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Patient Dashboard | AU Health Portal</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f6f9; margin: 0; padding: 20px; }
        .topbar { background: #002b49; color: #fff; padding: 15px 25px; display: flex; justify-content: space-between; align-items: center; border-radius: 6px; }
        .topbar a { color: #c59b27; text-decoration: none; font-weight: bold; }
        .grid { display: grid; grid-template-columns: 1fr 2fr; gap: 20px; margin-top: 20px; }
        .box { background: #fff; padding: 20px; border-radius: 6px; box-shadow: 0 2px 8px rgba(0,0,0,0.08); }
        input, select, textarea { width: 100%; padding: 8px; margin: 6px 0 14px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { background: #002b49; color: #fff; border: none; padding: 10px 16px; border-radius: 4px; cursor: pointer; font-weight: bold; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border-bottom: 1px solid #e2e8f0; padding: 10px; text-align: left; }
        th { background: #f8fafc; color: #002b49; }
        .badge { padding: 4px 10px; border-radius: 12px; font-size: 12px; font-weight: bold; }
        .pending { background-color: #fff3cd; color: #856404; }
        .confirmed { background-color: #d4edda; color: #155724; }
        .completed { background-color: #cce5ff; color: #004085; }
    </style>
</head>
<body>
<div class="topbar">
    <div>Welcome, <strong><c:out value="${sessionScope.authenticatedUser.fullName}"/></strong> (Patient)</div>
    <a href="LoginServlet">Logout</a>
</div>

<div class="grid">
    <div class="box">
        <h3>Book Medical Appointment</h3>
        <form action="AppointmentServlet" method="POST">
            <input type="hidden" name="action" value="book">
            <label>Department</label>
            <select name="department" required>
                <option value="General Medicine">General Medicine</option>
                <option value="Dental Clinic">Dental Clinic</option>
                <option value="Optometry">Optometry</option>
                <option value="Counseling">Counseling</option>
            </select>
            <label>Appointment Date</label>
            <input type="date" name="appointmentDate" required>
            <label>Symptoms</label>
            <textarea name="symptoms" rows="3" required></textarea>
            <button type="submit">Schedule Appointment</button>
        </form>
    </div>

    <div class="box">
        <h3>My Consultation History</h3>
        <table>
            <thead>
                <tr>
                    <th>ID</th>
                    <th>Department</th>
                    <th>Date</th>
                    <th>Symptoms</th>
                    <th>Status</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach var="app" items="${appointments}">
                    <tr>
                        <td>#<c:out value="${app.appointmentId}"/></td>
                        <td><c:out value="${app.department}"/></td>
                        <td><c:out value="${app.appointmentDate}"/></td>
                        <td><c:out value="${app.symptoms}"/></td>
                        <td>
                            <c:choose>
                                <c:when test="${app.status == 'CONFIRMED'}">
                                    <span class="badge confirmed">CONFIRMED</span>
                                </c:when>
                                <c:when test="${app.status == 'COMPLETED'}">
                                    <span class="badge completed">COMPLETED</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge pending">PENDING</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>
</body>
</html>