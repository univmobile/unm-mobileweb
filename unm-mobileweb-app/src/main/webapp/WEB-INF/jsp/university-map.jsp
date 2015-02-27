<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
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
			<jsp:include page="includes/maps/maps-search.jsp" />
			<div class="main-container container">
				<div class="map" style="height:${mapHeight};" id="map-canvas"></div>
				<jsp:include page="includes/maps/maps-poi.jsp" />
				<div class="category-wrap">
					<div class="list-wrap">
						<div class="category-nav-button top row">
							<button class="col-xs-3 btn show-hide-search show-search">
								<i class="icon text-hide"></i>
							</button>
							<button class="col-xs-9 btn show-hide-category hide-category">
								Trier par categories<i class="icon"></i>
							</button>
						</div>
						<ul class="category-list row">
							<c:forEach var="categoryItem" items="${allCategories}" varStatus="loop">
								<li class="list-item">
									<button id="category-btn-${categoryItem.getId()}" class="btn category-btn active" onClick="refreshPois('${categoryItem.getId()}',$(this).hasClass('active'));">
										<c:if test="${categoryItem.getInactiveIconUrl() != null}">
											<img class="icon" id="active-category-btn-${categoryItem.getId()}" src="${categoriesIconsUrl}${categoryItem.getActiveIconUrl()}" alt="" style="background-image: none;">
											<img class="icon" id="inactive-category-btn-${categoryItem.getId()}" src="${categoriesIconsUrl}${categoryItem.getInactiveIconUrl()}" alt="" style="background-image: none;">
										</c:if>
										<span>${categoryItem.getName()}</span>
									</button>
								</li>
							</c:forEach>
						</ul>
					</div>
					<div class="bottom-buttons">
						<div class="category-nav-button bottom row">
							<button class="col-xs-9 btn show-hide-category show-category">
								<i class="icon"></i>Trier par categories
							</button>
							<button class="col-xs-3 btn show-hide-search show-search">
								<i class="icon text-hide"></i>
							</button>
						</div>
						<div class="category-buttons row">
							<a class="active category-link one col-xs-4"><i class="icon"></i></a>
							<a href="paris-map" class="category-link two col-xs-4"><i
								class="icon"></i></a> <a href="goodplans-map"
								class="category-link three col-xs-4"><i class="icon"></i></a>
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
		src="https://maps.googleapis.com/maps/api/js?key=${API_KEY}">
		
	</script>
	<script src="./js/mapscripts.js"></script>
	<script type="text/javascript">
		var universityId = "${university.getId()}";
		var categoryRootId = "${categoryRootId}";
		var searchPoisWithoutUniversity = false;
		var librariesCategoryId = "${librariesCategoryId}";
		var markers = constructMarkers(); //global variable
		google.maps.event.addDomListener(window, 'load', initialize);

		function constructMarkers() {
			markersTemp = [];
			<c:forEach var="poiItem" items="${allPois}">
			var iconUrl = "";
			var activeIconUrl = "";
			if ("${poiItem.getCategory().getActiveIconUrl()}" != "") {
				activeIconUrl = "${categoriesIconsUrl}${poiItem.getCategory().getActiveIconUrl()}";
			}
			if ("${poiItem.getCategory().getMarkerIconUrl()}" != "") {
				iconUrl = "${categoriesIconsUrl}${poiItem.getCategory().getMarkerIconUrl()}";
			}
			var marker = new google.maps.Marker(
					{
						position : new google.maps.LatLng(
								"${poiItem.getLat()}", "${poiItem.getLng()}"),
						title : "${poiItem.escapeJS(poiItem.getName())}",
						icon : iconUrl,
						//below are custom poi values, not required for maps.Marker
						idPOI : "${poiItem.getId()}",
						namePOI : "${poiItem.escapeJS(poiItem.getName())}",
						descriptionPOI : "${poiItem.escapeJS(poiItem.getDescription())}",
						addressPOI : "${poiItem.escapeJS(poiItem.getAddress())}",
						floorPOI : "${poiItem.escapeJS(poiItem.getFloor())}",
						phonesPOI : "${poiItem.escapeJS(poiItem.getPhones())}",
						emailPOI : "${poiItem.escapeJS(poiItem.getEmail())}",
						categoryIdPOI : "${poiItem.getCategoryId()}",
						categoryImagePOI: activeIconUrl
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
	</script>
</body>
</html>