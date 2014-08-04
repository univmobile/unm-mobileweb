<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html lang="fr" dir="ltr">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="en">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>Régions</title>
<link type="text/css" rel="stylesheet" href="${baseURL}/css/styles.css">
</head>
<body id="body-regions">
<div class="body">

<div class="h1">
<h1>Régions</h1>
<div id="div-back">
<a id="link-back" href="${baseURL}/">Retour</a>
</div>
</div>

<table>
<tbody>
<c:forEach var="r" items="${regions}">
<tr>
<td>
<a id="link-region-${r.id}"
	href="${baseURL}/regions/?region=${r.id}">${r.label}</a>
</td>
</tr>
</c:forEach>
</tbody>
</table>

</div>
</body>
</html>