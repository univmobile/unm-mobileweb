<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<nav class="navbar-menu" id="mobile-nav">
	<header>
		<c:choose>
			<c:when test="${not empty universityLogo}">
				<a class="universite-logo" href="./"><img class="img-responsive" alt="Universite Paris" src="http://univmobile-dev.univ-paris1.fr/testSP/files/universitieslogos/${universityLogo}"></a>
			</c:when>
			<c:otherwise>
				<a class="universite-logo" href="./"></a>
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
		        		<c:if test="${menuItem.active}">
			        		<c:choose>
								<c:when test="${empty menuItem.url}">
									<!-- opens content of menu item -->
									<li><a href="menu-content?menuId=${menuItem.id}">${menuItem.name}</a></li>
								</c:when>
								<c:otherwise>
									<!--  opens url of menu item -->
									<c:if test="${menuItem.predefinedMenu}">
										<li><a href="${menuItem.url}">${menuItem.name}</a></li>
									</c:if>
									<c:if test="${not menuItem.predefinedMenu}">
										<li><a href="${menuItem.url}" target="_blank">${menuItem.name}</a></li>
									</c:if>
								</c:otherwise>
							</c:choose>
						</c:if>
					</c:forEach>
		        </ul>
		    </li>
	    </c:if>
	    <c:if test="${fn:length(menuAU) != 0}">
		    <li class="menu-item two dropdown">
		            <c:forEach var="menuItem" items="${menuAU}">
		        		<c:if test="${menuItem.id == 49}">
					        <a class="dropdown-toggle" role="button" href="${menuItem.url}">${menuItem.name}<span class="menu-item-icon"><i class="icon"></i></span></a>
						</c:if>
					</c:forEach>
		    </li>
	    </c:if>
	    <c:if test="${fn:length(menuTT) != 0}">
		    <li class="menu-item three dropdown">
		        <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">Tou'Trouver<span class="menu-item-icon"><i class="icon"></i></span></a>
		        <ul class="dropdown-menu" role="menu">
		            <c:forEach var="menuItem" items="${menuTT}">
		            	<c:if test="${menuItem.active}">
			        		<c:choose>
								<c:when test="${empty menuItem.url}">
									<!-- opens content of menu item -->
									<li><a href="menu-content?menuId=${menuItem.id}">${menuItem.name}</a></li>
								</c:when>
								<c:otherwise>
									<!--  opens url of menu item -->
									<c:if test="${menuItem.predefinedMenu}">
										<li><a href="${menuItem.url}">${menuItem.name}</a></li>
									</c:if>
									<c:if test="${not menuItem.predefinedMenu}">
										<li><a href="${menuItem.url}" target="_blank">${menuItem.name}</a></li>
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
		            	<c:if test="${menuItem.active}">
			        		<c:choose>
								<c:when test="${empty menuItem.url}">
									<!-- opens content of menu item -->
									<li><a href="menu-content?menuId=${menuItem.id}">${menuItem.name}</a></li>
								</c:when>
								<c:otherwise>
									<!--  opens url of menu item -->
									<c:if test="${menuItem.predefinedMenu}">
										<li><a href="${menuItem.url}">${menuItem.name}</a></li>
									</c:if>
									<c:if test="${not menuItem.predefinedMenu}">
										<li><a href="${menuItem.url}" target="_blank">${menuItem.name}</a></li>
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