<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
<meta charset="utf-8">
<title> Chi tiết shop </title>
</head>
<body>
    <h2> Chi tiết cửa hàng</h2>


    <p>ID: ${shop.id}</p>
    <p>Tên cửa hàng: ${shop.name}</p>
    <p>Địa chỉ: ${shop.address} </p>
    <p>
        <img src="${pageContext.request.contextPath}${shop.image}" width="200">
    </p>

    <a class="btn-add" href="/shops/edit/${shop.id}">UPDATE Của Hàng</a>
</body>
</html>