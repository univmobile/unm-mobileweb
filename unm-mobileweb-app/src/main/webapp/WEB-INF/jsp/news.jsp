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
          <div class="content-wrap yellow-section">
              <div class="container">
                  <div class="list-title"><i class="icon"></i>Act’Universitaire</div>
                  <c:if test="${fn:length(newsList) > 0}">
	                   <div class="list-wrap" id="accordion" role="tablist" aria-multiselectable="true">
	                      <ul class="article-list">
	                          <c:forEach var="newsItem" items="${newsList}" varStatus="loop">
	                      			<li class="list-item">
		                              <article>                                
		                                  <div class="row">
		                                      <a data-toggle="collapse" data-parent="#accordion" href="#collapse${loop.index}" aria-expanded="false" aria-controls="collapse${loop.index}">
		                                          <div class="img-wrap">
		                                              <img class="img-responsive" alt="article" src="${newsItem.imageUrl}">
		                                          </div>
		                                          <div class="description">
		                                              <time>${newsItem.author} - <fmt:formatDate pattern="dd/MM/YYYY" value="${newsItem.publishedDate}" /></time>
		                                              <h2>${newsItem.title}</h2>
		                                              <i class="icon"></i>
		                                          </div>
		                                      </a>
		                                  </div>
		                                  <div id="collapse${loop.index}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${loop.index}">
		                                      <div class="body">
		                                      	<p>${newsItem.description}</p>
		                                      	<c:if test="${not empty newsItem.link}">
		                                      		<a href="${newsItem.link}" class="btn button">Aller plus loin </a>
		                                      	</c:if>
		                                      </div>
		                                  </div>
		                              </article>
	                          		</li>                     		          
	                      	</c:forEach>
	                      </ul>
	                  </div>
	              </c:if>
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