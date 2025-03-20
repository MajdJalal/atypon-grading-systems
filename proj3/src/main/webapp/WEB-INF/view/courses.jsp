<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Courses</title>
</head>
<body>
    <h1>Courses</h1>
    <ul>
        <c:forEach items="${courses}" var="course">
            <li>${course.courseName} - ${course.credits} credits</li>
        </c:forEach>
    </ul>
</body>
</html>