<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html lang="fr" dir="ltr">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="en">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Mon profil</title>
<link type="text/css" rel="stylesheet" href="${baseURL}/css/mobile.css">
<style type="text/css">

</style>
<script type="text/javascript" src="${baseURL}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${baseURL}/js/jquery-ui-1.11.1.min.js"></script>
<script type="text/javascript">

	function logout() {
	
		var ok = confirm('Déconnexion de l’application UnivMobile.');
		
		if (ok) {
			window.location.href = '${baseURL}/?logout';
		}
	}
	
</script>
</head>
<body id="body-profile">
<div class="body">

<div class="h1 nav">
<h1>Profil : ${user.uid}</h1>
<div id="div-menu">
<a id="link-menu" href="${baseURL}/">Menu</a>
</div>
</div> <!-- end of div.h1.nav -->

<div id="div-details">
	<div class="field displayName">
		<div class="title">Nom complet</div>
		<div class="content">
			<c:out value="${user.displayName}"/>
		</div>
	</div>
	<div class="field uid">
		<div class="title">Identifiant</div>
		<div class="content">
			<c:out value="${user.uid}"/>
		</div>
	</div>
	<div class="field mail">
		<div class="title">e-mail</div>
		<div class="content">
			<c:out value="${user.mail}"/>
		</div>
	</div>
	<c:forEach var="twitterFollower" items="${user.twitterFollowers}">
	<div class="field twitterFollower">
		<div class="title">
			Twitter Follower:
			<c:out value="${twitterFollower.screenName}"/>
		</div>
		<div class="content">
			<c:out value="${twitterFollower.name}"/>
		</div>
	</div>
	</c:forEach>
</div> <!-- end of #div-details -->

<div id="div-logout">
	<a href="#" onclick="logout()">
		Déconnexion →
	</a>
</div>

</div> <!-- end of div.body -->
</body>
</html>