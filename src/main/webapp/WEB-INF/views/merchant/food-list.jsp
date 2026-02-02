<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>Quản lý món ăn</title>
    <link rel="stylesheet" href="/css/food.css">
</head>
<body>

<h2>🍔 DANH SÁCH MÓN ĂN</h2>

<a class="btn-add" href="/foods/create">➕ Thêm món</a>

<table>
    <tr>
        <th>ID</th>
        <th>Tên món</th>
        <th>Giá</th>
        <th>Hình ảnh</th>
        <th>Mô tả</th>
        <th>Shop</th>
        <th>Danh mục</th>
        <th>Hành động</th>
    </tr>

    <c:forEach items="${foods}" var="food">
        <tr>
            <td>${food.id}</td>
            <td>${food.name}</td>
            <td>${food.price} đ</td>
            <td>
                <img src="${food.image}" width="80">
            </td>
            <td>${food.description}</td>
            <td>${food.shop.name}</td>
            <td>${food.category.name}</td>
            <td>
                <a class="btn-edit" href="/foods/edit/${food.id}">✏️</a>
                <a class="btn-delete"
                   href="/foods/delete/${food.id}"
                   onclick="return confirm('Xóa món này?')">🗑</a>
            </td>
        </tr>
    </c:forEach>

</table>

</body>
</html>
