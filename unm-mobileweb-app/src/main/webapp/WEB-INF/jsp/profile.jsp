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
              <div class="main-container container">
                  <div class="universite-list-wrap" style="display: none;">
                      <div class="universite-change-screen">
                              <div class="screen-header">
                                  <div class="screen-title"><i class="icon"></i>Université actuelle</div>
                                  <span class="btn hide-universite-change show-hide-universite-change"><i class="icon"></i></span>
                              </div>
                              <div class="universite-change-field">
                                  <div class="current-univesite">${university.getTitle()}</div>
                              </div>
                              <section class="universite-results">
                                  <div class="info-title">Choisir une autre université </div>
                                  <ul class="universite--list">
                                  		<c:forEach var="universityItem" items="${universitiesList}">
		                                      <li class="list-item">
		                                          <a href="profile?universityId=${universityItem.getId()}" style="color: white;"><div class="title">${universityItem.getTitle()}</div></a>
		                                      </li>
                                  		</c:forEach>
                                  </ul>
                              </section>
                      </div>
                  </div>
                  <div class="user-info">
                      <div class="name">Patrick Termian</div>
                      <div class="universite">${university.getTitle()}</div>
                      <div class="link-wrap edit">
                          <a href="#" class="edit-profil show-hide-universite-change">Touchez pour modifier<i class="icon"></i></a>
                      </div>
                  </div>
                  <div class="list-title">Mes Médiathèques</div>
                   <div class="list-wrap">
                      <ul class="media-list">
                      		<c:forEach var="linkItem" items="${linksList}">
                      			<li class="list-item">                                
                              		<a href="${linkItem.getUrl()}" target="_blank"><h2 class="title">${linkItem.getLabel()}</h2></a>
                          		</li>
                      		</c:forEach> 
                      </ul>
                      <c:if test="${not empty linksList}"> 
	                      <div class="link-wrap more">
	                          <a href="media" class="link">Ouvrir ma médiathèque</a>
	                          <i class="icon"></i>
	                      </div>
                      </c:if>
                  </div>
                  <div class="list-title">Mes bibliothèques</div>
                   <div class="list-wrap">
                      <ul class="biblio-list">
                      		<c:forEach var="libraryItem" items="${librariesList}">
                      			<li class="list-item">                                
                              		<a href="university-map#${libraryItem.getPoi().getId()}">
                                  		<div class="title">
                                  			<span><c:if test="${libraryItem.getPoi().isIconRuedesfacs()}"><img alt="letters" src="./img/letters.jpg"></c:if></span>
                                  			${libraryItem.getPoi().getName()}
                                  		</div>
                                  		<i class="icon"></i>
                              		</a>
                          		</li>
                      		</c:forEach>                       
                      </ul>
                      <c:if test="${not empty librariesList}">
	                       <div class="link-wrap more">
	                           <a href="libraries" class="link">Voir toutes les bibliothèques</a>
	                           <i class="icon"></i>
	                       </div>
	                  </c:if>
                  </div>
                  <div class="list-title bookmark">Bookmarks</div>
                   <div class="list-wrap">
                      <ul class="bookmark-list">
                      		<c:forEach var="bookmarkItem" items="${bookmarksList}">
		                          <li class="list-item">                                
		                              <a href="${bookmarkItem.getMapUrl()}">
		                                  <div class="title">${bookmarkItem.getPoi().getName()}</div>
		                              </a>
		                          </li>
                     		</c:forEach>
                      </ul>
                      <c:if test="${not empty bookmarksList}">
                       	<div class="link-wrap more bookmark">
                           <a href="bookmarks" class="link">Voir tout mes bookmarks</a>
                           <i class="icon"></i>
                       	</div>
                      </c:if>
                  </div>
              </div>
          </div>
      </div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
    <script src="./js/bootstrap.min.js"></script>
    <script src="./js/jquery.customSelect.min.js"></script>
    <script src="./js/script.js"></script>
  </body>
</html>