<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Cửa hàng - Site Admin</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <style>
        body { background-color: #f4f7f6; }
        .sidebar { height: 100vh; background: #212529; color: white; position: fixed; width: 240px; }
        .sidebar a { color: #adb5bd; text-decoration: none; padding: 12px 20px; display: block; transition: 0.3s; }
        .sidebar a:hover, .sidebar a.active { background: #343a40; color: #ffc107; border-left: 4px solid #ffc107; }
        .main-content { margin-left: 240px; padding: 40px; }
        .table-container { background: white; border-radius: 15px; box-shadow: 0 4px 20px rgba(0,0,0,0.05); padding: 25px; }
        .badge-pending { background-color: #fff3cd; color: #856404; border: 1px solid #ffeeba; }
        .badge-active { background-color: #d4edda; color: #155724; border: 1px solid #c3e6cb; }
    </style>
</head>
<body>

<div class="sidebar">
    <div class="text-center py-4">
        <i class="fas fa-user-shield fa-3x text-warning"></i>
        <h5 class="mt-2 text-uppercase fw-bold">Site Admin</h5>
    </div>
    <a href="${pageContext.request.contextPath}/admin/dashboard"><i class="fas fa-chart-line me-2"></i> Dashboard</a>
    <a href="${pageContext.request.contextPath}/admin/users"><i class="fas fa-users me-2"></i> Quản lý User</a>
    <a href="${pageContext.request.contextPath}/admin/shops" class="active"><i class="fas fa-store me-2"></i> Duyệt Cửa hàng</a>
    <a href="${pageContext.request.contextPath}/admin/orders"><i class="fas fa-shopping-cart me-2"></i> Quản lý Đơn hàng</a>
    <hr class="mx-3">
    <a href="${pageContext.request.contextPath}/" target="_blank text-info"><i class="fas fa-external-link-alt me-2"></i> Xem trang chủ</a>
    <a href="${pageContext.request.contextPath}/logout" class="text-danger"><i class="fas fa-sign-out-alt me-2"></i> Đăng xuất</a>
</div>

<div class="main-content">
    <div class="container-fluid">
        <div class="card shadow-sm border-0">
            <div class="card-header bg-white py-3">
                <h5 class="mb-0 text-primary"><i class="fas fa-edit me-2"></i> Chỉnh sửa Cửa hàng</h5>
            </div>
            <div class="card-body p-4">
                <form action="${pageContext.request.contextPath}/admin/shops/update" method="post" enctype="multipart/form-data">
                    <input type="hidden" name="id" value="${shop.id}">

                    <div class="row">
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold">Tên cửa hàng</label>
                            <input type="text" name="name" class="form-control" value="${shop.name}" required>
                        </div>
                        <div class="col-md-6 mb-3">
                            <label class="form-label fw-bold">Trạng thái</label>
                            <select name="status" class="form-select">
                                <option value="PENDING" ${shop.status == 'PENDING' ? 'selected' : ''}>Chờ duyệt</option>
                                <option value="ACTIVE" ${shop.status == 'ACTIVE' ? 'selected' : ''}>Hoạt động</option>
                                <option value="BANNED" ${shop.status == 'BANNED' ? 'selected' : ''}>Đang bị khóa</option>
                            </select>
                        </div>
                    </div>

                    <div class="mb-3">
                        <label class="form-label fw-bold">Địa chỉ</label>
                        <textarea name="address" class="form-control" rows="3">${shop.address}</textarea>
                    </div>

                    <label class="form-label small text-muted">Chọn ảnh:</label>
                    <input type="file" name="imageFile" class="form-control" accept="image/*">

                    <div class="d-flex justify-content-end gap-2">
                        <a href="/admin/shops" class="btn btn-light border">Hủy</a>
                        <button type="submit" class="btn btn-primary px-4">Lưu thay đổi</button>
                    </div>
                </form>
            </div>
        </div>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>