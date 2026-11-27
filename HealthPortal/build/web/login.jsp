<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@taglib prefix="c" uri="jakarta.tags.core"%>
<!DOCTYPE html>
<html>
<head>
    <title>Ajman University | Health Portal Login</title>
    <style>
        body { font-family: Arial, sans-serif; background: #f4f6f9; display: flex; justify-content: center; padding-top: 60px; }
        .card { background: #fff; width: 380px; padding: 30px; border-radius: 6px; box-shadow: 0 3px 10px rgba(0,0,0,0.1); border-top: 5px solid #002b49; }
        h2 { color: #002b49; margin-top: 0; }
        input { width: 100%; padding: 10px; margin: 8px 0 16px; border: 1px solid #ccc; border-radius: 4px; box-sizing: border-box; }
        button { width: 100%; background: #002b49; color: #fff; border: none; padding: 12px; font-weight: bold; border-radius: 4px; cursor: pointer; }
        .err { color: #b91c1c; background: #fee2e2; padding: 8px; border-radius: 4px; margin-bottom: 12px; }
        .hint { font-size: 12px; color: #555; margin-top: 15px; background: #f8fafc; padding: 10px; border-radius: 4px; }
    </style>
</head>
<body>
<div class="card">
    <h2>AU Health Services Portal</h2>
    <c:if test="${not empty error}">
        <div class="err"><c:out value="${error}"/></div>
    </c:if>
    <form action="LoginServlet" method="POST">
        <label>Username</label>
        <input type="text" name="username" required>
        <label>Password</label>
        <input type="password" name="password" required>
        <button type="submit">Sign In</button>
    </form>
    <div class="hint">
        <strong>Test Accounts (Password: pass123):</strong><br>
        Patient: <code>student1</code> | Staff: <code>doctor1</code>
    </div>
</div>
</body>
</html>