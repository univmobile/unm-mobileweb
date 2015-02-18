<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="fr">
  <jsp:include page="includes/maps/maps-head.jsp" />
  <body>
      <div class="mask"></div>
	  <jsp:include page="includes/nav.jsp" />
      <div class="main-wrap">
          <jsp:include page="includes/maps/maps-header.jsp" />
          <div class="content-wrap geo category-one">
              <jsp:include page="includes/maps/maps-search.jsp" />
              <div class="main-container container">
                  <div class="map" style="height:${mapHeight};" id="map-canvas"></div>
                  <jsp:include page="includes/maps/maps-poi.jsp" />
                  <div class="category-wrap">
                       <div class="list-wrap">
                           <div class="category-nav-button top row">
                               <button class="col-xs-3 btn show-hide-search show-search"><i class="icon text-hide"></i></button>
                               <button class="col-xs-9 btn show-hide-category hide-category">Trier par categories<i class="icon"></i></button>
                           </div>
                           <ul class="category-list row">
                           		<c:forEach var="categoryItem" items="${allCategories}" varStatus="loop">
	                           		<li class="list-item">                                
	                                   <button class="btn category-btn active" onClick="refreshPois('${categoryItem.getId()}',$(this).hasClass('active'))">
	                                       <!-- <i class="icon"></i> -->
	                                       <span>${categoryItem.getName()}</span>
	                                   </button>
	                                </li>	                                
	                            </c:forEach>
                           </ul>
                       </div>
                      <div class="bottom-buttons">
                          <div class="category-nav-button bottom row">
                              <button class="col-xs-9 btn show-hide-category show-category"><i class="icon"></i>Trier par categories</button>
                              <button class="col-xs-3 btn show-hide-search show-search"><i class="icon text-hide"></i></button>
                          </div>
                          <div class="category-buttons row">
                              <a class="active category-link one col-xs-4"><i class="icon"></i></a>
                              <a href="goodplans-map" class="category-link two col-xs-4"><i class="icon"></i></a>
                              <a href="paris-map" class="category-link three col-xs-4"><i class="icon"></i></a>
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
    <script type="text/javascript">
	var markers = constructMarkers(); //global variable
	google.maps.event.addDomListener(window, 'load', initialize);
	
	function initialize() {
		var mapOptions = {
			center: { lat: 48.8567, lng: 2.3508}, //Paris
			zoom: 9
        };
        window.map = new google.maps.Map(document.getElementById('map-canvas'),
            mapOptions);
        displayAllMarkers(map);
	}
	
	function displayAllMarkers(map) {
		for (var i = 0; i < markers.length; i++) {
			markers[i].setMap(map);
		}
	}
	
	function displayMarkers(map, categoryId) {
		for (var i = 0; i < markers.length; i++) {
			if (categoryId == markers[i].categoryIdPOI) {
				markers[i].setMap(map);
			}
		}
	}
	
	function removeMarkers(categoryId) {
		for (var i = 0; i < markers.length; i++) {
			if (categoryId == markers[i].categoryIdPOI) {
				markers[i].setMap(null);
			}
		}
	}
	
	function constructMarkers() {
		markersTemp = [];
		<c:forEach var="poiItem" items="${allPois}">
			var marker = new google.maps.Marker({
				position: new google.maps.LatLng("${poiItem.getLat()}", "${poiItem.getLng()}"),
				title: "${poiItem.getName()}",
				//below are custom poi values, not required for maps.Marker
				idPOI: "${poiItem.getId()}",
				namePOI: "${poiItem.getName()}",
				descriptionPOI: "${poiItem.getDescriptionClean()}",
				addressPOI: "${poiItem.getAddressClean()}",
				floorPOI: "${poiItem.getFloor()}",
				phonesPOI: "${poiItem.getPhones()}",
				emailPOI: "${poiItem.getEmail()}",
				categoryIdPOI: "${poiItem.getCategoryId()}"
		 	});
			google.maps.event.addListener(marker, 'click', function() {
				openPoi(this);				
			  });

			markersTemp.push(marker);
		</c:forEach>
		return markersTemp;
	}
	
	function openPoi(markerItem) {
		
		//getComments(markerItem.idPOI);
		
		$('.poi-wrap .title').empty();
		$('.poi-wrap .title').append(markerItem.namePOI);
		
		$('.poi-wrap .body').empty();
		$('.poi-wrap .body').append(markerItem.descriptionPOI);
		
		$('#addressPOI').empty();
		$('#addressPOI').append(markerItem.addressPOI);
		
		$('#phonePOI').empty();
		$('#phonePOI').append(markerItem.phonesPOI);
		
		$('#emailPOI').empty();
		$('#emailPOI').append(markerItem.emailPOI);
		
		if (markerItem.categoryIdPOI == "25") {
			$('#menuTab').show();
			$('#menu').show();
		} else {
			$('#menuTab').hide();
			$('#menu').hide();
		}
		
		$('.poi-wrap').toggle("slide", {direction: "down"});
        $('.poi-wrap').toggleClass('open');
	}
	
	function refreshPois(categoryId, status) {
		if (status == true) {
			removeMarkers(categoryId)
		} else {
			displayMarkers(map, categoryId)
		}
	}
	
	function getComments(poiId) {
		console.log("poiId: "+poiId);
		
		$.ajax({
			type: "GET",
			url: "/json/?action=getComments",

			success: function (response) {
				alert("success");
				console.log("response: "+response);
			},
			error: function (xhr, status, error) {
				alert(xhr.responseText);
			}
		});
	}
    </script>
  </body>
</html>