<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<header class="shopee-header">
    <div class="container">
        <div class="navbar-top">
            <div class="navbar-left">
                Trang chủ ManhMall | <a href="${pageContext.request.contextPath}/shops/register">Trở thành Người bán</a> | Kết nối
                <i class="fab fa-facebook"></i> <i class="fab fa-instagram"></i>
            </div>
            <div class="navbar-right">
                <a href="#"><i class="fas fa-bell"></i> Thông báo</a>
                <a href="#"><i class="fas fa-question-circle"></i> Hỗ trợ</a>
                <a href="#"><i class="fas fa-globe"></i> Tiếng Việt</a>
                <c:choose>
                    <c:when test="${empty sessionScope.currentUser}">
                        <span style="margin: 0 5px;">|</span>
                        <a href="/register" style="font-weight: bold;">Đăng ký</a>
                        <span style="margin: 0 5px;">|</span>
                        <a href="/login" style="font-weight: bold;">Đăng nhập</a>
                    </c:when>

                    <c:otherwise>
                        <span style="margin: 0 5px;">|</span>
                        <span class="user-info" style="font-weight: bold; color: white;">
                            <i class="fas fa-user-circle"></i> ${sessionScope.currentUser.fullName}
                        </span>
                        <span style="margin: 0 5px;">|</span>
                        <a href="${pageContext.request.contextPath}/logout"
                           onclick="return confirm('Bạn có muốn đăng xuất?')"
                           style="color: #fff; font-weight: normal;">
                           Đăng xuất
                        </a>
                    </c:otherwise>
                </c:choose>
            </div>
        </div>

        <div class="header-main">
            <a href="/" class="logo">
                <i class="fas fa-shopping-bag" style="font-size: 40px; margin-right: 10px;"></i>
                <span class="mall-text">Mạnh Mall</span>
            </a>

            <div class="search-box">
                <input type="text" placeholder="Tìm trong Mạnh Mall" class="search-input">
                <button class="search-btn">
                    <i class="fas fa-search"></i>
                </button>
            </div>

            <a class="cart-link" href="${pageContext.request.contextPath}/cart" aria-label="Giỏ hàng">
                <i class="fas fa-shopping-cart cart-icon"></i>
            </a>

        </div>
    </div>
</header>