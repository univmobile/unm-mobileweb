<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="fr">
<jsp:include page="includes/head.jsp" />
<body>
	<div class="mask"></div>
	<jsp:include page="includes/nav.jsp" />
	<div class="main-wrap">
		<jsp:include page="includes/maps/maps-header.jsp" />
		<div class="content-wrap">
			<div class="container">
				<c:if test="${fn:length(newsList) > 0 && isActUniv}">
					<article class="main-article clearfix">
						<div class="head-img">
							<img class="img-responsive" alt="main article"
								src="${newsList[0].imageUrl}">
						</div>
						<div class="description">
							<time>${newsList[0].feedName}
								-
								<fmt:formatDate pattern="dd/MM/YYYY"
									value="${newsList[0].publishedDate}" />
							</time>
							<h2 class="title">${newsList[0].title}</h2>
							<div class="body">
								<p>${newsList[0].description}</p>
							</div>
							<c:if test="${not empty newsList[0].link}">
								<a href="${newsList[0].link}" class="btn button" target="_blank">Aller plus loin </a>
							</c:if>
						</div>
					</article>
				</c:if>
				<c:if test="${fn:length(newsList) > 1 && isActUniv}">
					<div class="list-title">Dernières actualités</div>
					<div class="list-wrap" id="accordion" role="tablist"
						aria-multiselectable="true">
						<ul class="article-list">
							<c:forEach var="newsItem" items="${newsList}" varStatus="loop">
								<!-- skip first article, because it is already used -->
								<c:if test="${newsItem != newsList[0]}">
									<li class="list-item">
										<article>
											<div class="row">
												<a data-toggle="collapse" data-parent="#accordion"
													href="#collapse${loop.index}" aria-expanded="false"
													aria-controls="collapse${loop.index}">
													<div class="img-wrap">
														<img class="img-responsive" alt="article"
															src="${newsItem.imageUrl}">
													</div>
													<div class="description">
														<time>${newsItem.feedName}
															-
															<fmt:formatDate pattern="dd/MM/YYYY"
																value="${newsItem.publishedDate}" />
														</time>
														<h2>${newsItem.title}</h2>
														<i class="icon"></i>
													</div>
												</a>
											</div>
											<div id="collapse${loop.index}"
												class="panel-collapse collapse" role="tabpanel"
												aria-labelledby="heading${loop.index}">
												<div class="body">
													<p>${newsItem.description}</p>
													<c:if test="${not empty newsItem.link}">
														<a href="${newsItem.link}" class="btn button" target="_blank">Aller
															plus loin </a>
													</c:if>
												</div>
											</div>
										</article>
									</li>
								</c:if>
							</c:forEach>
						</ul>
					</div>
				</c:if>
				<!-- map iamge -->
				<div class="link-wrap more">
					<a class="link" href="news">Voir toutes les Actualités</a>
				</div>
				<c:if test="${isUnivMap}">
					<div class="list-title">Géo’Campus</div>
					<div class="img-wrap">
						<a href="university-map"><img class="img-responsive"
							src="${mapUrl}"></a>
					</div>
					<div class="bottom-buttons">
						<div class="category-buttons row">
							<c:if test="${isUnivMap}">
								<a href="university-map"
									class="active category-link one <c:if test="${nbTTMenus == 1}">col-xs-12</c:if><c:if test="${nbTTMenus == 2}">col-xs-6</c:if><c:if test="${nbTTMenus == 3}">col-xs-4</c:if>">
										<i class="icon"></i>
								</a>
							</c:if>
							<c:if test="${isParisMap}">
								<a href="paris-map" 
									class="category-link two <c:if test="${nbTTMenus == 1}">col-xs-12</c:if><c:if test="${nbTTMenus == 2}">col-xs-6</c:if><c:if test="${nbTTMenus == 3}">col-xs-4</c:if>">
										<i class="icon"></i>
								</a>
							</c:if>
							<c:if test="${isBB}">
								<a href="goodplans-map" 
									class="category-link three <c:if test="${nbTTMenus == 1}">col-xs-12</c:if><c:if test="${nbTTMenus == 2}">col-xs-6</c:if><c:if test="${nbTTMenus == 3}">col-xs-4</c:if>">
										<i class="icon"></i>
								</a>
							</c:if>
						</div>
					</div>
				</c:if>
			</div>
		</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/jquery.customSelect.min.js"></script>
	<script src="./js/script.js"></script>
</body>
</html>