<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang chủ Mạnh Mall</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/home.css'/>">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body style="background-color: #f5f5f5;"> <jsp:include page="/WEB-INF/views/layout/Header.jsp" />

<div class="container">
    <h2 style="margin: 20px 0; color: #ee4d2d; border-bottom: 2px solid #ee4d2d; display: inline-block; padding-bottom: 5px;">
        GỢI Ý HÔM NAY
    </h2>

    <div class="product-grid">
        <c:forEach items="${foods}" var="f">
            <a href="${pageContext.request.contextPath}/foods/detail/${f.id}" class="product-link">
                <div class="product-card">
                    <img src="${pageContext.request.contextPath}${f.image}" alt="${f.name}">

                    <div class="product-info">
                        <p class="product-name">${f.name}</p>
                        <p class="product-price">
                            <fmt:formatNumber value="${f.price}" type="number" groupingUsed="true"/> đ
                        </p>
                        <p class="product-description">${f.description}</p>
                    </div>
                </div>
            </a>
        </c:forEach>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/Footer.jsp" />

<c:if test="${not empty message}">
    <script>
        const status = "${message}";
        if (status === "pending_shipper") {
            Swal.fire({
                title: 'Đăng ký thành công!',
                text: 'Yêu cầu trở thành shipper của bạn đã được gửi.',
                icon: 'success',
                confirmButtonColor: '#0d6efd',
                confirmButtonText: 'Đồng ý'
            });
        }
    </script>
</c:if>

</body>
</html>