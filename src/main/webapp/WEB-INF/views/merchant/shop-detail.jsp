<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <meta charset="utf-8">
    <title> Chi tiết shop </title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
</head>
<body>
    <h2> Chi tiết cửa hàng</h2>


    <p>ID: ${shop.id}</p>
    <p>Tên cửa hàng: ${shop.name}</p>
    <p>Địa chỉ: ${shop.address} </p>
    <p>
        <img src="${pageContext.request.contextPath}${shop.image}" width="200">
    </p>

    <%-- Nếu người đang xem chính là chủ quán này --%>
        <c:if test="${sessionScope.currentUser.id == shop.owner.id}">
            <div class="merchant-tools">
                <a href="/shops/edit" class="btn btn-sm btn-outline-secondary">Sửa thông tin quán</a>
                <a href="/shops/foods" class="btn btn-sm btn-primary">Quản lý món ăn</a>
                <a href="/shops/foods/create" class="btn btn-sm btn-success">Thêm món mới</a>
            </div>
        </c:if>
</body>
</html>