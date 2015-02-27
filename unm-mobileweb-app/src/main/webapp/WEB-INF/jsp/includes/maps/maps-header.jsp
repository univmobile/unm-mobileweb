<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8"%>

<header class="main-navbar container clearfix">
	<button type="button" class="navbar-menu-toggle">
	    <span class="icon-bar"></span>
	    <span class="icon-bar"></span>
	    <span class="icon-bar"></span>
	</button>
	<div class="user-nav">	    
	    	<c:choose>
		    	<c:when test="${currentUser != null}">
		    		<div class="user">
		        		<span class="welcome-msg">Bonjour</span>
		        		<div class="user-name">${currentUser.getDisplayName()}</div>
		        	</div>
		        </c:when>
	        <c:otherwise>
	        	<a href="login?path=${currentAbsolutePath}" style="color: white;"><div class="contact-link">Connectez-vous</div></a>
	        </c:otherwise>
	        </c:choose>   
	    <div class="universite-name">
	        ${university.getTitle()}
	    </div>
	    <div class="notifications">
	    	<a href="notifications">
	        	<i class="icon"></i>
	        </a>
	    </div>
	</div>
</header>