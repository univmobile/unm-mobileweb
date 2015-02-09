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
      <div class="mask"></div>
      <nav class="navbar-menu" id="mobile-nav">
          <header>
              <a class="universite-logo" href="#"><img class="img-responsive" alt="Universite Paris" src="./img/universite-logo.jpg"></a>
              <span class="close-btn"></span>
          </header>
          <ul class="menu navbar-nav">
              <li class="menu-item one active dropdown">
                  <a class="dropdown-toggle" data-toggle="dropdown" role="button" href="#">Mes services<span class="menu-item-icon"><i class="icon"></i></span></a>
                  <ul class="dropdown-menu" role="menu">
                      <li><a href="#">Mon profil</a></li>
                      <li><a href="#">Mes Bibliothèques</a></li>
                      <li><a href="#">ENT</a></li>
                      <li><a href="#">Ma médiathèque</a></li>
                      <li><a href="#">Futurs étudiants</a></li>
                  </ul>
              </li>
              <li class="menu-item two dropdown">
                  <a class="dropdown-toggle" data-toggle="dropdown" role="button" href="#">Act’Universitaire<span class="menu-item-icon"><i class="icon"></i></span></a>
                  <ul class="dropdown-menu" role="menu">
                      <li><a href="#">Menu 1</a></li>
                      <li><a href="#">Menu 2</a></li>
                      <li><a href="#">Menu 3</a></li>
                  </ul>
              </li>
              <li class="menu-item three dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">Tou’Trouver<span class="menu-item-icon"><i class="icon"></i></span></a>
                  <ul class="dropdown-menu" role="menu">
                      <li><a href="#">GéoCampus</a></li>
                      <li><a href="#">Que faire à Paris</a></li>
                      <li><a href="#">Les bons plans</a></li>
                  </ul>
              </li>
              <li class="menu-item four dropdown">
                  <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button">Ma vie U<span class="menu-item-icon"><i class="icon"></i></span></a>
                  <ul class="dropdown-menu" role="menu">
                      <li><a href="#">Catalogue SUDOC</a></li>
                      <li><a href="#">Rue des facs</a></li>
                      <li><a href="#">Guide de l'étudiant</a></li>
                      <li><a href="#">Mon CROUS</a></li>
                      <li><a href="#">Primo arrivant / Erasmus</a></li>
                  </ul>
              </li>
          </ul>
      </nav>
      <div class="main-wrap">
          <header class="main-navbar clearfix">
                      <button type="button" class="navbar-menu-toggle">
                          <span class="icon-bar"></span>
                          <span class="icon-bar"></span>
                          <span class="icon-bar"></span>
                      </button>
                      <div class="user-nav">
                          <div class="contact-link">Connectez-vous</div>
                          <div class="universite-name">
                              Université Paris I <br>
                              Panthéon<br>
                              La Sorbonne
                          </div>
                          <div class="notifications">
                              <i class="icon"></i>
                          </div>
                      </div>
          </header>
          <div class="content-wrap">
              <div class="container">
                  <article class="main-article clearfix">
                      <div class="head-img">
                          <img class="img-responsive" alt="main article" src="./img/home-img.jpg">
                      </div>
                      <div class="description">
                          <time>Nom du flux - 08/01/2015</time>
                          <h2 class="title">Hommage à Charlie Hebo</h2>
                          <div class="body">
                              <p>Rassemblement dans le hall du bâtiment A, ce jeudi 8 janvier,
                                  à 12h pour y observer 1 minute de silence. Hommage à Charlie Hebdo.</p>
                          </div>
                      </div>
                  </article>
                  <div class="list-title">Dernières actualités</div>
                  <div class="list-wrap"  id="accordion" role="tablist" aria-multiselectable="true">
                      <ul class="article-list">
                          <li class="list-item">
                              <article>                                
                                  <div class="row">
                                      <a data-toggle="collapse" data-parent="#accordion" href="#collapseOne" aria-expanded="true" aria-controls="collapseOne">
                                              <div class="img-wrap">
                                                  <img class="img-responsive" alt="article" src="./img/article-1.jpg">
                                              </div>
                                              <div class="description">
                                                  <time>Nom du flux - 04/01/2015</time>
                                                  <h2>Vivre avec une personne âgée : une coloc’ originale pour les étudiants</h2>
                                                  <i class="icon"></i>
                                              </div>
                                      </a>
                                  </div>
                                      <div id="collapseOne" class="panel-collapse collapse in" role="tabpanel" aria-labelledby="headingOne">
                                          <div class="body">
                                              <p>1ère étape - L'inscription par internet : vous vous enregistrez sur la plateforme afin de constituer votre dossier numérique Vous saisissez l'ensemble de vos demandes de poursuite d'études : candidatures</p>
                                              <p>Ajout et retrait de candidatures : Du 20 Janvier au 20 Mars 18H pour toutes vos candidatures.</p>
                                              <a href="#" class="btn button">Aller plus loin </a>
                                          </div>
                                      </div>
                              </article>
                          </li>
                          <li class="list-item">
                              <article>                                
                                  <div class="row">
                                      <a data-toggle="collapse" data-parent="#accordion" href="#collapseTwo" aria-expanded="false" aria-controls="collapseTwo">
                                          <div class="img-wrap">
                                              <img class="img-responsive" alt="article" src="./img/article-2.jpg">
                                          </div>
                                          <div class="description">
                                              <time>Nom du flux - 23/12/2014</time>
                                              <h2>Les écoles de commerce dans la bataille des Mooc</h2>
                                              <i class="icon"></i>
                                          </div>
                                      </a>
                                  </div>
                                  <div id="collapseTwo" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingTwo">
                                      <div class="body">
                                          <p>1ère étape - L'inscription par internet : vous vous enregistrez sur la plateforme afin de constituer votre dossier numérique Vous saisissez l'ensemble de vos demandes de poursuite d'études : candidatures</p>
                                          <p>Ajout et retrait de candidatures : Du 20 Janvier au 20 Mars 18H pour toutes vos candidatures.</p>
                                      </div>
                                  </div>
                              </article>
                          </li>
                          <li class="list-item">
                              <article>                                
                                  <div class="row">
                                      <a data-toggle="collapse" data-parent="#accordion" href="#collapseThree" aria-expanded="false" aria-controls="collapseThree">
                                          <div class="img-wrap">
                                              <img class="img-responsive" alt="article" src="./img/article-3.jpg">
                                          </div>
                                          <div class="description">
                                              <time>Nom du flux - 18/12/2014</time>
                                              <h2>Comment s’inscrire sur Admission Post Bac</h2>
                                              <i class="icon"></i>
                                          </div>
                                      </a>
                                  </div>
                                  <div id="collapseThree" class="panel-collapse collapse" role="tabpanel" aria-labelledby="headingThree">
                                      <div class="body">
                                          <p>1ère étape - L'inscription par internet : vous vous enregistrez sur la plateforme afin de constituer votre dossier numérique Vous saisissez l'ensemble de vos demandes de poursuite d'études : candidatures</p>
                                          <p>Ajout et retrait de candidatures : Du 20 Janvier au 20 Mars 18H pour toutes vos candidatures.</p>
                                      </div>
                                  </div>
                              </article>
                          </li>

                      </ul>
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