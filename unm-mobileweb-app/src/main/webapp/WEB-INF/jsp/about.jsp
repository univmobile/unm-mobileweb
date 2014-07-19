<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html lang="en" dir="ltr">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="en">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>À propos d’UnivMobile</title>
<link type="text/css" rel="stylesheet" href="${baseURL}/css/styles.css">
</head>
<body id="body-about">
<div class="body">

<div id="div-info">
<p>
UnivMobile
<p>
©2014 UNPIdF
<p>
<!-- e.g. Build #34 — 2014/07/16 15:45 -->
Build ${buildInfo.buildDisplayName} — ${buildInfo.buildId}
<p>
<br>
https://github.com/univmobile/unm-mobileweb
<p>
<!-- e.g. 6fa7922938635ec7dab6f894e7eaea4d1689ee70 -->
${buildInfo.gitCommitId}
</div>

<div id="div-lastFetchedAt">
<!-- e.g. Données récupérées le 16/07/2014 à 19:29:39 -->
Données récupérées
	le ${lastDataRefresh.dayAsString}
	à ${lastDataRefresh.timeAsString}
</div>

<div id="div-fetchNow">
<a href="#">Récupérer les données</a>
</div>

<div id="div-ok">
<a id="link-ok"
	href="${baseURL}/?univ=${selectedUniversityId}">OK</a>
</div>

</div>
</body>
</html>