<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="fr">
  <jsp:include page="includes/head.jsp" />
  <body>
      <div class="mask"></div>
      <jsp:include page="includes/nav.jsp" />
      <div class="main-wrap">
          <jsp:include page="includes/maps/maps-header.jsp" />
          <div class="content-wrap orange-section">
              <div class="container">
                  <div class="head-img">
                      <img src="./img/media-img.jpg" alt="main article" class="img-responsive">
                  </div>
                  <div class="list-title"><i class="icon"></i>Mes Médiathèques</div>
                   <div class="list-wrap">
                      <ul class="media-list">
                          <c:forEach var="linkItem" items="${linksList}">
                      			<li class="list-item">                                
                              		<a href="${linkItem.url}" target="_blank"><h2 class="title">${linkItem.label}</h2></a>
                          		</li>
                      	  </c:forEach> 
                      </ul>
                  </div>
              </div>
          </div>
      </div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="./js/bootstrap.min.js"></script>
    <script src="./js/jquery.customSelect.min.js"></script>
    <script src="./js/script.js"></script>
  </body>
</html>