<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Hệ thống Quản trị - Food Delivery</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background-color: #f8f9fa; }
        .sidebar { height: 100vh; background: #212529; color: white; position: fixed; width: 240px; }
        .sidebar a { color: #adb5bd; text-decoration: none; padding: 12px 20px; display: block; }
        .sidebar a:hover, .sidebar a.active { background: #343a40; color: white; border-left: 4px solid #ffc107; }
        .main-content { margin-left: 240px; padding: 30px; }
        .stat-card { border: none; border-radius: 10px; transition: transform 0.3s; }
        .stat-card:hover { transform: translateY(-5px); }
    </style>
</head>
<body>

<div class="sidebar">
    <div class="text-center py-4">
        <i class="fas fa-user-shield fa-3x text-warning"></i>
        <h5 class="mt-2">SITE ADMIN</h5>
    </div>
    <a href="${pageContext.request.contextPath}/admin/dashboard" class="active"><i class="fas fa-chart-line me-2"></i> Dashboard</a>
    <a href="${pageContext.request.contextPath}/admin/users"><i class="fas fa-users me-2"></i> Quản lý User</a>
    <a href="${pageContext.request.contextPath}/admin/shops"><i class="fas fa-store me-2"></i> Duyệt Cửa hàng</a>
    <a href="${pageContext.request.contextPath}/admin/orders"><i class="fas fa-shopping-cart me-2"></i> Quản lý Đơn hàng</a>
    <hr>
    <a href="${pageContext.request.contextPath}/" target="_blank"><i class="fas fa-external-link-alt me-2"></i> Xem trang chủ</a>
    <a href="${pageContext.request.contextPath}/logout" class="text-danger"><i class="fas fa-sign-out-alt me-2"></i> Đăng xuất</a>
</div>

<div class="main-content">
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2>Tổng quan hệ thống</h2>
        <span class="badge bg-dark p-2">Chào, ${sessionScope.currentUser.fullName}</span>
    </div>

    <div class="row">
        <div class="col-md-3">
            <div class="card stat-card bg-primary text-white p-3">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-uppercase">Người dùng</h6>
                        <h2 class="mb-0">${totalUsers}</h2>
                    </div>
                    <i class="fas fa-users fa-2x opacity-50"></i>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card bg-success text-white p-3">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-uppercase">Cửa hàng</h6>
                        <h2 class="mb-0">${totalShops}</h2>
                    </div>
                    <i class="fas fa-store fa-2x opacity-50"></i>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card bg-warning text-white p-3">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-uppercase">Đơn hàng</h6>
                        <h2 class="mb-0">${totalOrders}</h2>
                    </div>
                    <i class="fas fa-shipping-fast fa-2x opacity-50"></i>
                </div>
            </div>
        </div>
        <div class="col-md-3">
            <div class="card stat-card bg-danger text-white p-3">
                <div class="d-flex justify-content-between align-items-center">
                    <div>
                        <h6 class="text-uppercase">Món ăn</h6>
                        <h2 class="mb-0">${totalFoods}</h2>
                    </div>
                    <i class="fas fa-utensils fa-2x opacity-50"></i>
                </div>
            </div>
        </div>
    </div>

    <div class="mt-5 card shadow-sm p-4">
        <h5>Thông báo hệ thống</h5>
        <p class="text-muted">Chào mừng Leader trở lại. </p>
    </div>
</div>

</body>
</html>