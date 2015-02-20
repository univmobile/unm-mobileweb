<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav class="navbar-menu" id="mobile-nav">
	<header>
		<c:choose>
			<c:when test="${!universityLogo.equals('')}">
				<a class="universite-logo" href="#"><img class="img-responsive" alt="Universite Paris" src="${universityLogo}"></a>
			</c:when>
			<c:otherwise>
				<a class="universite-logo"></a>
			</c:otherwise>
		</c:choose>
	    <span class="close-btn"></span>
	</header>
	<ul class="menu navbar-nav">
		<c:if test="${fn:length(menuMS) != 0}">
		    <li class="menu-item one active dropdown">
		        <a class="dropdown-toggle" data-toggle="dropdown" role="button" href="#">Mes services<span class="menu-item-icon"><i class="icon"></i></span></a>
		        <ul class="dropdown-menu" role="menu">
		        	<c:forEach var="menuItem" items="${menuMS}">
		        		<c:if test="${menuItem.isActive()}">
			        		<c:choose>
								<c:when test="${menuItem.getUrl().equals('')}">
									<!-- opens content of menu item -->
									<li><a href="menu-content?menuId=${menuItem.getId()}">${menuItem.getName()}</a></li>
								</c:when>
								<c:otherwise>
									<!--  opens url of menu item -->
									<c:if test="${menuItem.isPredefinedMenu()}">
										<li><a href="${menuItem.getUrl()}">${menuItem.getName()}</a></li>
									</c:if>
									<c:if test="${!menuItem.isPredefinedMenu()}">
										<li><a href="${menuItem.getUrl()}" target="_blank">${menuItem.getName()}</a></li>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
		        </ul>
		    </li>
	    </c:if>
	    <li class="menu-item two dropdown">
	        <a class="dropdown-toggle" role="button" href="news">Act’Universitaire<span class="menu-item-icon"><i class="icon"></i></span></a>
	        <!--
	        <ul class="dropdown-menu" role="menu">
	            <li><a href="#">Menu 1</a></li>
	            <li><a href="#">Menu 2</a></li>
	            <li><a href="#">Menu 3</a></li>
	        </ul>
	        -->
	    </li>
	    <c:if test="${fn:length(menuTT) != 0}">
		    <li class="menu-item three dropdown">
		        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">Tou’Trouver<span class="menu-item-icon"><i class="icon"></i></span></a>
		        <ul class="dropdown-menu" role="menu">
		            <c:forEach var="menuItem" items="${menuTT}">
		            	<c:if test="${menuItem.isActive()}">
			        		<c:choose>
								<c:when test="${menuItem.getUrl().equals('')}">
									<!-- opens content of menu item -->
									<li><a href="menu-content?menuId=${menuItem.getId()}">${menuItem.getName()}</a></li>
								</c:when>
								<c:otherwise>
									<!--  opens url of menu item -->
									<c:if test="${menuItem.isPredefinedMenu()}">
										<li><a href="${menuItem.getUrl()}">${menuItem.getName()}</a></li>
									</c:if>
									<c:if test="${!menuItem.isPredefinedMenu()}">
										<li><a href="${menuItem.getUrl()}" target="_blank">${menuItem.getName()}</a></li>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
		        </ul>
		    </li>
	    </c:if>
	    <c:if test="${fn:length(menuMU) != 0}">
		    <li class="menu-item four dropdown">
		        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">Ma vie U<span class="menu-item-icon"><i class="icon"></i></span></a>
		        <ul class="dropdown-menu" role="menu">
		            <c:forEach var="menuItem" items="${menuMU}">
		            	<c:if test="${menuItem.isActive()}">
			        		<c:choose>
								<c:when test="${menuItem.getUrl().equals('')}">
									<!-- opens content of menu item -->
									<li><a href="menu-content?menuId=${menuItem.getId()}">${menuItem.getName()}</a></li>
								</c:when>
								<c:otherwise>
									<!--  opens url of menu item -->
									<c:if test="${menuItem.isPredefinedMenu()}">
										<li><a href="${menuItem.getUrl()}">${menuItem.getName()}</a></li>
									</c:if>
									<c:if test="${!menuItem.isPredefinedMenu()}">
										<li><a href="${menuItem.getUrl()}" target="_blank">${menuItem.getName()}</a></li>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
		        </ul>
		    </li>
	    </c:if>
	</ul>
</nav>