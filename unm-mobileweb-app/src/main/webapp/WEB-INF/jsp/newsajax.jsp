<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8"%>
                  <c:if test="${fn:length(newsList) > 0}">
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
		                                              <time>${newsItem.feedName} - <fmt:formatDate pattern="dd/MM/YYYY" value="${newsItem.publishedDate}" /></time>
		                                              <h2>${newsItem.title}</h2>
		                                              <i class="icon"></i>
		                                          </div>
		                                      </a>
		                                  </div>
		                                  <div id="collapse${loop.index}" class="panel-collapse collapse" role="tabpanel" aria-labelledby="heading${loop.index}">
		                                      <div class="body">
		                                      	<p>${newsItem.description}</p>
		                                      	<c:if test="${not empty newsItem.link}">
		                                      		<a href="${newsItem.link}" class="btn button" target="_blank">Aller plus loin </a>
		                                      	</c:if>
		                                      </div>
		                                  </div>
		                              </article>
	                          		</li>                     		          
	                      	</c:forEach>
	                      </ul>
	              </c:if>
