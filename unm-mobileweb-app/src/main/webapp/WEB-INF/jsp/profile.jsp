<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>

<!DOCTYPE html>
<html lang="fr">
  <jsp:include page="includes/head.jsp" />
  <body>
      <div class="mask"></div>
      <jsp:include page="includes/nav.jsp" />
      <div class="main-wrap">
          <jsp:include page="includes/maps/maps-header.jsp" />
          <div class="content-wrap orange-section">
              <div class="container">
                  <div class="user-info">
                      <div class="name">Patrick Termian</div>
                      <div class="universite">${university.getTitle()}</div>
                      <div class="link-wrap edit">
                          <a href="#" class="edit-profil">Touchez pour modifier<i class="icon"></i></a>
                      </div>
                  </div>
                  <div class="list-title">Mes Médiathèques</div>
                   <div class="list-wrap">
                      <ul class="media-list">
                          <li class="list-item">                                
                              <a href="#"><h2 class="title">Lien personnalisé # 1</h2></a>
                          </li>
                          <li class="list-item">                                
                              <a href="#"><h2 class="title">Lien personnalisé # 2</h2></a>
                          </li>
                          <li class="list-item">                                
                              <a href="#"><h2 class="title">Lien personnalisé # 3</h2></a>
                          </li>
                          <li class="list-item">                                
                              <a href="#"><h2 class="title">Lien personnalisé # 4</h2></a>
                          </li>
                          <li class="list-item">                                
                              <a href="#"><h2 class="title">Lien personnalisé # 5</h2></a>
                          </li>  
                      </ul>
                      <div class="link-wrap more">
                          <a href="#" class="link">Ouvrir ma médiathèque</a>
                          <i class="icon"></i>
                      </div>
                  </div>
                  <div class="list-title">Mes bibliothèques</div>
                   <div class="list-wrap">
                      <ul class="biblio-list">
                          <li class="list-item">                                
                              <a href="#">
                                  <div class="title"><span><img alt="letters" src="./img/letters.jpg"></span>Conservatoire national des arts et métiers - Bibliothèque centrale</div>
                                  <i class="icon"></i>
                              </a>
                          </li>
                          <li class="list-item">                                
                              <a href="#">
                                  <div class="title">Bibliothèque interuniversitaire Sainte-Barbe</div>
                                  <i class="icon"></i>
                              </a>
                          </li>
                          <li class="list-item">                                
                              <a href="#">
                                  <div class="title">Bibliothèque interuniversitaire Cujas</div>
                                  <i class="icon"></i>
                              </a>
                          </li>
                          <li class="list-item">                                
                              <a href="#">
                                  <div class="title"><span><img alt="letters" src="./img/letters.jpg"></span>Conservatoire national des arts et métiers - Bibliothèque centrale</div>
                                  <i class="icon"></i>
                              </a>
                          </li>
                          <li class="list-item">                                
                              <a href="#">
                                  <div class="title">Bibliothèque interuniversitaire de la Sorbonne</div>
                                  <i class="icon"></i>
                              </a>
                          </li>  
                      </ul>
                       <div class="link-wrap more">
                           <a href="#" class="link">Voir toutes les bibliothèques</a>
                           <i class="icon"></i>
                       </div>
                  </div>
                  <div class="list-title bookmark">Bookmarks</div>
                   <div class="list-wrap">
                      <ul class="bookmark-list">
                          <li class="list-item">                                
                              <a href="#">
                                  <div class="title">Lien personnalisé # 2</div>
                              </a>
                          </li>
                          <li class="list-item">                                
                              <a href="#">
                                  <div class="title">Lien lien personnalisé # 3</div>
                              </a>
                          </li>
                          <li class="list-item">                                
                              <a href="#">
                                  <div class="title">Lien personnalisé # 4</div>
                              </a>
                          </li> 
                      </ul>
                       <div class="link-wrap more bookmark">
                           <a href="#" class="link">Voir tout mes bookmarks</a>
                           <i class="icon"></i>
                       </div>
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