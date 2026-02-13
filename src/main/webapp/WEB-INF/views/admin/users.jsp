<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Quản lý Người dùng - Site Admin</title>
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
    <a href="${pageContext.request.contextPath}/admin/users" class="active"><i class="fas fa-users me-2"></i> Quản lý User</a>
    <a href="${pageContext.request.contextPath}/admin/shops"><i class="fas fa-store me-2"></i> Duyệt Cửa hàng</a>
    <a href="${pageContext.request.contextPath}/admin/orders"><i class="fas fa-shopping-cart me-2"></i> Quản lý Đơn hàng</a>
    <hr class="mx-3">
    <a href="${pageContext.request.contextPath}/" target="_blank text-info"><i class="fas fa-external-link-alt me-2"></i> Xem trang chủ</a>
    <a href="${pageContext.request.contextPath}/logout" class="text-danger"><i class="fas fa-sign-out-alt me-2"></i> Đăng xuất</a>
</div>

<div class="main-content">
    <div class="table-container shadow-sm p-4 bg-white rounded">
        <h4 class="mb-4 fw-bold"><i class="fas fa-users-cog text-primary"></i> Quản lý thành viên</h4>

        <table class="table table-hover align-middle">
            <thead class="table-light">
                <tr>
                    <th>Người dùng</th>
                    <th class="text-center">Vai trò</th>
                    <th class="text-center">Trạng thái</th>
                    <th class="text-end">Thao tác</th>
                </tr>
            </thead>
            <tbody>
                <c:forEach items="${users}" var="u">
                    <tr>
                        <td>
                            <div class="fw-bold">${u.fullName}</div>
                            <small class="text-muted">@${u.username}</small>
                        </td>
                        <td class="text-center">
                            <span class="badge ${u.role == 'ADMIN' ? 'bg-danger' : (u.role == 'MERCHANT' ? 'bg-info' : 'bg-secondary')}">
                                ${u.role}
                            </span>
                        </td>
                        <td class="text-center">
                            <span class="badge ${u.status == 'ACTIVED' ? 'bg-success' : 'bg-warning'}">
                                ${u.status}
                            </span>
                        </td>
                        <td class="text-end">
                            <div class="btn-group">
                                <a href="/admin/users/edit/${u.id}" class="btn btn-sm btn-outline-primary"><i class="fas fa-edit"></i></a>
                                <a href="/admin/users/toggle-status/${u.id}"
                                   class="btn btn-sm ${u.status == 'ACTIVED' ? 'btn-outline-danger' : 'btn-outline-success'}"
                                   title="${u.status == 'ACTIVED' ? 'Khóa' : 'Mở khóa'}">
                                    <i class="fas ${u.status == 'ACTIVED' ? 'fa-user-slash' : 'fa-user-check'}"></i>
                                </a>
                            </div>
                        </td>
                    </tr>
                </c:forEach>
            </tbody>
        </table>
    </div>
</div>

<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/js/bootstrap.bundle.min.js"></script>
</body>
</html>