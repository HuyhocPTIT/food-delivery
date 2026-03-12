<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tổng quan Shipper</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/home.css' />">
    <link rel="stylesheet" href="<c:url value='/css/shipper.css' />">
</head>
<body>
<jsp:include page="/WEB-INF/views/layout/Header.jsp" />

<div class="card shadow-sm border-0">
    <div class="card-header bg-primary text-white text-center py-3">
        <h4 class="mb-0"><i class="fas fa-motorcycle me-2"></i> ĐĂNG KÝ GIAO HÀNG MẠNH MALL</h4>
    </div>
    <div class="card-body p-4 text-center">
        <p>Bạn muốn kiếm thêm thu nhập?</p>
        <form action="${pageContext.request.contextPath}/shipper/register" method="post">
            <button type="submit" class="btn btn-primary btn-lg px-5 shadow">
                GỬI YÊU CẦU NGAY
            </button>
        </form>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/Footer.jsp" />
</body>
</html>