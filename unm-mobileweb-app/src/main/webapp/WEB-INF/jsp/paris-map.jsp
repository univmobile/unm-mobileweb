<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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
          <div class="content-wrap geo category-two">
              <jsp:include page="includes/maps/maps-search.jsp" />
              <div class="main-container container">
                  <div class="map" id="map-canvas"></div>
                  <jsp:include page="includes/maps/maps-poi.jsp" />
                  <div class="category-wrap">
                       <div class="list-wrap">
                           <div class="category-nav-button top row">
                               <button class="col-xs-9 btn show-hide-category hide-category"><i class="icon"></i>Trier par categories</button>
                               <button class="col-xs-3 btn show-hide-search show-search"><i class="icon text-hide"></i></button>
                           </div>
                           <ul class="category-list row" id="poiCategoriesFilter">
                               <c:forEach var="categoryItem" items="${allCategories}" varStatus="loop">
	                           		<li class="list-item">                                
	                                   <button id="category-btn-${categoryItem.id}" class="btn category-btn" onClick="$(this).toggleClass('active'); refreshPois('${categoryItem.id}',$(this).hasClass('active'));">
	                                       <c:if test="${not empty categoryItem.activeIconUrl}">
												<img class="icon" id="inactive-category-btn-${categoryItem.id}" src="${categoriesIconsUrl}${categoryItem.activeIconUrl}" alt="" style="background-image: none;">
										   </c:if>
	                                       <span>${categoryItem.name}</span>
	                                   </button>
	                                </li>	                                
	                            </c:forEach> 
                           </ul>
                       </div>
                      <div class="bottom-buttons" id="univBottomButtons">
                          <div class="category-nav-button bottom row">
                              <button class="col-xs-9 btn show-hide-category show-category"><i class="icon"></i>Trier par categories</button>
                              <button class="col-xs-3 btn show-hide-search show-search"><i class="icon text-hide"></i></button>
                          </div>
                          <div class="category-buttons row">
                              <c:if test="${not isIDF}">
								<a href="university-map" class="active category-link one col-xs-12"><i class="icon"></i></a>
                          	  </c:if>
                              <c:if test="${isIDF}">
                                <a href="university-map" class="category-link one col-xs-4"><i class="icon"></i></a>
                              	<a class="active category-link two col-xs-4"><i class="icon"></i></a>
                              	<a href="goodplans-map" class="category-link three col-xs-4"><i class="icon"></i></a>
                              </c:if>
                          </div>
                      </div>
                  </div>
              </div>
          </div>
      </div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="https://ajax.googleapis.com/ajax/libs/jqueryui/1.11.2/jquery-ui.min.js"></script>
    <script src="./js/bootstrap.min.js"></script>
    <script src="./js/jquery.customSelect.min.js"></script>
    <script src="./js/script.js"></script>
    
    <!-- Google Maps -->
    <script type="text/javascript"
      src="https://maps.googleapis.com/maps/api/js?key=${API_KEY}">
    </script>
    <script src="./js/mapscripts.js"></script>
    <script type="text/javascript">
    var universityId = "${university.id}";
    var categoryRootId = "${categoryRootId}";
	var searchPoisWithoutUniversity = true;
	var markers = constructMarkers(); //global variable
	google.maps.event.addDomListener(window, 'load', initialize);
	
	function initialize() {
		var mapHeight = $(window).height() - ($('#univHeader').height() + $('#univBottomButtons').height() + 24);
		$('#map-canvas').height(mapHeight);
		$('#poiCategoriesFilter').css('max-height', mapHeight + 'px');
		var mapOptions = {
			center: { lat: ${university.centralLat}, lng: ${university.centralLng} }, //Paris
			zoom: 13
	    };
	    window.map = new google.maps.Map(document.getElementById('map-canvas'),
	        mapOptions);
	    displayAllMarkers(map);
	    checkHash();
	}
	
	function constructMarkers() {
		markersTemp = [];
		var marker;
		<c:forEach var="poiItem" items="${allPois}">
		marker = new google.maps.Marker({
			position: new google.maps.LatLng("${poiItem.lat}", "${poiItem.lng}"),
			title: "${escape:javaScript(poiItem.name)}",
			<c:if test="${not empty poiItem.category.markerIconUrl}">
				icon : {
				    url: "${categoriesIconsUrl}${poiItem.category.markerIconUrl}",
				    scaledSize: new google.maps.Size(38, 38) },
			</c:if>
			//below are custom poi values, not required for maps.Marker
			idPOI: "${poiItem.id}",
			namePOI: "${escape:javaScript(poiItem.name)}",
			descriptionPOI: "${escape:javaScript(poiItem.description)}",
			addressPOI: "${escape:javaScript(poiItem.address)}",
			floorPOI: "${escape:javaScript(poiItem.floor)}",
			phonesPOI: "${escape:javaScript(poiItem.phones)}",
			emailPOI: "${escape:javaScript(poiItem.email)}",
			categoryIdPOI: "${poiItem.categoryId}",
			<c:if test="${not empty poiItem.category.activeIconUrl}">
				categoryImagePOI: "${categoriesIconsUrl}${poiItem.category.activeIconUrl}"
			</c:if>
	 	});
		google.maps.event.addListener(marker, 'click', function() {
			openPoi(this);				
		  });

		markersTemp.push(marker);
		</c:forEach>
		return markersTemp;
	}
	
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
		$('#addressPOI').append('<a href="https://maps.google.fr/maps?q=' + encodeURIComponent(markerItem.addressPOI) + '" target="blank"> ' + markerItem.addressPOI + '</a>');
		if (markerItem.addressPOI) {
			$('#addressContainer').show();
		} else {
			$('#addressContainer').hide();
		}
		
		$('#phonePOI').empty();
		$('#phonePOI').append(markerItem.phonesPOI);
		if (markerItem.phonesPOI) {
			$('#phoneContainer').show();
		} else {
			$('#phoneContainer').hide();
		}
		
		$('#emailPOI').empty();
		$('#emailPOI').append(markerItem.emailPOI);
		if (markerItem.emailPOI) {
			$('#emailContainer').show();
		} else {
			$('#emailContainer').hide();
		}
		
		if (markerItem.categoryIdPOI == "${restaurationUniversitaireCategoryId}") {
			$('#menuTab').show();
			$('#menu').show();
			getRestaurantMenus(markerItem.idPOI);
		} else {
			$('#menuTab').hide();
			$('#menu').hide();
			$('#poiTabs a:first').tab('show')  //select first tab
		}
		
		$('#commentsTab').hide();
		
		if (!$('.poi-wrap').hasClass('open')) {
			$('.poi-wrap').toggle("slide", {direction: "down"});
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
    </script>
  </body>
</html>