<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix="escape" uri="/WEB-INF/tld/commons-lang.tld"%>
<%@ page pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="fr">
<jsp:include page="includes/maps/maps-head.jsp" />
<body>
	<div class="mask"></div>
	<jsp:include page="includes/nav.jsp" />
	<div class="main-wrap">
		<jsp:include page="includes/maps/maps-header.jsp" />
		<div class="content-wrap geo category-one">
			<div class="main-container container">
				<div class="map" style="height:300px;" id="map-canvas"></div>


<div class="poi-wrap" style="display:none;">
    <div class="poi-screen">
        <div class="poi-header">
            <span class="category-icon category-7">
				<img class="icon text-hide" id="poiBlockIcon" src="" alt="" style="background-image: none;">
            	<!-- <i class="icon text-hide"></i> -->
            </span>
            <div class="title"></div>
            <button class="btn show-hide-poi hide-poi"><i class="icon text-hide"></i></button>
        </div>
        <div class="poi-info">
            <address><i class="fa fa-map-marker fa-fw"></i><span id="addressPOI"></span></address>
            <div class="phone"><i class="fa fa-phone fa-fw"></i><span id="phonePOI"></span></div>
            <div class="email"><i class="fa fa-envelope fa-fw"></i><span id="emailPOI"></span></div>
        </div>
        <div id="poiTabPanel" class="tabpanel" role="tabpanel">
            <ul id="poiTabs" class="nav nav-tabs" role="tablist">
                <li role="presentation" class="one active"><a href="#plan" aria-controls="plan" role="tab" data-toggle="tab"><i class="fa fa-info-circle"></i></a></li>
                <li id="commentsTab" role="presentation" class="two"><a href="#comments" aria-controls="comments" role="tab" data-toggle="tab"><i class="fa fa-comments"></i></a></li>
                <li id="menuTab" role="presentation" class="three"><a href="#menu" aria-controls="menu" role="tab" data-toggle="tab"><i class="fa fa-cutlery"></i></a></li>
            </ul>
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane fade in active" id="plan">
                    <div class="body"></div>
                    <button class="btn submit show-hide-search show-search-long" onclick="document.location.href='university-map'"><i class="icon arrow"></i>Retour à Géo'Campus</button>
                </div>
            </div>
        </div>
    </div>
</div>


				<div class="category-wrap">
					<div class="bottom-buttons" id="univBottomButtons">
						<div class="category-buttons row">
                              <c:if test="${not isIDF}">
								<a href="university-map" class="active category-link one col-xs-12"><i class="icon"></i></a>
                          	  </c:if>
                              <c:if test="${isIDF}">
                                <a href="university-map" class="active category-link one col-xs-4"><i class="icon"></i></a>
                              	<a href="paris-map" class="category-link two col-xs-4"><i class="icon"></i></a>
                              	<a href="goodplans-map" class="category-link three col-xs-4"><i class="icon"></i></a>
                              </c:if>
						</div>
					</div>
				</div>
			</div>
		</div>
	</div>

	<!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
	<script
		src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
	<script
		src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
	<script src="./js/bootstrap.min.js"></script>
	<script src="./js/jquery.customSelect.min.js"></script>
	<script src="./js/script.js"></script>

	<!-- Google Maps -->
	<script type="text/javascript"
		src="https://maps.googleapis.com/maps/api/js?libraries=geometry&sensor=false&key=${API_KEY}">
		
	</script>
	<script src="./js/mapscripts.js"></script>
	<script type="text/javascript">
	  // This example adds a UI control allowing users to remove the
	  // ground overlay from the map.

	  var imageMap;
	  var map;
	  var minZoomLevel = 5;
	  
		function openPoi(markerItem) {

			$('.poi-wrap .title').empty();
			$('.poi-wrap .title').append(markerItem.namePOI);

			$('.poi-wrap .body').empty();
			if (markerItem.descriptionPOI) {
				$('.poi-wrap .body').append('<p>'+markerItem.descriptionPOI+'</p>');
			}
			if (markerItem.website) {
				$('.poi-wrap .body').append('<p><strong>Site web : </strong> <a href="' + markerItem.website + '" target="blank">' + markerItem.website + '</a></p>');
			}
			if (markerItem.publicWelcome) {
				$('.poi-wrap .body').append('<p><strong>Public accueillis : </strong> '+markerItem.publicWelcome+'</p>');
			}
			if (markerItem.disciplines) {
				$('.poi-wrap .body').append('<p><strong>Disciplines : </strong> '+markerItem.disciplines+'</p>');
			}
			if (markerItem.openingHours) {
				$('.poi-wrap .body').append('<p><strong>Horaires et jours d\'ouverture : </strong> '+markerItem.openingHours+'</p>');
			}
			if (markerItem.closingHours) {
				$('.poi-wrap .body').append('<p><strong>Horaires et jours de fermeture : </strong> '+markerItem.closingHours+'</p>');
			}
			if (markerItem.floorPOI) {
				$('.poi-wrap .body').append('<p><strong>Emplacement : </strong> '+markerItem.floorPOI+'</p>');
			}
			if (markerItem.itinerary) {
				$('.poi-wrap .body').append('<p><strong>Accès : </strong> '+markerItem.itinerary+'</p>');
			}
			
			$('#addressPOI').empty();
			$('#addressPOI').append(markerItem.addressPOI);

			$('#phonePOI').empty();
			$('#phonePOI').append('<a href="tel:' + markerItem.phonesPOI  + '">' + markerItem.phonesPOI + '</a>');

			$('#emailPOI').empty();
			$('#emailPOI').append('<a href="mailto:' + markerItem.emailPOI + '">' + markerItem.emailPOI + '</a>');

			if (markerItem.categoryIdPOI == "${restaurationUniversitaireCategoryId}") {
				$('#menuTab').show();
				$('#menu').show();
				getRestaurantMenus(markerItem.idPOI);
			} else {
				$('#menuTab').hide();
				$('#menu').hide();
				$('#poiTabs a:first').tab('show') //select first tab
			}

			$('#commentsTab').hide();

			if (!$('.poi-wrap').hasClass('open')) {
				$('.poi-wrap').toggle("slide", {
					direction : "down"
				});
				$('.poi-wrap').toggleClass('open');
			}
			
			if (markerItem.categoryImagePOI != "") {
				$('#poiBlockIcon').attr("src", markerItem.categoryImagePOI);
			} else {
				$('#poiBlockIcon').attr("src", "");
			}

			addPoiIdHash(markerItem.idPOI);
			
			$('#poiIdInputFieldForBookmark').val(markerItem.idPOI);
		}

	  function initialize() {
		var mapHeight = $(window).height() - ($('#univHeader').height() + $('#univBottomButtons').height() + 24);
		$('#map-canvas').height(mapHeight);
		
		var imageHeight = ${imageMap.height};
		var imageWidth = ${imageMap.width};
	    var mapCenter = new google.maps.LatLng(0, 0);
		var north = new google.maps.geometry.spherical.computeOffset(mapCenter, imageHeight/2, 0); 
		var south  = new google.maps.geometry.spherical.computeOffset(mapCenter, imageHeight/2, 180); 
		var east = new google.maps.geometry.spherical.computeOffset(mapCenter, imageWidth/2, 90); 
		var west  = new google.maps.geometry.spherical.computeOffset(mapCenter, imageWidth/2, 270); 
		var southWest = new google.maps.LatLng(south.lat(), west.lng());
		var northEast = new google.maps.LatLng(north.lat(), east.lng())
		
	    var imageBounds = new google.maps.LatLngBounds(
	        southWest,
	        northEast);
			
		var customMapTypeOptions = {
		        getTileUrl: function(coord, zoom) {
		            return null;
		        },
		        tileSize: new google.maps.Size(256, 256),
		        maxZoom: 20,
		        minZoom: 15,
		        radius: 0,
		        name: 'Image'
		    };
	    var customMapType = new google.maps.ImageMapType(customMapTypeOptions);

	    var myLatlng = new google.maps.LatLng(0, 0);
	    var size = new google.maps.Size(1200, 1200); //FIXME: imagen
	    var optimalZoom = zoomLevelByDiag(size);
	    var mapOptions = {
	        center: myLatlng,
	        zoom: optimalZoom,
	        disableDoubleClickZoom: true,
	        streetViewControl: false,
	        mapTypeControlOptions: {
	            mapTypeIds: ['image']
	        }
	    };

	    
	    map = new google.maps.Map(document.getElementById('map-canvas'),
	        mapOptions);
		map.mapTypes.set('image', customMapType);
		map.setMapTypeId('image');
		
		map.fitBounds(imageBounds);
		markersTemp = [];
		<c:forEach var="poiItem" items="${imageMap.pois}">
		var marker = new google.maps.Marker({
		      position: new google.maps.LatLng(${poiItem.lat}, ${poiItem.lng}),
		      map: map,
		      title: "${escape:javaScript(poiItem.name)}",
				<c:if test="${not empty poiItem.category.markerIconUrl}">icon : {
				    url: "${categoriesIconsUrl}${poiItem.category.markerIconUrl}",
				    scaledSize: new google.maps.Size(38, 38) },</c:if>
				//below are custom poi values, not required for maps.Marker
				idPOI: "${poiItem.id}",
				namePOI: "${escape:javaScript(poiItem.name)}",
				descriptionPOI: "${escape:javaScript(poiItem.description)}",
				addressPOI: "${escape:javaScript(poiItem.address)}",
				floorPOI: "${escape:javaScript(poiItem.floor)}",
				phonesPOI: "${escape:javaScript(poiItem.phones)}",
				emailPOI: "${escape:javaScript(poiItem.email)}",
				categoryIdPOI: "${poiItem.categoryId}",
				itinerary: "${escape:javaScript(poiItem.itinerary)}",
				publicWelcome: "${escape:javaScript(poiItem.publicWelcome)}",
				disciplines: "${escape:javaScript(poiItem.disciplines)}",
				openingHours: "${escape:javaScript(poiItem.openingHours)}",
				closingHours: "${escape:javaScript(poiItem.closingHours)}",
				website: "${escape:javaScript(poiItem.url)}",
				<c:if test="${not empty poiItem.category.activeIconUrl}">categoryImagePOI: "${categoriesIconsUrl}${poiItem.category.activeIconUrl}"</c:if>
		  });
		google.maps.event.addListener(marker, 'click', function() {
			openPoi(this);
		});

		markersTemp.push(marker);
		</c:forEach>

	    imageMap = new google.maps.GroundOverlay(
	        '${imageMap.url}',
	        imageBounds);

	    addOverlay();
		
		var lastValidCenter = map.getCenter();
		google.maps.event.addListener(map, 'center_changed', function() {
	        if (imageBounds.contains(map.getCenter())) {
	            lastValidCenter = map.getCenter();
	            return; 
	        }   
	        map.panTo(lastValidCenter);
	    });  
	  }

	  function addOverlay() {
	      imageMap.setMap(map);
	  }
	  
	  function diagLenght(size) {
	      return Math.sqrt(Math.pow(size.height, 2) + Math.pow(size.width, 2));
	  }
	  
	  function zoomLevelByDiag(size) {
	      var diag = diagLenght(size);
	      if (diag < 500) {
	          return 19;
	      } else if (diag < 1000) {
	          return 18;
	      } else if (diag < 3000) {
	          return 17;
	      } else {
	          return 16;
	      }
	  }

	  // [START region_removal]
	  function removeOverlay() {
	    imageMap.setMap(null);
	  }
	  // [END region_removal]

	  google.maps.event.addDomListener(window, 'load', initialize);

	</script>
</body>
</html>