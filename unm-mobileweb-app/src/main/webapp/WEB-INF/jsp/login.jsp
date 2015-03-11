<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="utf-8"%>

<!DOCTYPE html>
<html lang="fr">
  <jsp:include page="includes/head.jsp" />
  <body>
      <div class="mask"></div>
      <jsp:include page="includes/nav.jsp" />
      <div class="main-wrap login">
          <jsp:include page="includes/maps/maps-header.jsp" />
          <div class="content-wrap">
              <div class="container">
                  <div class="logo-wrap">
                      <img class="img-responsive center-block" src="./img/univmobile-logo-dark.png" alt="Univmobile logo">
                  </div>
                  <div class="universite-login-wrap">
                      <div class="form-title">Connexion</div>
                      <c:if test="${errorMessage != null}">
                      	<div class="alert alert-danger" role="alert"><b>Error:</b> ${errorMessage}</div>
                      </c:if>
                      <form id="login-form" method="post">
                          <div class="form-group">
                              <label for="exampleInputEmail1">Nom d'utilisateur</label>
                              <input name="usernameField" type="text" class="form-control" id="exampleInputEmail1" placeholder="" required>
                          </div>
                          <div class="form-group">
                              <label for="exampleInputPassword1">Mot de passe</label>
                              <input name="passwordField" type="password" class="form-control" id="exampleInputPassword1" placeholder="" required>
                          </div>
                          <button class="btn confirm center-block" type="submit">Valider</button>
                      </form>
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