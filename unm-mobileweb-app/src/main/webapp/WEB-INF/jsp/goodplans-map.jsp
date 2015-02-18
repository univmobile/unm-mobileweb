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
            <div class="content-wrap geo category-three">
                <jsp:include page="includes/maps/maps-search.jsp" />
                <div class="main-container container">
                    <div class="map" style="height:${mapHeight};" id="map-canvas"></div>
                    <jsp:include page="includes/maps/addpoi.jsp" />
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
                                <button class="col-xs-3 btn show-hide-search show-search"><i class="icon text-hide"></i></button>
                                <button class="col-xs-9 btn show-hide-category show-category">Trier par categories<i class="icon"></i></button>
                            </div>
                            <div class="category-buttons row">
                                <a href="university-map" class="category-link one col-xs-4"><i class="icon"></i></a>
                                <a href="paris-map" class="category-link two col-xs-4"><i class="icon"></i></a>
                                <a class="active category-link three col-xs-4"><i class="icon"></i></a>
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
				title: "${poiItem.escapeJS(poiItem.getName())}",
				//below are custom poi values, not required for maps.Marker
				idPOI: "${poiItem.getId()}",
				namePOI: "${poiItem.escapeJS(poiItem.getName())}",
				descriptionPOI: "${poiItem.escapeJS(poiItem.getDescription())}",
				addressPOI: "${poiItem.escapeJS(poiItem.getAddress())}",
				floorPOI: "${poiItem.escapeJS(poiItem.getFloor())}",
				phonesPOI: "${poiItem.escapeJS(poiItem.getPhones())}",
				emailPOI: "${poiItem.escapeJS(poiItem.getEmail())}",
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
		
		if (markerItem.categoryIdPOI == "${restaurationUniversitaireCategoryId}") {
			$('#menuTab').show();
			$('#menu').show();
		} else {
			$('#menuTab').hide();
			$('#menu').hide();
			$('#poiTabs a:first').tab('show')  //select first tab
		}
		
		$('.poi-wrap').toggle("slide", {direction: "down"});
        $('.poi-wrap').toggleClass('open');
        
        getComments(markerItem.idPOI);
	}
	
	function refreshPois(categoryId, status) {
		if (status == true) {
			removeMarkers(categoryId)
		} else {
			displayMarkers(map, categoryId)
		}
	}
	
	function getComments(poiId) {
		
		$.ajax({
			type: "GET",
			dataType: "json",
			url: "/unm-mobileweb/json/?action=Comments&poiId="+poiId /* +"&size=200&page=0" */,

			success: function (response) {
				for (var i = 0; i < response.length; i++) {
					appendComment(response[i]);
				}
			}
		});
	}
	
	function appendComment(commentItem) {
		var message = $('<span>').text(commentItem.message).html();
		var author = $('<span>').text(commentItem.author).html();
		$('.comments-list').append("<li class='comment-item'>" + 
										"<div class='author'>" + author + "</div>" +
										" <time>" + time_ago(new Date(commentItem.postedOn)) + "</time>" + 
										"<p class='comment-text'>" + message + "</p>" + 
									"<li>"
		);	
	}
	
	
	//http://stackoverflow.com/a/12475270
	function time_ago(time){

		switch (typeof time) {
		    case 'number': break;
		    case 'string': time = +new Date(time); break;
		    case 'object': if (time.constructor === Date) time = time.getTime(); break;
		    default: time = +new Date();
		}
		var time_formats = [
           [60, 'secondes', 1], // 60   [120, '1 minute ago', '1 minute from now'], // 60*2
           [3600, 'minutes', 60], // 60*60, 60
           [7200, 'Il y a une heure', 'Dans une heure'], // 60*60*2
           [86400, 'heures', 3600], // 60*60*24, 60*60
           [172800, 'Hier', 'Demain'], // 60*60*24*2
           [604800, 'jours', 86400], // 60*60*24*7, 60*60*24
           [1209600, 'La semaine dernière', 'La semaine prochaine'], // 60*60*24*7*4*2
           [2419200, 'semaines', 604800], // 60*60*24*7*4, 60*60*24*7
           [4838400, 'Le mois dernier', 'Le mois prochain'], // 60*60*24*7*4*2
           [29030400, 'mois', 2419200], // 60*60*24*7*4*12, 60*60*24*7*4
           [58060800, 'Année dernière', 'Année prochaine'], // 60*60*24*7*4*12*2
           [2903040000, 'années', 29030400], // 60*60*24*7*4*12*100, 60*60*24*7*4*12
           [5806080000, 'Siècle dernier', 'Siècle prochain'], // 60*60*24*7*4*12*100*2
           [58060800000, 'siècles', 2903040000] // 60*60*24*7*4*12*100*20, 60*60*24*7*4*12*100

        ];
		var seconds = (+new Date() - time) / 1000,
	    	token = 'Il y a', list_choice = 1;

		if (seconds == 0) {
			return 'Maintenant'
		}
		if (seconds < 0) {
			seconds = Math.abs(seconds);
			token = 'Depuis';
			list_choice = 2;
		}
		var i = 0, format;
		while (format = time_formats[i++])
		    if (seconds < format[0]) {
		        if (typeof format[2] == 'string')
		        	return format[list_choice];
		        else
		            return token + ' '  +Math.floor(seconds / format[2]) + ' ' + format[1] ;
		    }
		return time;
		}
    </script>
    </body>
</html>