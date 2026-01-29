<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang chủ Mạnh Mall</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/home.css'/>">
</head>
<body>

<jsp:include page="/WEB-INF/views/layout/Header.jsp" />

<main style="min-height: 500px; padding: 20px;">
    <h2>Chào mừng đến với Mạnh Mall!</h2>
</main>

<jsp:include page="/WEB-INF/views/layout/Footer.jsp" />

</body>
</html>