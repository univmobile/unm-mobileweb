<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html lang="fr" dir="ltr">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="en">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Régions</title>
<link type="text/css" rel="stylesheet" href="${baseURL}/css/mobile.css">
</head>
<body id="body-regions">
<div class="body">

<div class="h1 nav">
<div class="up">
<a href="${baseURL}/regions/?selected=${region.id}">&lt;</a>
</div>
<h1>
${region.label}
</h1>
<div id="div-back">
<a href="${baseURL}/">Retour</a>
</div>
</div> <!-- end of div.h1.nav -->

<div id="div-table">
<c:forEach var="u" items="${universities}">
<div>
<a href="${baseURL}/?region=${region.id}&univ=${u.id}">${u.title}</a>
</div>
</c:forEach>
</div>

</div> <!-- end of div.body -->
</body>
</html>