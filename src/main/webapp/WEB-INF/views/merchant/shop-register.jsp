<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Đăng ký Cửa hàng - Mạnh Mall</title>
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.0/dist/css/bootstrap.min.css" rel="stylesheet">
    <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.4.0/css/all.min.css">
    <link rel="stylesheet" href="/css/shop.css">
    <link rel="stylesheet" href="/css/home.css">
    <style>
        .card { border: none; border-radius: 8px; box-shadow: 0 0.125rem 0.25rem rgba(0, 0, 0, 0.075); }
        .card-header { background-color: #fff; border-bottom: 1px solid #edf2f9; padding: 1.25rem; }
        .form-label { font-weight: 600; color: #334155; margin-bottom: 0.5rem; }
        .form-control, .form-select { border-radius: 6px; border: 1px solid #d1d5db; padding: 0.625rem; }
        .btn-primary { background-color: #ee4d2d; border: none; padding: 0.625rem 1.25rem; font-weight: 500; }
        .btn-light { background-color: #f8f9fa; border: 1px solid #d1d5db; color: #4b5563; }
        .image-preview-container { margin-top: 1rem; border: 2px dashed #e5e7eb; border-radius: 8px; padding: 1rem; text-align: center; background: #fff; }
        .image-preview-container img { max-width: 100%; max-height: 200px; border-radius: 6px; display: none; }
    </style>
</head>
<body>

<jsp:include page="/WEB-INF/views/layout/Header.jsp" />

<div class="container py-5">
    <div class="row justify-content-center">
        <div class="col-lg-8">
            <div class="card">
                <div class="card-header d-flex align-items-center">
                    <h5 class="mb-0 fw-bold">Đăng ký Cửa hàng mới</h5>
                </div>
                <div class="card-body p-4">
                    <form action="${pageContext.request.contextPath}/shops/register" method="post" enctype="multipart/form-data">
                        <div class="row g-3">
                            <div class="col-md-12">
                                <label class="form-label">Tên cửa hàng</label>
                                <input type="text" name="name" class="form-control" placeholder="Nhập tên cửa hàng của bạn" required>
                            </div>

                            <div class="col-md-12">
                                <label class="form-label">Địa chỉ</label>
                                <textarea name="address" class="form-control" rows="3" placeholder="Số nhà, tên đường, phường/xã..." required></textarea>
                            </div>

                            <div class="col-md-12">
                                <label class="form-label">Hình ảnh đại diện (Logo)</label>
                                <div class="input-group">
                                    <input type="file" name="imageFile" class="form-control" id="shopImage" accept="image/*" required>
                                </div>
                                <div class="image-preview-container" id="previewBox">
                                    <i class="fas fa-image fa-2x text-muted mb-2 d-block" id="placeholderIcon"></i>
                                    <span class="text-muted d-block" id="placeholderText">Chưa có tệp nào được chọn</span>
                                    <img id="imgPreview" src="#" alt="Preview">
                                </div>
                            </div>
                        </div>

                        <div class="d-flex justify-content-end gap-2 mt-4">
                            <a href="${pageContext.request.contextPath}/shops" class="btn btn-light">Hủy</a>
                            <button type="submit" class="btn btn-primary">Gửi yêu cầu xét duyệt</button>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </div>
</div>

<script>
    // Xử lý Preview ảnh ngay khi chọn file
    const shopImage = document.getElementById('shopImage');
    const imgPreview = document.getElementById('imgPreview');
    const placeholderIcon = document.getElementById('placeholderIcon');
    const placeholderText = document.getElementById('placeholderText');

    shopImage.onchange = evt => {
        const [file] = shopImage.files;
        if (file) {
            imgPreview.src = URL.createObjectURL(file);
            imgPreview.style.display = 'inline-block';
            placeholderIcon.style.display = 'none';
            placeholderText.style.display = 'none';
        }
    }
</script>

<jsp:include page="/WEB-INF/views/layout/Footer.jsp" />

</body>
</html>