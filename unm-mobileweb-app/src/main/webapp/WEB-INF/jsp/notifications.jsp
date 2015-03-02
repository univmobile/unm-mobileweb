<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<%@ page pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="fr">
  <jsp:include page="includes/head.jsp" />
  <body>
  	    <script>
  	      function formatTime(javaDateString) {
  	    	return time_ago(new Date(parseInt(javaDateString)))
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
      <div class="mask"></div>
      <jsp:include page="includes/nav.jsp" />
      <div class="main-wrap">
          <jsp:include page="includes/maps/maps-header.jsp" />
          <div class="content-wrap">
              <div class="container">
                  <h3>Notifications (${fn:length(notificationsList)})</h3>
                  <ul class="notifications-list">
                  		<c:forEach var="notificationItem" items="${notificationsList}">
		                      <li class="list-item">
		                          <p>${notificationItem.content}</p>
		                          <time><script>document.write(formatTime("${notificationItem.notificationTime.time})"))</script></time>
		                      </li>
						</c:forEach>
                  </ul>

              </div>
          </div>
      </div>

    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>
    <script src="./js/bootstrap.min.js"></script>
    <script src="./js/jquery.customSelect.min.js"></script>
    <script src="./js/script.js"></script>
  </body>
</html>