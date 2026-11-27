<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Staff Medical Portal | AU Health Portal</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f6f9; margin: 0; padding: 20px; }
        .topbar { background: #002b49; color: #fff; padding: 15px 25px; display: flex; justify-content: space-between; align-items: center; border-radius: 6px; }
        .topbar a { color: #c59b27; text-decoration: none; font-weight: bold; }
        .box { background: #fff; padding: 20px; border-radius: 6px; box-shadow: 0 2px 8px rgba(0,0,0,0.08); margin-top: 20px; }
        table { width: 100%; border-collapse: collapse; margin-top: 10px; }
        th, td { border-bottom: 1px solid #e2e8f0; padding: 10px; text-align: left; }
        th { background: #f8fafc; color: #002b49; }
        .badge { padding: 4px 10px; border-radius: 12px; font-size: 12px; font-weight: bold; }
        .pending { background-color: #fff3cd; color: #856404; }
        .confirmed { background-color: #d4edda; color: #155724; }
        .completed { background-color: #cce5ff; color: #004085; }
        select, button { padding: 6px 10px; border-radius: 4px; }
        button { background: #002b49; color: #fff; border: none; cursor: pointer; }
    </style>
</head>
<body>
<div class="topbar">
    <div>Medical Staff Portal — <strong><c:out value="${sessionScope.authenticatedUser.fullName}"/></strong></div>
    <a href="LoginServlet">Logout</a>
</div>

<div class="box">
    <h3>Master Patient Appointment Queue</h3>
    <table>
        <thead>
            <tr>
                <th>ID</th>
                <th>Patient</th>
                <th>Department</th>
                <th>Date</th>
                <th>Symptoms</th>
                <th>Current Status</th>
                <th>Update Action</th>
            </tr>
        </thead>
        <tbody>
            <c:forEach var="app" items="${appointments}">
                <tr>
                    <td>#<c:out value="${app.appointmentId}"/></td>
                    <td><c:out value="${app.patientUsername}"/></td>
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
                    <td>
                        <form action="AppointmentServlet" method="POST" style="display:flex; gap:6px;">
                            <input type="hidden" name="action" value="updateStatus">
                            <input type="hidden" name="appointmentId" value="${app.appointmentId}">
                            <select name="status">
                                <option value="PENDING" ${app.status == 'PENDING' ? 'selected' : ''}>PENDING</option>
                                <option value="CONFIRMED" ${app.status == 'CONFIRMED' ? 'selected' : ''}>CONFIRMED</option>
                                <option value="COMPLETED" ${app.status == 'COMPLETED' ? 'selected' : ''}>COMPLETED</option>
                            </select>
                            <button type="submit">Save</button>
                        </form>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>