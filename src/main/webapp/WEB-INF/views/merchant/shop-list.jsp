<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Danh sách món ăn</title>
    <link rel="stylesheet" href="/css/shop.css">
    <link rel="stylesheet" href="/css/home.css">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
</head>
<body>

<jsp:include page="/WEB-INF/views/layout/Header.jsp" />
<h2>🍔 DANH SÁCH CỬA HÀNG</h2>


<table>
    <tr>
        <th>Tên shop</th>
        <th>Địa chỉ</th>
        <th>Hình ảnh</th>
    </tr>

    <c:forEach items="${shops}" var="shop">
        <tr style="cursor:pointer"
            onclick="window.location='${pageContext.request.contextPath}/shops/${shop.id}'">
            <td>
                <a href="${pageContext.request.contextPath}/shops/${shop.id}" >
                        ${shop.name}
                </a>
            </td>
            <td>${shop.address}</td>
            <td>
                <img src="${pageContext.request.contextPath}${shop.image}" width="80">
            </td>
        </tr>
    </c:forEach>

</table>
<jsp:include page="/WEB-INF/views/layout/Footer.jsp" />

<c:if test="${not empty message}">
    <script>
        const status = "${message}";
        if (status === "pending") {
            Swal.fire({
                title: 'Đăng ký thành công!',
                text: 'Yêu cầu mở quán của bạn đã được gửi.',
                icon: 'success',
                confirmButtonColor: '#0d6efd',
                confirmButtonText: 'Đồng ý'
            });
        }
    </script>
</c:if>

</body>
</html>