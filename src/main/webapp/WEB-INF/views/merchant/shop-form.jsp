<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Shop Edit</title>
    <link rel="stylesheet" href="/css/shop.css">
</head>
<body>

<form action="/foods/save" method="post">

    <input type="hidden" name="id" value="${shop.id}">

    <label>Tên shop</label>
    <input type="text" name="name" value="${shop.name}" required>

    <label>Địa chỉ</label>
    <input type="text"  name="address" value="${shop.address}" required>

    <label>Hình ảnh</label>
    <input type="file" name="image" value="${shop.image}" accept="image/*">

    <div class="form-actions">
        <button type="submit">💾 Lưu</button>
        <a class="btn-back" href="/shops/${shop.id}">⬅ Quay lại</a>
    </div>

</form>

</body>
</html>