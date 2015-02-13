<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="fr">
  <jsp:include page="includes/head.jsp" />
  <body>
      <div class="mask"></div>
      <jsp:include page="includes/nav.jsp" />
      <div class="main-wrap">
          <header class="main-navbar clearfix">
                      <button type="button" class="navbar-menu-toggle">
                          <span class="icon-bar"></span>
                          <span class="icon-bar"></span>
                          <span class="icon-bar"></span>
                      </button>
                      <div class="user-nav">
                          <div class="contact-link">Connectez-vous</div>
                          <div class="universite-name">
                          	${university.getTitle()}
                          </div>
                          <div class="notifications">
                              <i class="icon"></i>
                          </div>
                      </div>
          </header>
          <div class="content-wrap">
              <div class="container">
                  <article class="main-article clearfix">
                      <div class="head-img">
                          <img class="img-responsive" alt="main article" src="${newsList[0].getImageUrl()}">
                      </div>
                      <div class="description">
                          <time>${newsList[0].getAuthor()} - <fmt:formatDate pattern="dd/MM/YYYY" value="${newsList[0].getPublishedDate()}" /></time>
                          <h2 class="title">${newsList[0].getTitle()}</h2>
                          <div class="body">
                              <p>${newsList[0].getDescription()}</p>
                          </div>
                      </div>
                  </article>
                  <c:if test="${fn:length(newsList) > 1}">
	                  <div class="list-title">Dernières actualités</div>
	                  <div class="list-wrap"  id="accordion" role="tablist" aria-multiselectable="true">
	                      <ul class="article-list">
	                      	<c:forEach var="newsItem" items="${newsList}" varStatus="loop">
	                      		<!-- skip first article, because it is already used -->
	                      		<c:if test="${newsItem != newsList[0]}">
	                      			<li class="list-item">
		                              <article>                                
		                                  <div class="row">
		                               		  
		                               		  <c:choose>
		                               		  	<c:when test="${loop.index == 1}"><c:set var="number" value="One"/></c:when>
		                               		  	<c:when test="${loop.index == 2}"><c:set var="number" value="Two"/></c:when>
		                               		  	<c:when test="${loop.index == 3}"><c:set var="number" value="Three"/></c:when>
		                               		  	<c:when test="${loop.index == 4}"><c:set var="number" value="Four"/></c:when>
		                               		  </c:choose>
		                               		  
		                                      <a data-toggle="collapse" data-parent="#accordion" href="#collapse${number}" aria-expanded="false" aria-controls="collapse${number}">
		                                          <div class="img-wrap">
		                                              <img class="img-responsive" alt="article" src="${newsItem.getImageUrl()}">
		                                          </div>
		                                          <div class="description">
		                                              <time>${newsItem.getAuthor()} - <fmt:formatDate pattern="dd/MM/YYYY" value="${newsItem.getPublishedDate()}" /></time>
		                                              <h2>${newsItem.getTitle()}</h2>
		                                              <i class="icon"></i>
		                                          </div>
		                                      </a>
		                                  </div>
		                                  <div id="collapse${number}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${number}">
		                                      <div class="body">
		                                      	<p>${newsItem.getDescription()}</p>
		                                      	<c:if test="${newsItem.getLink() != null && !newsItem.getLink().equals('')}">
		                                      		<a href="${newsItem.getLink()}" class="btn button">Aller plus loin </a>
		                                      	</c:if>
		                                      </div>
		                                  </div>
		                              </article>
	                          		</li>
	                      		</c:if>                      		          
	                      	</c:forEach>
	                      
	                      
	                      
	                      
	                      	  <!--
	                          <li class="list-item">
	                              <article>                                
	                                  <div class="row">
	                                      <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
	                                              <div class="img-wrap">
	                                                  <img class="img-responsive" alt="article" src="./img/article-1.jpg">
	                                              </div>
	                                              <div class="description">
	                                                  <time>Nom du flux - 04/01/2015</time>
	                                                  <h2>Vivre avec une personne âgée : une coloc’ originale pour les étudiants</h2>
	                                                  <i class="icon"></i>
	                                              </div>
	                                      </a>
	                                  </div>
	                                      <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
	                                          <div class="body">
	                                              <p>1ère étape - L'inscription par internet : vous vous enregistrez sur la plateforme afin de constituer votre dossier numérique Vous saisissez l'ensemble de vos demandes de poursuite d'études : candidatures</p>
	                                              <p>Ajout et retrait de candidatures : Du 20 Janvier au 20 Mars 18H pour toutes vos candidatures.</p>
	                                              <a href="#" class="btn button">Aller plus loin </a>
	                                          </div>
	                                      </div>
	                              </article>
	                          </li>
	                          <li class="list-item">
	                              <article>                                
	                                  <div class="row">
	                                      <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
	                                          <div class="img-wrap">
	                                              <img class="img-responsive" alt="article" src="./img/article-2.jpg">
	                                          </div>
	                                          <div class="description">
	                                              <time>Nom du flux - 23/12/2014</time>
	                                              <h2>Les écoles de commerce dans la bataille des Mooc</h2>
	                                              <i class="icon"></i>
	                                          </div>
	                                      </a>
	                                  </div>
	                                  <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
	                                      <div class="body">
	                                          <p>1ère étape - L'inscription par internet : vous vous enregistrez sur la plateforme afin de constituer votre dossier numérique Vous saisissez l'ensemble de vos demandes de poursuite d'études : candidatures</p>
	                                          <p>Ajout et retrait de candidatures : Du 20 Janvier au 20 Mars 18H pour toutes vos candidatures.</p>
	                                      </div>
	                                  </div>
	                              </article>
	                          </li>
	                          <li class="list-item">
	                              <article>                                
	                                  <div class="row">
	                                      <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
	                                          <div class="img-wrap">
	                                              <img class="img-responsive" alt="article" src="./img/article-3.jpg">
	                                          </div>
	                                          <div class="description">
	                                              <time>Nom du flux - 18/12/2014</time>
	                                              <h2>Comment s’inscrire sur Admission Post Bac</h2>
	                                              <i class="icon"></i>
	                                          </div>
	                                      </a>
	                                  </div>
	                                  <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
	                                      <div class="body">
	                                          <p>1ère étape - L'inscription par internet : vous vous enregistrez sur la plateforme afin de constituer votre dossier numérique Vous saisissez l'ensemble de vos demandes de poursuite d'études : candidatures</p>
	                                          <p>Ajout et retrait de candidatures : Du 20 Janvier au 20 Mars 18H pour toutes vos candidatures.</p>
	                                      </div>
	                                  </div>
	                              </article>
	                          </li>
	                          -->
	
	                      </ul>
	                  </div>
				  </c:if>
				  <!-- map iamge -->
				  <div class="img-wrap">
		          	<a href="geocampus"><img class="img-responsive" src="${mapUrl}"></a>
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