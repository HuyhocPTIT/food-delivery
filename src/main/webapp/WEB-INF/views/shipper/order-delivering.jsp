<%@ page contentType="text/html; charset=UTF-8" %>
<%@ taglib uri="jakarta.tags.core" prefix="c" %>
<%@ taglib uri="jakarta.tags.fmt" prefix="fmt"%>
<!DOCTYPE html>
<html>
<head>
  <meta charset="UTF-8">
  <title>Đơn đang giao</title>
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/6.5.1/css/all.min.css">
  <link rel="stylesheet" href="<c:url value='/css/home.css' />">
  <link rel="stylesheet" href="<c:url value='/css/shipper.css' />">
</head>
<body>
<jsp:include page="/WEB-INF/views/layout/Header.jsp" />

<div class="shipper-container">
  <div class="shipper-header">
    <div>
      <h2>Đơn hàng đang giao</h2>
      <span class="shipper-badge">SHIPPING</span>
    </div>
    <nav class="shipper-nav">
      <a href="${pageContext.request.contextPath}/shipper/dashboard" class="tab">Tổng quan</a>
      <a href="${pageContext.request.contextPath}/shipper/dashboard" class="tab">Đơn chờ nhận</a>
      <a href="${pageContext.request.contextPath}/shipper/delivering" class="tab active">Đang giao</a>
      <a href="${pageContext.request.contextPath}/shipper/stats" class="tab">Thống kê</a>
    </nav>
  </div>

  <div class="shipper-card">
    <c:choose>
      <c:when test="${empty orders}">
        <div class="shipper-empty">Bạn chưa nhận đơn nào để giao.</div>
      </c:when>
      <c:otherwise>
        <table class="shipper-table">
          <fmt:setLocale value="vi_VN"/>
          <thead>
          <tr>
            <th>Mã đơn</th>
            <th>Khách hàng</th>
            <th>Địa chỉ giao</th>
            <th>Liên hệ</th>
            <th>Tổng tiền</th>
            <th>Trạng thái</th>
            <th>Hành động</th>
          </tr>
          </thead>
          <tbody>
          <c:forEach items="${orders}" var="order">
            <tr>
              <td>#${order.id}</td>
              <td>${order.customer.fullName}</td>
              <td>${order.address}</td>
              <td>${order.phone}</td>
              <td><fmt:formatNumber value="${order.totalPrice}" groupingUsed="true" maxFractionDigits="0"/></td>
              <td>Đang giao</td>
              <td>
                <form action="${pageContext.request.contextPath}/orders/complete" method="post">
                  <input type="hidden" name="orderId" value="${order.id}" />
                  <button type="submit" class="shipper-action secondary">
                    <i class="fas fa-circle-check"></i> Đã giao
                  </button>
                </form>
              </td>
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
