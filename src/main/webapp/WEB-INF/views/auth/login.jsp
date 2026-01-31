<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng nhập</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/home.css'/>">
    <link rel="stylesheet" href="<c:url value='/css/auth.css'/>">
</head>
<body>

<jsp:include page="/WEB-INF/views/layout/Header.jsp" />

<div class="register-container">
    <div class="register-form-box">
        <h3>Đăng nhập</h3>

        <form action="${pageContext.request.contextPath}/login" method="post">
            <div class="input-group">
                <input type="text" name="username" placeholder="Tên đăng nhập" required>
            </div>
            <div class="input-group">
                <input type="password" name="password" placeholder="Mật khẩu" required>
            </div>

            <c:if test="${not empty error}">
                <p style="color: red; font-size: 13px; margin-bottom: 10px;">
                    <i class="fas fa-exclamation-circle"></i> ${error}
                </p>
            </c:if>

            <button type="submit" class="btn-register">Đăng nhập</button>
        </form>

        <div class="form-footer">
            Bạn chưa có tài khoản? <a href="${pageContext.request.contextPath}/register">Đăng ký</a>
        </div>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/Footer.jsp" />

</body>
</html>