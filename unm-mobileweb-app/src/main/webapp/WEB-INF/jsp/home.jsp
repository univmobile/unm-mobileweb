<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html lang="fr" dir="ltr">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="en">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>UnivMobile</title>
<link type="text/css" rel="stylesheet" href="${baseURL}/css/mobile.css">
</head>
<body id="body-home">
<div class="body">

<div id="div-about">
<a id="link-about"
	href="${baseURL}/about/?univ=${selectedUniversityId}">À propos…</a>
</div>

<div id="div-login">
<c:choose>
<c:when test="${empty user}">
	<a id="link-login"
		href="${baseURL}/login/?univ=${selectedUniversityId}">Se connecter…</a>
</c:when>
<c:otherwise>
	<a id="link-profile"
		href="${baseURL}/profile/">Profil : ${user.uid}</a>
</c:otherwise>
</c:choose>
</div>

<div class="h1">
<h1 id="h1-title">UnivMobile</h1>
</div>

<div id="div-selectedUniversity">
<c:choose>
<c:when test="${selectedUniversityId != null}">
	${selectedUniversityLabel}
</c:when>
<c:otherwise>
	Aucune université sélectionnée
</c:otherwise>
</c:choose>
</div>

<div id="div-choisir">
<a id="link-choisir"
	href="${baseURL}/regions/?univ=${selectedUniversityId}">Choisir…</a>
</div>

<div id="div-gotoGeocampus">
<div>
	<a id="link-geocampus"
	   href="${baseURL}/geocampus">Géocampus</a>
</div>
</div>

</div> <!-- end of div.body -->
</body>
</html>