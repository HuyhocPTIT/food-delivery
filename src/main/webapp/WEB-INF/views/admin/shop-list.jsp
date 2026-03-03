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
    <div class="d-flex justify-content-between align-items-center mb-4">
        <h2 class="fw-bold text-dark"><i class="fas fa-store-alt text-primary me-2"></i> Quản lý Cửa hàng</h2>
        <nav aria-label="breadcrumb">
            <ol class="breadcrumb mb-0">
                <li class="breadcrumb-item"><a href="/admin/dashboard">Admin</a></li>
                <li class="breadcrumb-item active">Shops</li>
            </ol>
        </nav>
    </div>

    <div class="table-container">
        <div class="d-flex justify-content-between mb-3">
            <h5 class="text-secondary">Danh sách yêu cầu và cửa hàng</h5>
            <button class="btn btn-sm btn-outline-primary"><i class="fas fa-download me-1"></i> Xuất báo cáo</button>
        </div>

        <table class="table table-hover align-middle">
            <thead class="table-light">
                <tr>
                    <th class="col-id ps-3">ID</th>
                    <th class="col-info">Thông tin Shop</th>
                    <th class="col-owner text-center">Chủ sở hữu</th>
                    <th class="col-status text-center">Trạng thái</th>
                    <th class="col-action text-center">Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${shops}" var="shop">
                    <tr>
                        <td class="ps-3 text-muted">#${shop.id}</td>
                        <td>
                            <div class="d-flex align-items-center">
                                <div style="min-width: 0;"> <div class="fw-bold text-dark text-truncate-custom">${shop.name}</div>
                                    <small class="text-muted d-block text-truncate-custom">
                                        <i class="fas fa-map-marker-alt me-1"></i> ${shop.address}
                                    </small>
                                </div>
                            </div>
                        </td>
                        <td class="text-center">
                            <div class="fw-semibold">${shop.owner.fullName}</div>
                            <small class="text-primary text-truncate-custom">@${shop.owner.username}</small>
                        </td>
                        <td class="text-center">
                            <c:choose>
                                <c:when test="${shop.status == 'ACTIVE'}">
                                    <span class="badge badge-active p-2 w-75"><i class="fas fa-check-circle me-1"></i> Hoạt động</span>
                                </c:when>
                                <c:when test="${shop.status == 'BANNED'}">
                                    <span class="badge bg-danger p-2 w-75"><i class="fas fa-ban me-1"></i> Bị khóa</span>
                                </c:when>
                                <c:otherwise>
                                    <span class="badge badge-pending p-2 w-75 text-dark"><i class="fas fa-clock me-1"></i> Chờ duyệt</span>
                                </c:otherwise>
                            </c:choose>
                        </td>
                        <td class="text-center">
                            <div class="btn-group shadow-sm">
                                <c:if test="${shop.status == 'PENDING'}">
                                    <a href="/admin/shops/approve/${shop.id}" class="btn btn-sm btn-success" title="Duyệt"><i class="fas fa-check"></i></a>
                                </c:if>
                                <a href="/admin/shops/edit/${shop.id}" class="btn btn-sm btn-light border" title="Sửa"><i class="fas fa-pen text-primary"></i></a>
                                <a href="/admin/shops/delete/${shop.id}" class="btn btn-sm btn-light border" title="Xóa" onclick="return confirm('Xác nhận thao tác?')"><i class="fas fa-trash text-danger"></i></a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>

        <c:if test="${empty shops}">
            <div class="text-center py-5">
                <i class="fas fa-box-open fa-3x text-light mb-3"></i>
                <p class="text-muted">Chưa có cửa hàng nào trong danh sách.</p>
            </div>
        </c:if>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>