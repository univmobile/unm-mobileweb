<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page pageEncoding="UTF-8"%> 
<!DOCTYPE html>
<html lang="fr" dir="ltr">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<meta http-equiv="Content-Language" content="en">
<meta name="viewport" content="width=device-width, initial-scale=1.0">
<title>POIs</title>
<link type="text/css" rel="stylesheet" href="${baseURL}/css/mobile.css">
<style type="text/css">

</style>
<script type="text/javascript" src="${baseURL}/js/jquery-1.11.1.min.js"></script>
<script type="text/javascript" src="${baseURL}/js/jquery-ui-1.11.1.min.js"></script>
<!--
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=${API_KEY}"></script>
-->
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js"></script>
<script type="text/javascript">
	
	// 0. GENERATED

	<% pageContext.setAttribute("newLine", "\n"); %>
		
	var pois = [<c:forEach
			var="category" items="${pois}"><c:forEach
			var="poi" items="${category.pois}">{
		name: "${poi.name}",
		lat: ${poi.latitude},
		lng: ${poi.longitude},<c:if test="${poi.address != null and poi.address != ''}">
		address: "${fn:replace(poi.address, newLine, ' ')}",</c:if><c:if
			test="${poi.floor != null and poi.floor != ''}">
		floor: "${poi.floor}",</c:if><c:if test="${poi.openingHours != null and poi.openingHours != ''}">
		openingHours: "${poi.openingHours}",</c:if><c:if test="${poi.phone != null and poi.phone != ''}">
		phone: "${poi.phone}",</c:if><c:if test="${poi.email != null and poi.email != ''}">
		email: "${poi.email}",</c:if><c:if test="${poi.itinerary != null and poi.itinerary != ''}">
		itinerary: "${poi.itinerary}",</c:if><c:if test="${poi.imageUrl != null and poi.imageUrl != ''}">
		imageUrl: "${poi.imageUrl}",
		imageWidth: ${poi.imageWidth},
		imageHeight: ${poi.imageHeight},</c:if><c:if test="${poi.url != null and poi.url != ''}">
		url: "${poi.url}",</c:if>
		markerType: "${poi.markerType}",
		markerIndex: "${poi.markerIndex}",
		id: ${poi.id},
		commentsUrl: "${poi.commentsUrl}"
	},</c:forEach></c:forEach>];

	// 1. DATA
	
	/**
	 *	return the POI with the given id, or null.
	 */
	function getPoiById(poiId) {
	
		console.log('poiId: ' + poiId);
		
		for (var i = 0; i < pois.length; ++i) {
			
			var poi = pois[i];
			
			if (poi.id == poiId) {
			
				return poi;
			}
		}
		
		return null;
	}
	
	// 2. LAYOUT
	
	function resizeHeights() {
	
		var height = $(window).height()
			- $('div.h1.nav').height()
			- $('div.bottom').height();
			
		$('#div-map').css('height', height);
		
		google.maps.event.trigger(map, 'resize');
	}
	
	var markerImageBaseURL = '${baseURLbackend}/img/markers/marker_green';
	var markerImageBaseSize = new google.maps.Size(22, 40);	
	var markerImageSuffix = '.png';	
	var markerImageSize = markerImageBaseSize;
	
	if (window.devicePixelRatio > 1.5) { // Retina display
		markerImageSuffix = '@2x.png';
		markerImageSize = new google.maps.Size(44, 80);
	}

	var markerImageBaseOrigin = new google.maps.Point(0, 0);
	var markerImageBaseAnchor = new google.maps.Point(11, 40);	
	var markerAnchorPoint = new google.maps.Point(5, -28); // For infoWindows
	
	// 3. INTERACTION
	
	var map;
	var infoWindow = null;
	var swallowNextClick = false;
	
	function onfocusPoi(poiId) {

		swallowNextClick = false;

		var poi = getPoiById(poiId);
		
		if (poi != null) {
		
			selectPoi(poi);
							
			var newCenter = new google.maps.LatLng(poi.lat, poi.lng);
				
			if (newCenter.equals(map.getCenter())) {

				if (infoWindow == null || infoWindow.poi.id != poiId) {
				
					swallowNextClick = true; // Do not toggle
				}
			}
				
			openInfoWindow(poi);	
		}
	}
	
	function onclickPoi(poiId) {

		var poi = getPoiById(poiId);
		
		if (poi != null) {
		
			selectPoi(poi);
			
			var newCenter = new google.maps.LatLng(poi.lat, poi.lng);
				
			if (newCenter.equals(map.getCenter())) {

				if (infoWindow != null && infoWindow.poi.id == poiId) {
				
					if (!swallowNextClick) {
						
						infoWindow.close(); // Toggle infoWindow
					
						infoWindow = null;
					}

				} else {
				
					openInfoWindow(poi);	
				}
				
			} else {
				
				map.setCenter(newCenter);
				
				openInfoWindow(poi);	
			}
		}
		
		swallowNextClick = false;
	}

	var selectedPoi;
	
	/**
	 *	select the corresponding POI within the list.
	 */
	function selectPoi(poi) {
	
		selectedPoi = poi;
		
		var tdId = 'td-poi-' + poi.id;
		
		console.log('tdId: ' + tdId);
		
		$('td.poi').each(function() {			
			$(this).toggleClass('selected', $(this).attr('id') == tdId);
		});
	
		var liId = 'li-poiNav-' + poi.id;
		
		$('li.poiNav').each(function() {			
			$(this).toggleClass('selected', $(this).attr('id') == liId);
		});
		
		$('#div-poiNav li.prev').toggleClass('dimmed',
			$('#li-poiNav-' + poi.id).prev('li.poiNav').length == 0);
		$('#div-poiNav li.next').toggleClass('dimmed', 
			$('#li-poiNav-' + poi.id).next('li.poiNav').length == 0);
	}
		
	var detailsMap = null;
	var detailsMarker = null;
	
	function showDetails(poiId) {
		
		var poi = getPoiById(poiId);
		
		if (poi == null) return;
	
		var currentMode = 'list';
		
		if ($('body').hasClass('map')) currentMode = 'map';
		
		// "BACK"
		$('div.h1.nav div.up a').click(function() {
		
			$('div.bottomNav li').removeClass('selected');
			
			$('div.bottomNav li.tab_' + currentMode).addClass('selected');
		
			$('body').removeClass('details list map').addClass(currentMode);
			
			selectPoi(poi);
			
			if (infoWindow != null) {
			
				infoWindow.close();
				
				infoWindow = null;
			}
		});
	
		// UPDATE CONTEXT
		
		$('body').removeClass('list map comments').addClass('details');
		
		$('div.bottomNav li.tab_details').addClass('selected');
		
		// $('#div-details').removeClass('hidden');
		
		$('#div-details div.name').html(poi.name);
		
		$('#div-details div.field.active span.id').html(poi.id);
		
		setDetailsField('floor',        poi.floor);
		setDetailsField('openingHours', poi.openingHours);
		setDetailsField('phone',        poi.phone);
		setDetailsField('address',      poi.address);
		setDetailsField('email',        poi.email);
		setDetailsField('itinerary',    poi.itinerary);
		setDetailsField('url',          poi.url);
		setDetailsField('coordinates',  poi.lat + ',' + poi.lng);
		
		var newCenter = new google.maps.LatLng(poi.lat, poi.lng);
		var newZoom = 16;
		
		map.setCenter(newCenter);
		
		if (detailsMap == null) {
		
			var mapOptions = {
				center: newCenter,
				zoom: newZoom
			};
		
			detailsMap = new google.maps.Map(
				document.getElementById('div-detailsMap'), mapOptions
			);
		}

		detailsMap.setCenter(newCenter);
		detailsMap.setZoom(newZoom);

		if (detailsMarker != null) detailsMarker.setMap(null);
		
		detailsMarker = new google.maps.Marker({
			position: newCenter,
			map: detailsMap,
			title: poi.name,
			xanchorPoint: markerAnchorPoint
		});
	}
	
	function setDetailsField(fieldName, content) {
	
		$('#div-details div.field.' + fieldName)
			.toggleClass('hidden', content == null)
			.children('div.content').html(content);
	}

	function showComments(poiId) {
		
		var poi = getPoiById(poiId);
		
		if (poi == null) return;
	
		var currentMode = 'list';
		
		if ($('body').hasClass('map')) currentMode = 'map';
		
		// "BACK"
		$('div.h1.nav div.up a').click(function() {
		
			$('div.bottomNav li').removeClass('selected');
			
			$('div.bottomNav li.tab_' + currentMode).addClass('selected');
		
			$('body').removeClass('comments details list map').addClass(currentMode);
			
			selectPoi(poi);
		});
	
		// UPDATE CONTEXT
		
		$('body').removeClass('list map details').addClass('comments');
		
		$('div.bottomNav li.tab_comments').addClass('selected');
		
		// $('#div-comments').removeClass('hidden');
		
		$('#div-comments div.name').html(poi.name);
		
		//$('#div-comments div.field.active span.id').html(poi.id);		
	}
	
	function getSelectedLiPoiId() {
	
		var lis = $('li.poiNav.selected');
		
		return lis.length == 0 ? null : lis[0].id;
	}
	
	function selectPrevPoi() {
		
		var liId = getSelectedLiPoiId();
		
		if (liId == null) return;
		
		for (var i = 0; i < pois.length; ++i) {
		
			var poi = pois[i];
			
			if (liId == 'li-poiNav-' + poi.id) {
			
				if (i > 0) onfocusPoi(pois[i - 1].id);
				
				break;
			}
		}
	}

	function selectNextPoi() {
		
		var liId = getSelectedLiPoiId();
		
		if (liId == null) return;
		
		for (var i = 0; i < pois.length; ++i) {
		
			var poi = pois[i];
			
			if (liId == 'li-poiNav-' + poi.id) {
			
				if (i + 1 < pois.length) onfocusPoi(pois[i + 1].id);
				
				break;
			}
			
			prev = poi;
		}
	}
	
	/**
	 *	open the corresponding infoWindow on the map.
	 */
	function openInfoWindow(poi) {
	
		if (infoWindow != null) {
			
			if (infoWindow.poi.id == poi.id) return;
			
			infoWindow.close();
		}
		
		var div = $('#div-hidden div.infoWindow').clone();
		
		div.attr('id', 'div-infoWindow-' + poi.id);
		div.children('span.name').html(poi.name);
		div.children('span.address').html(poi.address);
		div.click(function() { showDetails(poi.id); });
		
		var imgDiv = div.children('div');
				
		if (poi.imageUrl == null) {
			imgDiv.addClass('hidden');
		} else {
			imgDiv.removeClass('hidden');
			var img = imgDiv.children('img');
			img.attr('src', poi.imageUrl);
			if (poi.imageWidth < poi.imageHeight) {
				img.removeClass('height100').addClass('width100');				
			} else {
				img.removeClass('width100').addClass('height100');
			}
		}
		
		div = div[0];
		
		infoWindow = new google.maps.InfoWindow({
			content: div
		});
		
		infoWindow.poi = poi;

		google.maps.event.addListener(infoWindow, 'closeclick', function() {
			infoWindow = null;
		});
		
		infoWindow.open(map, poi.marker);
	}
	
	function loadPoiComments(poi) {
	
		var timeline = $('#div-comments-timeline');
		
		console.log('poi: ' + poi);
		
		console.log('poi.commentsUrl: ' + poi.commentsUrl);
		
		if (poi == null) {
		
			timeline.html('');
			
			return;
		}
		
		$.ajax({
			url: poi.commentsUrl,
			dataType: 'json',
			error: function(jqXHR, textStatus, errorThrown) {

				var divError = $(document.createElement('div'));
				var divErrorThrown = $(document.createElement('div'));
					
				divError.addClass('error').html(
					'Une erreur s’est produite lors de la récupération des commentaires.'
				);
				
				divError.append(divErrorThrown);
				
				divErrorThrown.addClass('errorThrown').html(
					textStatus + ': ' + errorThrown
				);
				
				timeline.html(divError);
			},
			success: function(json) {
				
				var comments = json.comments;
				
				// console.log('setComments');
			
				var ul = $(document.createElement('ul'));
				
				for (var i = 0; i < comments.length; ++i) {
				
					var comment = comments[i];
					
					var li = $(document.createElement('li'));
					
					ul.append(li);
					
					var imgProfileImage = $(document.createElement('img'));
					var divDisplayName = $(document.createElement('div'));
					var divUsername = $(document.createElement('div'));
					var divText = $(document.createElement('div'));
					var divTimestamp = $(document.createElement('div'));
					var anchorTimestamp = $(document.createElement('a'));
					
					li.append(imgProfileImage);
					li.append(divDisplayName);
					li.append(divUsername);
					li.append(divText);
					li.append(divTimestamp);
					
					imgProfileImage.addClass('profileImage');
					if (comment.author.profileImage != null) {
						imgProfileImage.attr('src', comment.author.profileImage.url);
					}
					divDisplayName.addClass('displayName')
						.html(comment.author.displayName);
					divUsername.addClass('username')
						.html('@' + comment.author.username);
					divTimestamp.addClass('timestamp')
						.append(anchorTimestamp);
					anchorTimestamp.attr('href', comment.url)
						.attr('title', comment.postedAt)
						.html(comment.displayPostedAt);
						
					var message = comment.text;
					
					divText.addClass('text').html(message);
				}
				
				timeline.html(ul); // Erase previous content
			}
		});
	}

	// 8. INIT
	
	function initialize() {
	
		// 0. POIS
		
		if (pois.length > 0) selectPoi(pois[0]);
			
		// 1. MAIN COMPONENTS
		
		var mapOptions = {
			center: new google.maps.LatLng(${map.center}),
			zoom: ${map.zoom}
		};
		
		map = new google.maps.Map(
			document.getElementById('div-map'), mapOptions
		);

		// 2. MAIN COMPONENTS BEHAVIOUR

		// 3. MAIN COMPONENTS LAYOUT
		
		resizeHeights();
		
		// 4. GOOGLE MAP MARKERS
		
		for (var i = 0; i < pois.length; ++i) {
		
			var poi = pois[i];
			var lat = poi.lat;
			var lng = poi.lng;
			var name = poi.name;

			var markerImage = {
				url: markerImageBaseURL + poi.markerIndex + markerImageSuffix,
				size: markerImageSize,
				scaledSize: markerImageBaseSize,
				origin: markerImageBaseOrigin,
				anchor: markerImageBaseAnchor
			};

			var marker = new google.maps.Marker({
				position: new google.maps.LatLng(lat, lng),
				map: map,
				title: name,
				xicon: markerImage,
				anchorPoint: markerAnchorPoint
			});
			
			poi.marker = marker;
			
			google.maps.event.addListener(marker, 'click', 
				// Use a closure to create a safe scope for (poi, marker)
				(function(poi) { return function() {
					
					selectPoi(poi);
					
					if (infoWindow != null && infoWindow.poi.id == poi.id) {
					
						infoWindow.close(); // Toggle infoWindow
						
						infoWindow = null;
						
					} else {
					
						openInfoWindow(poi);
					}
					
				}})(poi)
			);
		}
		
		// 5. INTERACTIONS
		
		$('div.bottomNav li.tab_list').click(function() {
			$('div.bottomNav li.tab_list').addClass('selected');
			$('div.bottomNav li.tab_map').removeClass('selected');
			$('body').removeClass('map details comments').addClass('list');
		});
	
		$('div.bottomNav li.tab_map').click(function() {
			$('div.bottomNav li.tab_list').removeClass('selected');
			$('div.bottomNav li.tab_map').addClass('selected');
			$('body').removeClass('list details comments').addClass('map');
			resizeHeights();
		});

		$('div.bottomNav li.tab_details').click(function() {
			$('div.bottomNav li.tab_details').addClass('selected');
			$('div.bottomNav li.tab_comments').removeClass('selected');
			$('body').removeClass('map list comments').addClass('details');
		});

		$('div.bottomNav li.tab_comments').click(function() {
			$('div.bottomNav li.tab_details').removeClass('selected');
			$('div.bottomNav li.tab_comments').addClass('selected');
			$('body').removeClass('map list details').addClass('comments');
			loadPoiComments(selectedPoi);
		});

		// 9. END
		
		<c:if test="${not empty selectedPoiId}"> <!-- used in devel tests -->
			selectPoi(getPoiById(${selectedPoiId}));
		<c:choose>
		<c:when test="${mode == 'details'}">
			showDetails(${selectedPoiId});
		</c:when>
		<c:when test="${mode == 'comments'}">
			console.log('comments');
			loadPoiComments(selectedPoi);
			showComments(${selectedPoiId});
		</c:when>
		</c:choose>
		</c:if>
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);
	
</script>
</head>
<body id="body-pois" class="${mode}">
<div class="body">

<div class="h1 nav">
<div class="up">
<a href="#" onclick="$('body').removeClass('details map').addClass('list');"
	id="link-up">&lt;</a>
</div>
<h1>POIs</h1>
<div id="div-back">
<a id="link-back" href="${baseURL}/">Retour</a>
</div>
</div> <!-- end of div.h1.nav -->

<table class="pois">
<tbody>
<c:forEach var="category" items="${pois}">
<tr class="category">
<th>
	${category.name}
</th>
</tr>
<c:forEach var="poi" items="${category.pois}">
<tr>
<td id="td-poi-${poi.id}" class="poi">
	<a id="link-poi-${poi.id}" href="#" onclick="showDetails(${poi.id});">
		${poi.name}
		<c:if test="${poi.address != null}">
		<br>
		<span class="address">
			${poi.address}
		</span>
		</c:if>
	</a>
</td>
</tr>
</c:forEach>
</c:forEach>
</tbody>
</table> <!-- end of table.pois -->

<div id="div-map"></div>

<div id="div-details">
	<div class="field name">
	</div>
	<!--
	<div class="field active">
		<div class="title">Statut</div>
		<div class="content">
			<span>POI </span><span class="id"></span>
			<label for="radio-active-yes">Actif</label>
			<input type="radio" name="active" value="yes" checked
				id="radio-active-yes" disabled>
			<label for="radio-active-no">Inactif</label>
			<input type="radio" name="active" value="no"
				id="radio-active-no" disabled>
		</div>
	</div>	
	-->
	<div class="field floor">
		<div class="title">Emplacement</div>
		<div class="content"></div>
	</div>
	<div class="field openingHours">
		<div class="title">Horaires</div>
		<div class="content"></div>
	</div>	
	<div class="field phone">
		<div class="title">Téléphone</div>
		<div class="content"></div>
	</div>	
	<div class="field address">
		<div class="title">Adresse</div>
		<div class="content"></div>
	</div>	
	<div class="field email">
		<div class="title">E-mail</div>
		<div class="content"></div>
	</div>	
	<div class="field itinerary">
		<div class="title">Accès</div>
		<div class="content"></div>
	</div>	
	<div class="field url">
		<div class="title">Site web</div>
		<div class="content"></div>
	</div>	
	<!--
	<div class="field coordinates">
		<div class="title">Coordonnées Lat/Lng</div>
		<div class="content"></div>
	</div>	
	-->
	<div class="field map" id="div-detailsMap">		
	</div>
</div> <!-- end of #div-details -->

<div id="div-comments">

	<div class="field name"></div>
	
	<div id="div-comments-timeline"></div>
	
	<!--
	<div id="div-comments-buttons">
	
	<button id="button-addComment" onclick="openAddCommentDialog(); return false;">
		Ajouter un commentaire…
	</button>
	
	</div>
	-->
	<!-- end of #div-comments-buttons -->
	
</div> <!-- end of #div-comments -->

<div class="bottom">

<div id="div-poiNav">
<ul>
<li class="prev"> <a href="#" onclick="selectPrevPoi();">&lt;</a>
<li class="next"> <a href="#" onclick="selectNextPoi();">&gt;</a>
<c:forEach var="category" items="${pois}">
<c:forEach var="poi" items="${category.pois}">
<li id="li-poiNav-${poi.id}" class="poiNav">
	<a id="link-poiNav-${poi.id}" href="#" onclick="onclickPoi(${poi.id});">
		${poi.name}
	<br>
		<span class="address">
		<c:choose>
		<c:when test="${poi.address != null}">
			${poi.address}
		</c:when>
		<c:otherwise>
			&#160;
		</c:otherwise>
		</c:choose>
	</a>
</c:forEach>
</c:forEach>
</ul>
</div> <!-- end of #div-poiNav -->

<div class="details bottomNav">
<ul>
	<li class="tab_details<c:if test="${mode == 'details'}"> selected</c:if>">
		<a href="#" id="link-tab_details">
			Détails
		</a>
	<li class="tab_comments<c:if test="${mode == 'comments'}"> selected</c:if>">
		<a href="#" id="link-tab_comments">
			Commentaires
		</a>
</ul>
</div> <!-- end of div.detailsBottomNav -->

<div class="list bottomNav">
<ul>
	<li class="tab_list<c:if test="${mode == 'list'}"> selected</c:if>">
		<a href="#" id="link-tab_list">
			Liste
		</a>
	<li class="tab_map<c:if test="${mode == 'map'}"> selected</c:if>">
		<a href="#" id="link-tab_map">
			Plan
		</a>
</ul>
</div> <!-- end of div.bottomNav -->

</div> <!-- end of div.bottom -->

</div> <!-- end of div.body -->

<div id="div-hidden" style="display: none;">
<div class="infoWindow">
<div class="img">
	<img>
</div>
<span class="name">
</span>
<br>
<span class="address">
</span>
</div> <!-- end of #div-infoWindow -->
</div> <!-- end of div[@style = 'display: none;'] -->

</body>
</html>