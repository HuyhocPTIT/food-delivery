<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Quản lý món ăn</title>
    <link rel="stylesheet" href="/css/shop.css">
</head>
<body>

<h2>🍔 DANH SÁCH MÓN ĂN</h2>


<table>
    <tr>
        <th>ID</th>
        <th>Tên shop</th>
        <th>address</th>
        <th>Hình ảnh</th>
        <th>Thao tác</th>


    </tr>

    <c:forEach items="${shops}" var="shop">
        <tr style="cursor:pointer"
            onclick="window.location='${pageContext.request.contextPath}/shops/${shop.id}'">
            <td>${shop.id}</td>
            <td>
                <a href="${pageContext.request.contextPath}/shops/${shop.id}" >
                        ${shop.name}
                </a>
            <td>${shop.address}</td>
            <td>
            <img src="${pageContext.request.contextPath}${shop.image}" width="80">
            </td>
            <td>
                <a class="btn-edit" href="/shops/edit/${shop.id}">✏️</a>
                <a class="btn-delete"
                   href="/shops/delete/${shop.id}"
                   onclick="return confirm('Xóa shop này?')">🗑</a>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>