/*!
    query-string
    Parse and stringify URL query strings
    https://github.com/sindresorhus/query-string
    by Sindre Sorhus
    MIT License
*/
(function () {
    'use strict';
    var queryString = {};

    queryString.parse = function (str) {
        if (typeof str !== 'string') {
            return {};
        }

        str = str.trim().replace(/^\?/, '');

        if (!str) {
            return {};
        }

        return str.trim().split('&').reduce(function (ret, param) {
            var parts = param.replace(/\+/g, ' ').split('=');
            var key = parts[0];
            var val = parts[1];

            key = decodeURIComponent(key);
            // missing `=` should be `null`:
            // http://w3.org/TR/2012/WD-url-20120524/#collect-url-parameters
            val = val === undefined ? null : decodeURIComponent(val);

            if (!ret.hasOwnProperty(key)) {
                ret[key] = val;
            } else if (Array.isArray(ret[key])) {
                ret[key].push(val);
            } else {
                ret[key] = [ret[key], val];
            }

            return ret;
        }, {});
    };

    queryString.stringify = function (obj) {
        return obj ? Object.keys(obj).map(function (key) {
            var val = obj[key];

            if (Array.isArray(val)) {
                return val.map(function (val2) {
                    return encodeURIComponent(key) + '=' + encodeURIComponent(val2);
                }).join('&');
            }

            return encodeURIComponent(key) + '=' + encodeURIComponent(val);
        }).join('&') : '';
    };

    queryString.push = function (key, new_value) {
    var params = queryString.parse(location.search);
    if (new_value != null) {
    	params[key] = new_value;
    } else {
    	delete params[key];
    }
    var new_params_string = queryString.stringify(params)
    history.pushState({}, "", window.location.pathname + '?' + new_params_string);
  }

    if (typeof module !== 'undefined' && module.exports) {
        module.exports = queryString;
    } else {
        window.queryString = queryString;
    }
})();

function inviteToLogin(message) {
	if ($('#dataConfirmModal')) {
		$('#dataConfirmModal').remove();
	}
	$('body').append('<div id="dataConfirmModal" class="modal" role="dialog" aria-labelledby="dataConfirmLabel" aria-hidden="true"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button><h3 id="dataConfirmLabel">Merci de vous connecter</h3></div><div class="modal-body">'+message+'</div><div class="modal-footer"><button class="btn" data-dismiss="modal" aria-hidden="true">OK</button></div></div></div></div>');	
	$('#dataConfirmModal').modal({show:true});
	return false;
}

function displayAllMarkers(map) {
	for (var i = 0; i < markers.length; i++) {
		markers[i].setMap(map);
	}
}

function removeAllMarkers() {
	for (var i = 0; i < markers.length; i++) {
		markers[i].setMap(null);
	}
}

function displayMarkers(map, categoryId) {
	if ($("#poiCategoriesFilter button.active").length == 1) {
		removeAllMarkers();
	}
	for (var i = 0; i < markers.length; i++) {
		if (categoryId == markers[i].categoryIdPOI) {
			markers[i].setMap(map);
		}
	}
}

function removeMarkers(categoryId) {
	// If no category is selected, we display all the markers
	var noSelectedCategory = ($("#poiCategoriesFilter button.active").length == 0);
	for (var i = 0; i < markers.length; i++) {
		if (noSelectedCategory) {
			markers[i].setMap(map);
		} else {
			if (categoryId == markers[i].categoryIdPOI) {
				markers[i].setMap(null);
			}
		}
	}
}

function refreshPois(categoryId, status) {
	if (status == false) {
		removeMarkers(categoryId)		
	} else {
		displayMarkers(map, categoryId)
	}
}

function getComments(poiId) {
	
	$.ajax({
		type: "GET",
		dataType: "json",
		url: "json/?action=Comments&poiId="+poiId /* +"&size=200&page=0" */,

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
		url: "json/?action=RestaurantMenu&poiId="+poiId /* +"&size=200&page=0" */,

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
function checkHash(){
	$('.show-hide-poi').click(function(){
		queryString.push('poi', null);
     });
	
	var queryParams = queryString.parse(location.search);
	var poiId = null;
	if (queryParams != null) {
		poiId = queryParams['poi'];
	}
	if (poiId != null) {
			if (poiId == "libraries" && !(typeof librariesCategoryId === 'undefined')) {
				removeAllMarkers();
				$('.category-btn').each(function() {
					if ($(this).attr('id') != "category-btn-"+librariesCategoryId) {
						$(this).toggleClass('active');
						
						$('#active-'+$(this).attr('id')).hide();
						$('#inactive-'+$(this).attr('id')).show();
					}				
				});
				//display only library pois
				refreshPois(librariesCategoryId, false);
			} else {
				for (var i = 0; i < markers.length; i++) {
					if (markers[i].idPOI == poiId) {
						openPoi(markers[i]);
						break;
					}
				}
			}	
	}
}

function getURLParameter(name) {
    return decodeURIComponent((new RegExp('[?|&]' + name + '=' + '([^&;]+?)(&|#|;|$)').exec(location.search)||[,""])[1].replace(/\+/g, '%20'))||null;
}

function changeUrlParam (param, value, markerItem) {
    var currentURL = window.location.href+'&';
    var change = new RegExp('('+param+')=(.*)&', 'g');
    var newURL = currentURL.replace(change, '$1='+value+'&');

    var markerName = '';
    if (markerItem != null) {
    	markerName = markerItem.namePOI;
    }
    if (getURLParameter(param) !== null){
        try {
            window.history.replaceState(markerItem, markerName, newURL.slice(0, - 1) );
        } catch (e) {
            console.log(e);
        }
    } else {
        var currURL = window.location.href;
        if (currURL.indexOf("?") !== -1){
            window.history.replaceState(markerItem, markerName, currentURL.slice(0, - 1) + '&' + param + '=' + value);
        } else {
            window.history.replaceState(markerItem, markerName, currentURL.slice(0, - 1) + '?' + param + '=' + value);
        }
    }
}

function addPoiIdHash(poiId, markerItem) {
	queryString.push("poi", poiId);
	//changeUrlParam("poi", poiId, markerItem);
	//window.location.replace("#" + poiId);
}

//category icons
$(document).ready(function(){
	/*$('.category-btn').each(function() {
		$('#active-'+$(this).attr('id')).show();
		$('#inactive-'+$(this).attr('id')).hide();
	});
	
	$('.category-btn').click(function(){	
		if($(this).hasClass('active')) {
			$('#active-'+$(this).attr('id')).show();
			$('#inactive-'+$(this).attr('id')).hide();
		} else {
			$('#active-'+$(this).attr('id')).hide();
			$('#inactive-'+$(this).attr('id')).show();
		}
	});*/
});

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
	
	var requestUrl = "";
	if (searchPoisWithoutUniversity) {
		requestUrl = "json/?action=SearchPoi&searchInput="+ searchInput + "&categoryRootId=" + categoryRootId +"&size=10&page=0";
	} else {
		requestUrl = "json/?action=SearchPoi&searchInput="+ searchInput + "&universityId=" + universityId + "&categoryRootId=" + categoryRootId +"&size=10&page=0";
	}
	
	$.ajax({
		type: "GET",
		dataType: "json",
		url: requestUrl,

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
        openPoi(getMarker(this.id));
    });
}

function getMarker(poiId) {
	for (var i = 0; i < markers.length; i++) {
		if ("" + markers[i].idPOI == "" + poiId) {
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