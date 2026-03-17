<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<html>
<head>
    <title>Food Form</title>
    <link rel="stylesheet" href="/css/food.css">
</head>
<body>

<h2>
    <c:if test="${food.id == null}">➕ Thêm món ăn</c:if>
    <c:if test="${food.id != null}">✏️ Sửa món ăn</c:if>
</h2>

<form action="<c:if test="${food.id == null}">/shops/foods/save</c:if><c:if test="${food.id != null}">/shops/foods/update/${food.id}</c:if>" method="post" enctype="multipart/form-data">

    <input type="hidden" name="id" value="${food.id}">

    <label>Tên món</label>
    <input type="text" name="name" value="${food.name}" required>

    <label>Giá</label>
    <input type="number" step="0.01" name="price" value="${food.price}" required>

    <label>Hình ảnh</label>
    <input type="file" name="imageFile" accept="image/*">
    <c:if test="${not empty food.image}">
        <img src="${food.image}" alt="Current Image" style="max-width: 200px;">
    </c:if>

    <label>Mô tả</label>
    <textarea name="description">${food.description}</textarea>
    <label>Danh mục</label>
    <select name="categoryId" required>
        <c:forEach items="${categories}" var="cate">
            <option value="${cate.id}"
                    <c:if test="${food.category != null && food.category.id == cate.id}">
                        selected
                    </c:if>>
                    ${cate.name}
            </option>
        </c:forEach>
    </select>

    <div class="form-actions">
        <button type="submit">💾 Lưu</button>
        <a class="btn-back" href="/shops/foods">⬅ Quay lại</a>
    </div>

</form>

</body>
</html>