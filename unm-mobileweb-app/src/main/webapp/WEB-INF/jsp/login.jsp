<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html lang="fr" dir="ltr">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="en">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>UnivMobile — Connexion</title>
<link type="text/css" rel="stylesheet" href="${baseURL}/css/mobile.css">
</head>
<body id="body-login">
<div class="body">

<div class="h1">
<h1 id="h1">Connexion</h1>
</div>

<c:choose>
<c:when test="${shibbolethEnabled}">
<div id="div-shibboleth" class="login">
<a id="link-shibboleth"
	href="${baseURL}/login/shibboleth/">
<span class="login">Sécurité Shibboleth…</span>
<span class="info">
	Sécurité renforcée :
	<br>
	<strong>
	${selectedUniversityLabel}
	</strong>
</span>
</a>
</div>
</c:when>
<c:when test="${not empty selectedUniversityLabel}">
<div id="div-shibboleth" class="login error">
<span class="login">Sécurité Shibboleth…</span>
<span class="info">
	Non disponible :
	<br>
	<strong>
	${selectedUniversityLabel}
	</strong>
</span>
</div>
</c:when>
<c:otherwise>
<div id="div-shibboleth" class="login error">
<span class="login">Sécurité Shibboleth…</span>
<span class="info">
	Sécurité renforcée :
	vous devez sélectionner une université.
</span>
</div>
</c:otherwise>
</c:choose>

<div id="div-classic" class="login">
<a id="link-classic"
	href="${baseURL}/login/classic/">
<span class="login">Identifiant générique…</span>
<span class="info">Sécurité standard.</span>
</a>
</div>

<div id="div-cancel">
<a id="link-cancel" href="${baseURL}/">
	Annuler
</a>
</div>

</div> <!-- end of div.body -->
</body>
</html>