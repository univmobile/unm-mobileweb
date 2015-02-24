<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ page pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="fr">
  <jsp:include page="includes/head.jsp" />
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
                      <form id="select-universite" method="post">
                          <div class="form-group">
                          	<label>Choisir son universit�  :</label>
                                                      
                         	<select class="form-control" name="univ">
                            	<c:forEach var="region" items="${regionsList}">
     						    	<optgroup label="${region.getName()}">
     						    	
     						    		<c:forEach var="university" items = "${region.getUniversities()}">
     						    			<option value="${university.getId()}">${university.getTitle()}</option>
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