<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Tổng quan Shipper</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/home.css' />">
    <link rel="stylesheet" href="<c:url value='/css/shipper.css' />">
</head>
<body>
<jsp:include page="/WEB-INF/views/layout/Header.jsp" />

<div class="shipper-container">
    <div class="shipper-header">
        <div>
            <h2>Tổng quan công việc Shipper</h2>
            <span class="shipper-badge">Dashboard</span>
        </div>
        <nav class="shipper-nav">
            <a href="${pageContext.request.contextPath}/shipper/dashboard" class="tab active">Tổng quan</a>
            <a href="${pageContext.request.contextPath}/shipper/waiting" class="tab">Đơn chờ nhận</a>
            <a href="${pageContext.request.contextPath}/shipper/delivering" class="tab">Đang giao</a>
            <a href="${pageContext.request.contextPath}/shipper/stats" class="tab">Thống kê</a>
        </nav>
    </div>

    <div class="shipper-card">
        <div class="shipper-summary">
            <div class="summary-item">
                <h3>Đơn chờ nhận</h3>
                <p>${readyCount}</p>
                <span class="summary-note">Sẵn sàng để nhận</span>
            </div>
            <div class="summary-item">
                <h3>Đơn đang giao của tôi</h3>
                <p>${deliveringCount}</p>
                <span class="summary-note">Đang xử lý</span>
            </div>
            <div class="summary-item">
                <h3>Đơn đã giao thành công</h3>
                <p>${deliveredCount}</p>
                <span class="summary-note">Tích lũy đến hiện tại</span>
            </div>
            <div class="summary-item">
                <h3>Điểm đánh giá trung bình</h3>
                <div class="rating" title="${avgRating}">
                    <div class="rating-back">★★★★★</div>
                    <div class="rating-front" style="width: ${avgRating * 20}%">★★★★★</div>
                </div>
            </div>
        </div>

        <div style="display: flex; gap: 10px; margin-top: 20px; flex-wrap: wrap;">
            <a href="${pageContext.request.contextPath}/shipper/waiting" class="shipper-action" style="text-decoration:none; display:inline-flex; align-items:center; gap:6px;">
                <i class="fas fa-box"></i> Xem đơn chờ nhận
            </a>
            <a href="${pageContext.request.contextPath}/shipper/delivering" class="shipper-action secondary" style="text-decoration:none; display:inline-flex; align-items:center; gap:6px;">
                <i class="fas fa-motorcycle"></i> Tiếp tục giao đơn
            </a>
        </div>
    </div>

    <div class="shipper-card">
        <h3>Đơn đang giao gần đây</h3>
        <c:choose>
            <c:when test="${empty deliveringOrders}">
                <div class="shipper-empty">Hiện bạn chưa có đơn đang giao.</div>
            </c:when>
            <c:otherwise>
                <fmt:setLocale value="vi_VN"/>
                <table class="shipper-table">
                    <thead>
                    <tr>
                        <th>Mã đơn</th>
                        <th>Khách hàng</th>
                        <th>Địa chỉ</th>
                        <th>Liên hệ</th>
                        <th>Tổng tiền</th>
                    </tr>
                    </thead>
                    <tbody>
                    <c:forEach items="${deliveringOrders}" var="order">
                        <tr>
                            <td>#${order.id}</td>
                            <td>${order.customer.fullName}</td>
                            <td>${order.address}</td>
                            <td>${order.phone}</td>
                            <td><fmt:formatNumber value="${order.totalPrice}" type="number" groupingUsed="true" maxFractionDigits="0"/> đ</td>
                        </tr>
                    </c:forEach>
                    </tbody>
                </table>
            </c:otherwise>
        </c:choose>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/Footer.jsp" />
</body>
</html>