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
<script src="//code.jquery.com/ui/1.11.1/jquery-ui.js"></script>
<!--
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js?key=${API_KEY}"></script>
-->
<script type="text/javascript" src="https://maps.googleapis.com/maps/api/js"></script>
<script type="text/javascript">
	
	// 0. GENERATED
	
	var pois = [<c:forEach
			var="category" items="${pois}"><c:forEach
			var="poi" items="${category.pois}">{
		name: "${poi.name}",
		lat: ${poi.latitude},
		lng: ${poi.longitude},<c:if test="${poi.address != null and poi.address != ''}">
		address: "${poi.address}",</c:if><c:if test="${poi.floor != null and poi.floor != ''}">
		floor: "${poi.floor}",</c:if><c:if test="${poi.openingHours != null and poi.openingHours != ''}">
		openingHours: "${poi.openingHours}",</c:if><c:if test="${poi.phone != null and poi.phone != ''}">
		phone: "${poi.phone}",</c:if><c:if test="${poi.email != null and poi.email != ''}">
		email: "${poi.email}",</c:if><c:if test="${poi.itinerary != null and poi.itinerary != ''}">
		itinerary: "${poi.itinerary}",</c:if><c:if test="${poi.image != null and poi.image != ''}">
		image: "${poi.image}",
		imageWidth: ${poi.imageWidth},
		imageHeight: ${poi.imageHeight},</c:if><c:if test="${poi.url != null and poi.url != ''}">
		url: "${poi.url}",</c:if>
		markerType: "${poi.markerType}",
		markerIndex: "${poi.markerIndex}",
		id: ${poi.id}
	},</c:forEach></c:forEach>];

	// 1. LAYOUT
	
	function resizeHeights() {
	
		var height = $(window).height()
			- $('div.h1.nav').height()
			- $('div.bottom').height();
			
		$('#div-map').css('height', height);
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

		for (var i = 0; i < pois.length; ++i) {
			
			var poi = pois[i];
			
			if (poi.id == poiId) {
		
				selectPoi(poi);
							
				var newCenter = new google.maps.LatLng(poi.lat, poi.lng);
				
				if (newCenter.equals(map.getCenter())) {

					if (infoWindow == null || infoWindow.poi.id != poiId) {
				
						swallowNextClick = true; // Do not toggle
					}
				}
				
				openInfoWindow(poi);	
				
				break;
			}
		}
	}
	
	function onclickPoi(poiId) {

		for (var i = 0; i < pois.length; ++i) {
			
			var poi = pois[i];
			
			if (poi.id == poiId) {
		
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
				
				break;
			}
		}
		
		swallowNextClick = false;
	}

	/**
	 *	select the corresponding POI within the list.
	 */
	function selectPoi(poi) {
	
		var tdId = 'td-poi-' + poi.id;
		
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
		
		var poi = null;
		
		for (var i = 0; i < pois.length; ++i) {
		
			if (pois[i].id == poiId) {
			
				poi = pois[i];
				
				break;
			}
		}
		
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
		
		$('body').removeClass('list map').addClass('details');
		
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
		
		div.children('span.name').html(poi.name);
		div.children('span.address').html(poi.address);
		div.click(function() { showDetails(poi.id); });
		
		var imgDiv = div.children('div');
				
		if (poi.image == null) {
			imgDiv.addClass('hidden');
		} else {
			imgDiv.removeClass('hidden');
			var img = imgDiv.children('img');
			img.attr('src', poi.image);
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
			$('body').removeClass('map details').addClass('list');
		});
	
		$('div.bottomNav li.tab_map').click(function() {
			$('div.bottomNav li.tab_list').removeClass('selected');
			$('div.bottomNav li.tab_map').addClass('selected');
			$('body').removeClass('list details').addClass('map');
		});

		// 9. END
	}
	
	google.maps.event.addDomListener(window, 'load', initialize);
	
</script>
</head>
<body id="body-pois" class="${mode}">
<div class="body">

<div class="h1 nav">
<div class="up">
<a href="#" onclick="$('body').removeClass('details map').addClass('list');">&lt;</a>
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
</table>

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
</div>

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
	<c:if test="${poi.address != null}">
		<br>
		<span class="address">
			${poi.address}
		</span>
	</c:if>
	</a>
</c:forEach>
</c:forEach>
</ul>
</div> <!-- end of #div-poiNav -->

<div class="details bottomNav">
<ul>
	<li class="tab_details<c:if test="${mode == 'details'}"> selected</c:if>">
		<a href="#">
			Détails
		</a>
	<li class="tab_comments<c:if test="${mode == 'comments'}"> selected</c:if>">
		<a href="#">
			Commentaires
		</a>
</ul>
</div> <!-- end of div.detailsBottomNav -->

<div class="list bottomNav">
<ul>
	<li class="tab_list<c:if test="${mode == 'list'}"> selected</c:if>">
		<a href="#">
			Liste
		</a>
	<li class="tab_map<c:if test="${mode == 'map'}"> selected</c:if>">
		<a href="#">
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