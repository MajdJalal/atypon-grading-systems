<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Students</title>
</head>
<body>
    <h1>Students</h1>
    <ul>
        <c:forEach items="${students}" var="student">
            <li>${student.username} - ${student.email}</li>
        </c:forEach>
    </ul>
</body>
</html>