<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt" %>

<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Trang chủ Mạnh Mall</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/home.css?v=3'/>">
    <link rel="stylesheet" href="<c:url value='/css/home.css'/>">
    <script src="https://cdn.jsdelivr.net/npm/sweetalert2@11"></script>
</head>
<body style="background-color: #f5f5f5;"> <jsp:include page="/WEB-INF/views/layout/Header.jsp" />

<div class="container">
    <div class="main-layout">

        <aside class="sidebar">
            <h3 class="filter-title"><i class="fas fa-list"></i> Danh mục</h3>
            <ul class="cat-list">
                <li><a href="?categoryId=1">Cơm Gà</a></li>
                <li><a href="?categoryId=2">Trà Sữa</a></li>
                <li><a href="?categoryId=3">Bánh Mì</a></li>
                <li style="border-top: 1px dashed #ddd;"><a href="${pageContext.request.contextPath}/">Tất cả món ăn</a></li>
            </ul>
            <div class="price-range-filter" style="margin-top: 30px; border-top: 1px solid #eee; padding-top: 20px;">
                    <h3 class="filter-title" style="font-size: 16px; margin-bottom: 15px;">Khoảng Giá</h3>
                    <form action="${pageContext.request.contextPath}/" method="GET">
                        <div class="price-input-group" style="display: flex; align-items: center; gap: 5px; margin-bottom: 15px;">
                            <input type="number" name="minPrice"
                                   value="<fmt:formatNumber value='${minPrice}' pattern='#' />"
                                   placeholder="₫ TỪ" class="price-input">

                            <span style="color: #bdbdbd">-</span>

                            <input type="number" name="maxPrice"
                                   value="<fmt:formatNumber value='${maxPrice}' pattern='#' />"
                                   placeholder="₫ ĐẾN" class="price-input">
                        </div>
                        <button type="submit" class="btn-apply">ÁP DỤNG</button>
                        <a href="${pageContext.request.contextPath}/"
                            style="display: block; text-align: center; margin-top: 10px; font-size: 12px; color: #666; text-decoration: none; border: 1px solid #ddd; padding: 5px; border-radius: 2px; background: #fff;">
                            XÓA TẤT CẢ
                        </a>
                    </form>
            </div>
        </aside>

        <div class="main-content">
            <h2 style="margin: 0 0 20px 0; color: #ee4d2d; border-bottom: 2px solid #ee4d2d; display: inline-block; padding-bottom: 5px;">
                GỢI Ý HÔM NAY
            </h2>

            <div class="sort-bar">
                <span>Sắp xếp theo: </span>
                <button class="sort-btn active">Phổ biến</button>
                <button class="sort-btn">Mới nhất</button>
                <button class="sort-btn">Best Seller</button>
                <div class="price-filter-dropdown">
                        <div class="dropdown-label">
                            <span>Giá</span>
                            <i class="fas fa-chevron-down"></i>
                        </div>
                        <ul class="dropdown-menu">
                            <li><a href="?sort=price,asc">Giá: Thấp đến Cao</a></li>
                            <li><a href="?sort=price,desc">Giá: Cao đến Thấp</a></li>
                        </ul>
                    </div>
            </div>

            <div class="product-grid">
                <c:forEach items="${foods}" var="f">
                    <a href="${pageContext.request.contextPath}/foods/detail/${f.id}"
                       class="product-link"
                       style="text-decoration: none;">

                        <div class="product-card">
                            <span class="mall-badge">Mall</span>

                            <img src="<c:url value='${f.image}'/>"
                                 alt="${f.name}"
                                 onerror="this.src='https://via.placeholder.com/200x200?text=No+Image';"
                                 style="width: 100%; height: 180px; object-fit: cover;">

                            <div class="product-info">
                                <p class="product-name" style="color: #333; margin-bottom: 8px;">${f.name}</p>

                                <p class="product-price" style="text-decoration: none;">
                                    <fmt:formatNumber value="${f.price}" type="number" groupingUsed="true"/> đ
                                </p>

                                <p class="product-description" style="color: #666; font-size: 12px;">${f.description}</p>
                            </div>
                        </div>
                    </a>
                </c:forEach>
            </div>
            <div class="pagination">
                <c:if test="${currentPage > 0}">
                    <a href="?page=${currentPage - 1}" class="pagination-btn">
                        <i class="fas fa-chevron-left"></i>
                    </a>
                </c:if>

               <c:forEach begin="0" end="${totalPages - 1}" var="i">
                   <a href="?page=${i}&sort=${currentSort}${not empty categoryId ? '&categoryId=' += categoryId : ''}${not empty minPrice ? '&minPrice=' += minPrice : ''}${not empty maxPrice ? '&maxPrice=' += maxPrice : ''}"
                      class="pagination-btn ${currentPage == i ? 'active' : ''}">
                       ${i + 1}
                   </a>
               </c:forEach>

                <c:if test="${currentPage < totalPages - 1}">
                    <a href="?page=${currentPage + 1}" class="pagination-btn">
                        <i class="fas fa-chevron-right"></i>
                    </a>
                </c:if>
            </div>
        </div> </div> </div> ```






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