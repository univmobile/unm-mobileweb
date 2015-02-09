<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

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
      <div class="main-wrap">
          <div class="content-wrap splash">
              <div class="container">
                  <div class="splash-header">
                      <div class="header-image">
                          <img class="img-responsive" src="./img/splash-bg.jpg" alt="splash"> 
                      </div>
                      <div class="page-logo">
                          <img class="img-responsive" src="./img/univmobile-logo.png" alt="splash"> 
                      </div>
                  </div>
                  <div class="universite-form-wrap">
                      <form id="select-universite">
                          <div class="form-group">
                          	<label>Choisir son université :</label>
                                                      
                         	<select class="form-control">
                            	<c:forEach var="region" items="${regionsList}">
     						    	<optgroup label="${region.getName()}">
     						    	
     						    		<c:forEach var="university" items = "${region.getUniversities()}">
     						    			<option>${university.getTitle()}</option>
     						    		</c:forEach>
     						    	
   									</optgroup>
    							</c:forEach>
                          	</select>
                          </div>
                          <button type="submit" class="btn confirm center-block">Valider</button>
                      </form>
                  </div>
                  <div class="splash-logos-wrap">
                      <div class="logo-wrap one"><img class="img-responsive" alt="logo" src="./img/splash-logo-1.jpg"></div>
                      <div class="logo-wrap two"><img class="img-responsive" alt="logo" src="./img/splash-logo-2.jpg"></div>
                      <div class="logo-wrap three"><img class="img-responsive" alt="logo" src="./img/splash-logo-3.png"></div>
                  </div>
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