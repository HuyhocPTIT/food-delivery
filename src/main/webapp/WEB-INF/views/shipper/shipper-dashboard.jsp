<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Dashboard Shipper - Quản lý đơn hàng</title>
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
    <link rel="stylesheet" href="<c:url value='/css/home.css' />">
    <link rel="stylesheet" href="<c:url value='/css/shipper.css' />">
</head>
<body>
<jsp:include page="/WEB-INF/views/layout/Header.jsp" />

<div class="shipper-container">
    <!-- Header Section -->
    <div class="shipper-header">
        <div class="header-left">
            <div class="title-group">
                <i class="fas fa-tachometer-alt"></i>
                <h2>Dashboard Shipper</h2>
            </div>
            <span class="shipper-badge status-online">
                <i class="fas fa-circle"></i>
                Đang hoạt động
            </span>
        </div>
        <nav class="shipper-nav">
            <a href="${pageContext.request.contextPath}/shipper/dashboard" class="nav-link active">
                <i class="fas fa-box"></i>
                <span>Đơn chờ nhận</span>
            </a>
            <a href="${pageContext.request.contextPath}/shipper/delivering" class="nav-link">
                <i class="fas fa-truck"></i>
                <span>Đang giao</span>
            </a>
            <a href="${pageContext.request.contextPath}/shipper/stats" class="nav-link">
                <i class="fas fa-chart-bar"></i>
                <span>Thống kê</span>
            </a>
        </nav>
    </div>

    <!-- Quick Stats Overview -->
    <div class="quick-stats">
        <div class="quick-stat-item">
            <div class="stat-icon-small pending">
                <i class="fas fa-clock"></i>
            </div>
            <div class="stat-info">
                <span class="stat-label">Chờ nhận</span>
                <span class="stat-number">${pendingCount != null ? pendingCount : 12}</span>
            </div>
        </div>
        <div class="quick-stat-item">
            <div class="stat-icon-small delivering">
                <i class="fas fa-shipping-fast"></i>
            </div>
            <div class="stat-info">
                <span class="stat-label">Đang giao</span>
                <span class="stat-number">${deliveringCount != null ? deliveringCount : 5}</span>
            </div>
        </div>
        <div class="quick-stat-item">
            <div class="stat-icon-small completed">
                <i class="fas fa-check-circle"></i>
            </div>
            <div class="stat-info">
                <span class="stat-label">Hoàn thành hôm nay</span>
                <span class="stat-number">${todayCompleted != null ? todayCompleted : 18}</span>
            </div>
        </div>
        <div class="quick-stat-item">
            <div class="stat-icon-small earnings">
                <i class="fas fa-dollar-sign"></i>
            </div>
            <div class="stat-info">
                <span class="stat-label">Thu nhập hôm nay</span>
                <span class="stat-number">${todayEarnings != null ? todayEarnings : '450,000'} đ</span>
            </div>
        </div>
    </div>

    <!-- Filter and Search Section -->
    <div class="shipper-card">
        <div class="filter-section">
            <div class="search-box">
                <i class="fas fa-search"></i>
                <input type="text" placeholder="Tìm kiếm theo mã đơn, tên khách hàng...">
            </div>
            <div class="filter-buttons">
                <button class="filter-btn active" data-status="all">
                    <i class="fas fa-list"></i>
                    Tất cả
                </button>
                <button class="filter-btn" data-status="priority">
                    <i class="fas fa-exclamation-circle"></i>
                    Ưu tiên
                </button>
                <button class="filter-btn" data-status="nearby">
                    <i class="fas fa-map-marker-alt"></i>
                    Gần tôi
                </button>
            </div>
        </div>
    </div>

    <!-- Orders List -->
    <div class="shipper-card">
        <div class="card-header">
            <h3>
                <i class="fas fa-box-open"></i>
                Đơn hàng chờ nhận
            </h3>
            <span class="order-count">${pendingCount != null ? pendingCount : 12} đơn</span>
        </div>

        <div class="orders-list">
            <!-- Order Item 1 -->
            <div class="order-item priority">
                <div class="order-badge">
                    <i class="fas fa-star"></i>
                    Ưu tiên
                </div>
                <div class="order-header">
                    <div class="order-info-left">
                        <h4 class="order-id">
                            <i class="fas fa-hashtag"></i>
                            DH202402081234
                        </h4>
                        <span class="order-time">
                            <i class="far fa-clock"></i>
                            15 phút trước
                        </span>
                    </div>
                    <div class="order-value">
                        <span class="cod-label">COD</span>
                        <span class="amount">580,000 đ</span>
                    </div>
                </div>

                <div class="order-details">
                    <div class="detail-row">
                        <i class="fas fa-user"></i>
                        <div class="detail-content">
                            <span class="detail-label">Khách hàng</span>
                            <span class="detail-value">Nguyễn Văn A</span>
                        </div>
                    </div>
                    <div class="detail-row">
                        <i class="fas fa-phone"></i>
                        <div class="detail-content">
                            <span class="detail-label">Số điện thoại</span>
                            <span class="detail-value">0912345678</span>
                        </div>
                    </div>
                    <div class="detail-row">
                        <i class="fas fa-map-marker-alt"></i>
                        <div class="detail-content">
                            <span class="detail-label">Địa chỉ giao hàng</span>
                            <span class="detail-value">123 Đường Lê Lợi, Quận 1, TP.HCM</span>
                        </div>
                    </div>
                    <div class="detail-row">
                        <i class="fas fa-route"></i>
                        <div class="detail-content">
                            <span class="detail-label">Khoảng cách</span>
                            <span class="detail-value distance">2.3 km</span>
                        </div>
                    </div>
                </div>

                <div class="order-actions">
                    <button class="btn-view-detail">
                        <i class="fas fa-eye"></i>
                        Xem chi tiết
                    </button>
                    <button class="btn-accept">
                        <i class="fas fa-check"></i>
                        Nhận đơn
                    </button>
                </div>
            </div>

            <!-- Order Item 2 -->
            <div class="order-item">
                <div class="order-header">
                    <div class="order-info-left">
                        <h4 class="order-id">
                            <i class="fas fa-hashtag"></i>
                            DH202402081235
                        </h4>
                        <span class="order-time">
                            <i class="far fa-clock"></i>
                            32 phút trước
                        </span>
                    </div>
                    <div class="order-value">
                        <span class="cod-label">COD</span>
                        <span class="amount">320,000 đ</span>
                    </div>
                </div>

                <div class="order-details">
                    <div class="detail-row">
                        <i class="fas fa-user"></i>
                        <div class="detail-content">
                            <span class="detail-label">Khách hàng</span>
                            <span class="detail-value">Trần Thị B</span>
                        </div>
                    </div>
                    <div class="detail-row">
                        <i class="fas fa-phone"></i>
                        <div class="detail-content">
                            <span class="detail-label">Số điện thoại</span>
                            <span class="detail-value">0987654321</span>
                        </div>
                    </div>
                    <div class="detail-row">
                        <i class="fas fa-map-marker-alt"></i>
                        <div class="detail-content">
                            <span class="detail-label">Địa chỉ giao hàng</span>
                            <span class="detail-value">456 Nguyễn Huệ, Quận 1, TP.HCM</span>
                        </div>
                    </div>
                    <div class="detail-row">
                        <i class="fas fa-route"></i>
                        <div class="detail-content">
                            <span class="detail-label">Khoảng cách</span>
                            <span class="detail-value distance">4.7 km</span>
                        </div>
                    </div>
                </div>

                <div class="order-actions">
                    <button class="btn-view-detail">
                        <i class="fas fa-eye"></i>
                        Xem chi tiết
                    </button>
                    <button class="btn-accept">
                        <i class="fas fa-check"></i>
                        Nhận đơn
                    </button>
                </div>
            </div>

            <!-- Order Item 3 -->
            <div class="order-item">
                <div class="order-header">
                    <div class="order-info-left">
                        <h4 class="order-id">
                            <i class="fas fa-hashtag"></i>
                            DH202402081236
                        </h4>
                        <span class="order-time">
                            <i class="far fa-clock"></i>
                            1 giờ trước
                        </span>
                    </div>
                    <div class="order-value">
                        <span class="cod-label">COD</span>
                        <span class="amount">150,000 đ</span>
                    </div>
                </div>

                <div class="order-details">
                    <div class="detail-row">
                        <i class="fas fa-user"></i>
                        <div class="detail-content">
                            <span class="detail-label">Khách hàng</span>
                            <span class="detail-value">Lê Văn C</span>
                        </div>
                    </div>
                    <div class="detail-row">
                        <i class="fas fa-phone"></i>
                        <div class="detail-content">
                            <span class="detail-label">Số điện thoại</span>
                            <span class="detail-value">0901234567</span>
                        </div>
                    </div>
                    <div class="detail-row">
                        <i class="fas fa-map-marker-alt"></i>
                        <div class="detail-content">
                            <span class="detail-label">Địa chỉ giao hàng</span>
                            <span class="detail-value">789 Pasteur, Quận 3, TP.HCM</span>
                        </div>
                    </div>
                    <div class="detail-row">
                        <i class="fas fa-route"></i>
                        <div class="detail-content">
                            <span class="detail-label">Khoảng cách</span>
                            <span class="detail-value distance">1.8 km</span>
                        </div>
                    </div>
                </div>

                <div class="order-actions">
                    <button class="btn-view-detail">
                        <i class="fas fa-eye"></i>
                        Xem chi tiết
                    </button>
                    <button class="btn-accept">
                        <i class="fas fa-check"></i>
                        Nhận đơn
                    </button>
                </div>
            </div>
        </div>

        <!-- Empty State (hiển thị khi không có đơn) -->
        <%-- <div class="shipper-empty">
            <i class="fas fa-box-open"></i>
            <h3>Không có đơn hàng nào</h3>
            <p>Hiện tại không có đơn hàng chờ nhận</p>
        </div> --%>
    </div>
</div>

<jsp:include page="/WEB-INF/views/layout/Footer.jsp" />

<script>
    // Filter buttons functionality
    document.querySelectorAll('.filter-btn').forEach(btn => {
        btn.addEventListener('click', function() {
            document.querySelectorAll('.filter-btn').forEach(b => b.classList.remove('active'));
            this.classList.add('active');
        });
    });

    // Accept order button
    document.querySelectorAll('.btn-accept').forEach(btn => {
        btn.addEventListener('click', function() {
            if(confirm('Bạn có chắc muốn nhận đơn hàng này?')) {
                // Add your accept order logic here
                alert('Đã nhận đơn hàng thành công!');
            }
        });
    });
</script>
</body>
</html>
