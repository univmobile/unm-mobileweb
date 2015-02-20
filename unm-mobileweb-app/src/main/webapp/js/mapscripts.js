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
			$('#commentsList').empty();
			for (var i = 0; i < response.length; i++) {
				appendComment(response[i]);
			}
		}
	});
}

function appendComment(commentItem) {
	var message = $('<span>').text(commentItem.message).html();
	var author = $('<span>').text(commentItem.author).html();
	$('#commentsList').append("<li class='comment-item'>" + 
									"<div class='author'>" + author + "</div>" +
									" <time>" + time_ago(new Date(commentItem.postedOn)) + "</time>" + 
									"<p class='comment-text'>" + message + "</p>" + 
							  "<li>"
	);
}

function getRestaurantMenus(poiId) {
	
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "/unm-mobileweb/json/?action=RestaurantMenu&poiId="+poiId /* +"&size=200&page=0" */,

		success: function (response) {
			var todaysDate = new Date().setHours(0,0,0,0);
			$('#menuList').empty();
			for (var i = 0; i < response.length; i++) {
				var menuDate = new Date(response[i].effectiveDate).setHours(0,0,0,0);
				if (todaysDate == menuDate) {
					appendRestaurantMenu(response[i]);
					if(response[i+1] != null) {
						appendRestaurantMenu(response[i+1]);
					}
					break;
				}			
			}
		}
	});
}

function appendRestaurantMenu(menuItem) {
	$('#emptyString').hide();
	$('#menuList').append("<li style='list-style: none'>" +
								" <time>" + new Date(menuItem.effectiveDate).toLocaleDateString() + "</time>" +
								menuItem.description + 
						  "</li>");
}

//poi hash
$(document).ready(function(){
	$('.show-hide-poi').click(function(){
		window.location.replace("# ");
     });
	
	if (window.location.hash) {
		var hash = window.location.hash.substring(1);
		
		for (var i = 0; i < markers.length; i++) {
			if (markers[i].idPOI == hash) {
				openPoi(markers[i]);
				break;
			}
		}
	}
});

function addPoiIdHash(poiId) {
	window.location.replace("#" + poiId);
}

//search
$(document).ready(function(){
	$('.show-hide-search').click(function(){
		newCleanSearch();
	});
	
	$('#clearButton').click(function(){
		newCleanSearch();		
    });
	
	$('#searchInput').bind('input', function() {
		if ($(this).val().length > 0) {
			$('#clearButton').show();
	    } else {
	    	$('#clearButton').hide();
	    }
		
	    if ($(this).val().length > 2) {
	    	getPoiSearchResults($(this).val());
	    } else {
	    	$('#searchResultsTitle').empty();
	    	$('#searchResultsTitle').append("Merci de saisir au moins 3 caractères");
	    	$('#seachResultsList').empty();
	    }
	});
});

function newCleanSearch() {
	$('#searchInput').val("");
	$('#clearButton').hide();
	$('#searchResultsTitle').empty();
	$('#searchResultsTitle').append("Merci de saisir au moins 3 caractères");
	$('#seachResultsList').empty();
	$('#searchInput').focus();
}

function getPoiSearchResults(searchInput) {
	
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "/unm-mobileweb/json/?action=SearchPoi&searchInput="+ searchInput + "&universityId=" + universityId +"&size=10&page=0"  ,

		success: function (response) {
			$('#seachResultsList').empty();
			if (response != null) {
				$('#searchResultsTitle').empty();
		    	$('#searchResultsTitle').append("Résultats de la recherche:");
			} else {
				$('#searchResultsTitle').empty();
		    	$('#searchResultsTitle').append("Pas de résultat");
			}
			
			for (var i = 0; i < response.length; i++) {
				appendSearchResult(response[i]);
			}
		}
	});
}

function appendSearchResult(poiItem) {
	$('#seachResultsList').append("<li id='" + poiItem.id + "' class='list-item search-result-item'>" +
    									"<i class='icon'></i>" +
    									"<div class='title'>" + poiItem.name + "</div>" +
    							  "</li>");
	
	$('#'+poiItem.id).click(function(){     
        $('.search-wrap').toggle("slide", {direction: "up"});
        openPoi(getMarker(poiItem.id));
    });
}

function getMarker(poiId) {
	for (var i = 0; i < markers.length; i++) {
		if (markers[i].idPOI == poiId) {
			return markers[i];
		}
	}
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