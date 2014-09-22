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
<body id="body-login" class="classic<c:if test="${err_login}"> error</c:if>">
<form action="${baseURL}/login/classic" method="POST">
<div class="body">

<div class="h1">
<h1 id="h1">UnivMobile</h1>
</div>

<div id="div-info" class="login">
<c:choose>
<c:when test="${err_login}">
	<span class="error">Erreur : identifiant ou <br> mot de passe erronés</span>
</c:when>
<c:otherwise>
	<span class="login">Identifiant spécifique</span>
	<span class="info">Sécurité standard.</span>
</c:otherwise>
</c:choose>
</div>

<div class="label">
<!--
Identifiant
-->
<span class="info">e-mail ou uid</span>
</div>

<div class="input">
<input id="text-login" type="text" name="login" value="${login}">
</div>

<div class="label">
Mot de passe
</div>

<div class="input">
<input id="text-password" type="password" name="password">
</div>

<div id="div-button-login">
<button id="button-login">
	Connexion
</button>
</div>

<div id="div-cancel">
<a id="link-cancel" href="${baseURL}/">
	Annuler
</a>
</div>

</div> <!-- end of div.body -->
</form>
</body>
</html>