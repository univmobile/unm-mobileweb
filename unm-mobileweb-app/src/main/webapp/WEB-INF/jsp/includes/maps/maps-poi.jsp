<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8"%>

<div class="poi-wrap" style="display:none;">
    <div class="poi-screen">
        <div class="poi-header">
            <span class="category-icon category-7"><i class="icon text-hide"></i></span>
            <div class="title"></div>
            <button class="btn show-hide-poi hide-poi"><i class="icon text-hide"></i></button>
        </div>
        <div class="poi-info">
        	<c:if test="${currentUser != null}">
	        	<form id="add-bookmark" class="add-bookmark" method="post">
		        	<div style="position: absolute;  left:-1px;">
		        		<input id="poiIdInputFieldForBookmark" type="hidden" name="poiIdBookmark">
		        		<button type="submit" style="font-size:10px; color:black">Bookmark</button>
		        	</div>
	        	</form>
        	</c:if>
            <address><i class="fa fa-map-marker fa-fw"></i><span id="addressPOI"></span></address>
            <div class="phone"><i class="fa fa-phone fa-fw"></i><span id="phonePOI"></span></div>
            <div class="email"><i class="fa fa-envelope fa-fw"></i><span id="emailPOI"></span></div>
        </div>
        <div id="poiTabPanel" class="tabpanel" role="tabpanel">
            <ul id="poiTabs" class="nav nav-tabs" role="tablist">
                <li role="presentation" class="one active"><a href="#plan" aria-controls="plan" role="tab" data-toggle="tab"><i class="fa fa-info-circle"></i></a></li>
                <li id="commentsTab" role="presentation" class="two"><a href="#comments" aria-controls="comments" role="tab" data-toggle="tab"><i class="fa fa-comments"></i></a></li>
                <li id="menuTab" role="presentation" class="three"><a href="#menu" aria-controls="menu" role="tab" data-toggle="tab"><i class="fa fa-cutlery"></i></a></li>
            </ul>
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane fade in active" id="plan">
                    <div class="body"></div>
                    <button class="btn show-hide-search show-search-long"><i class="icon"></i>Rechercher</button>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="comments">
                    <ul id="commentsList" class="comments-list"></ul>
                    <c:if test="${currentUser != null}">
	                    <div class="new-comment-wrap">
	                        <form id="new-comment" class="new-comment" method="post">
	                            <div class="form-group comment-field">
	                                <textarea name="commentMessage" class="form-control" rows="6" placeholder=""></textarea>
	                                <input id="poiIdInputField" type="hidden" name="poiId">
	                            </div>
	                            <button type="submit" class="btn long-button submit center-block">Valider</button>
	                        </form>
	                    </div>
                    </c:if>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="menu">
                	<span id="emptyString" style="padding: 10px">vide</span>
                	<ul id="menuList" class="menu-list"></ul>
                </div>
            </div>
        </div>
    </div>
</div>