<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="fr">
  <head>
    <meta charset="utf-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta id="viewport" name="viewport" content="width=device-width, initial-scale=1">
    <script>
    (function(doc) {
        var viewport = document.getElementById('viewport');
        if ( document.documentElement.clientWidth > 640 ) {
            viewport.setAttribute("content", "width=640, user-scalable=yes");
                }
            }(document));
    </script>
    <title>Univmobile</title>
    <link href='http://fonts.googleapis.com/css?family=Exo:400,600,300,700,400italic,500' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/font-awesome/4.3.0/css/font-awesome.min.css">
    <!-- Bootstrap -->
    <link href="./css/bootstrap.css" rel="stylesheet">
    <link href="./css/style.css" rel="stylesheet">

    <!-- HTML5 shim and Respond.js for IE8 support of HTML5 elements and media queries -->
    <!-- WARNING: Respond.js doesn't work if you view the page via file:// -->
    <!--[if lt IE 9]>
      <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
      <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>
    <![endif]-->  
  </head>
  <body>
      <div class="mask"></div>
	  <jsp:include page="includes/nav.jsp" />
      <div class="main-wrap">
          <header class="main-navbar container clearfix">
                      <button type="button" class="navbar-menu-toggle">
                          <span class="icon-bar"></span>
                          <span class="icon-bar"></span>
                          <span class="icon-bar"></span>
                      </button>
                      <div class="user-nav">
                          <div class="user">
                              <span class="welcome-msg">Bonjour</span>
                              <div class="user-name">Patrick</div>
                          </div>
                          <div class="universite-name">
                              ${university.getTitle()}
                          </div>
                          <div class="notifications">
                              <i class="icon"></i>
                          </div>
                      </div>
          </header>
          <div class="content-wrap geo category-one">
              <div class="search-wrap">
                  <div class="container">
                      <form class="search">
                          <div class="form-group">
                              <span class="btn go-search"><i class="icon"></i></span>
                              <input type="text" class="form-control search-field" placeholder="Tappez votre recherche">
                              <span class="btn hide-search show-hide-search"><i class="icon"></i></span>
                          </div>
                      </form>
                      <section class="search-results">
                          <div class="results-title">Recherches précédentes</div>
                          <ul class="results-list">
                              <li class="list-item">
                                  <i class="icon"></i>
                                  <div class="title">Bureau des éléves</div>
                              </li>
                              <li class="list-item">
                                  <i class="icon"></i>
                                  <div class="title">Sorbonne</div>
                              </li>
                              <li class="list-item">
                                  <i class="icon"></i>
                                  <div class="title">Salle de sport </div>
                              </li>
                              <li class="list-item">
                                  <i class="icon"></i>
                                  <div class="title">Administration</div>
                              </li>
                              <li class="list-item">
                                  <i class="icon"></i>
                                  <div class="title">Direction</div>
                              </li>
                          </ul>
                      </section>
                  </div>
              </div>
              <div class="main-container container">
              	  <!-- MAP IMAGE
                  <div class="map">
                      <img src="./img/map-yellow.jpg" alt="main article" class="img-responsive">
                  </div>
                  -->
                  <div class="map" style="height:400px;" id="map-canvas"></div>
                  
                        
                  
                  <div class="poi-wrap" style="display:none;">
                      <div class="poi-screen">
                          <div class="poi-header">
                              <span class="category-icon category-7"><i class="icon text-hide"></i></span>
                              <div class="title"></div>
                              <button class="btn show-hide-poi hide-poi"><i class="icon text-hide"></i></button>
                          </div>
                          <div class="poi-info">
                              <address><i class="fa fa-map-marker fa-fw"></i><span id="some id for address">Campus Censier, bâtiment A, porte 22 (RdC)</span></address>
                              <div class="phone"><i class="fa fa-phone fa-fw"></i>01 45 87 40 30</div>
                              <div class="email"><i class="fa fa-envelope fa-fw"></i>infirmerie@univ-paris3.fr</div>
                          </div>
                          <div class="tabpanel" role="tabpanel">
                              <ul class="nav nav-tabs" role="tablist">
                                  <li role="presentation" class="one active"><a href="#plan" aria-controls="plan" role="tab" data-toggle="tab"><i class="fa fa-info-circle"></i></a></li>
                                  <li role="presentation" class="two"><a href="#comments" aria-controls="comments" role="tab" data-toggle="tab"><i class="fa fa-comments"></i></a></li>
                                  <li role="presentation" class="three"><a href="#menu" aria-controls="menu" role="tab" data-toggle="tab"><i class="fa fa-cutlery"></i></a></li>
                              </ul>
                              <div class="tab-content">
                                  <div role="tabpanel" class="tab-pane fade in active" id="plan">
                                      <div class="body">
                                          
                                      </div>
                                      <button class="btn show-hide-search show-search-long"><i class="icon"></i>Rechercher</button>
                                  </div>
                                  <div role="tabpanel" class="tab-pane fade" id="comments">
                                      <ul class="comments-list">
                                          <li class="comment-item">
                                              <div class="author">Jeremy D.</div>
                                              <time>il y a 8 heures</time>
                                              <p class="comment-text">« Très bon accueil, personnel serviable et très à l’écoute. Prise en charge très rapide ! »</p>
                                          </li>
                                          <li class="comment-item">
                                              <div class="author">Jeremy D.</div>
                                              <time>il y a 8 heures</time>
                                              <p class="comment-text">« Très bon accueil, personnel serviable et très à l’écoute. Prise en charge très rapide ! »</p>
                                          </li>
                                      </ul>
                                      <div class="new-comment-wrap">
                                          <form id="new-comment" class="new-comment">
                                              <div class="form-group comment-field">
                                                  <textarea class="form-control" rows="6" placeholder=""></textarea>
                                              </div>
                                              <button type="submit" class="btn long-button submit center-block">Valider</button>
                                          </form>
                                      </div>
                                  </div>
                                  <div role="tabpanel" class="tab-pane fade" id="menu">...menu</div>
                              </div>
                          </div>
                      </div>
                  </div>
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
                      
                           		   
                           	   <!--
                               <li class="list-item">                                
                                   <button class="btn category-btn button-1">
                                       <i class="icon"></i>
                                       <span>Bibliothèque Universitaire</span>
                                   </button>
                               </li>
                               <li class="list-item">                                
                                   <button class="btn category-btn button-2">
                                       <i class="icon"></i>
                                       <span>Orientation & Insertion professionnelle</span>
                                   </button>
                               </li>
                               <li class="list-item">                                
                                   <button class="btn category-btn button-3">
                                       <i class="icon"></i>
                                       <span>Plan</span>
                                   </button>
                               </li>
                               <li class="list-item">                                
                                   <button class="btn category-btn button-4">
                                       <i class="icon"></i>
                                       <span>Restauration Universitaire</span>
                                   </button>
                               </li>
                               <li class="list-item">                                
                                   <button class="btn category-btn button-5">
                                       <i class="icon"></i>
                                       <span>Equipements sportifs & service des sports</span>
                                   </button>
                               </li>
                               <li class="list-item">                                
                                   <button class="btn category-btn button-6">
                                       <i class="icon"></i>
                                       <span>Culture</span>
                                   </button>
                               </li>
                               <li class="list-item">                                
                                   <button class="btn category-btn button-7">
                                       <i class="icon"></i>
                                       <span>Santé & Social</span>
                                   </button>
                               </li>
                               <li class="list-item">                                
                                   <button class="btn category-btn button-8">
                                       <i class="icon"></i>
                                       <span>Centres administratifs</span>
                                   </button>
                               </li>
                               <li class="list-item">
                                   <button class="btn category-btn button-9">
                                       <i class="icon"></i>
                                       <span>Scolarité & Inscription</span>
                                   </button>
                               </li>
                               <li class="list-item">                                
                                   <button class="btn category-btn button-10">
                                       <i class="icon"></i>
                                       <span>Relais handicap</span>
                                   </button>
                               </li>
                               <li class="list-item">                                
                                   <button class="btn category-btn button-11">
                                       <i class="icon"></i>
                                       <span>Institut & École</span>
                                   </button>
                               </li>
                               <li class="list-item">                                
                                   <button class="btn category-btn button-12">
                                       <i class="icon"></i>
                                       <span>Recherche</span>
                                   </button>
                               </li> 
                               -->
                           </ul>
                       </div>
                      <div class="bottom-buttons">
                          <div class="category-nav-button bottom row">
                              <button class="col-xs-9 btn show-hide-category show-category"><i class="icon"></i>Trier par categories</button>
                              <button class="col-xs-3 btn show-hide-search show-search"><i class="icon text-hide"></i></button>
                          </div>
                          <div class="category-buttons row">
                              <a class="active category-link one col-xs-4"><i class="icon"></i></a>
                              <a href="geo2.html" class="category-link two col-xs-4"><i class="icon"></i></a>
                              <a href="geo3.html" class="category-link three col-xs-4"><i class="icon"></i></a>
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
				namePOI: "${poiItem.getName()}",
				addressPOI: "${poiItem.getAddressItem()}",
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
		
		$('.poi-wrap .title').empty();
		$('.poi-wrap .title').append(markerItem.namePOI);
		
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
    </script>
  </body>
</html>