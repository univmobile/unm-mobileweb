unm-mobileweb
=============

_Application Mobile web UnivMobile_

Ce projet contient le code Java de l’application Mobile web UnivMobile.

Documentation parente : [unm-devel](../../../unm-devel/blob/develop/README.md "Documentation parente : unm-devel/README.md")

### Architecture logicielle

![](src/site/resources/images/mobileweb.png?raw=true =600x "Mobile web + Backend")

L’application Mobile web UnivMobile récupère les données JSON du backend
via une URL spécifiée avec le paramètre « jsonURL »
dans WEB-INF/web.xml

En développement :

    <init-param>
        <param-name>inject:String ref:jsonURL</param-name>
        <param-value>http://univmobile.vswip.com/unm-backend/json/</param-value>
    </init-param>

En intégration continue :

    <init-param>
        <param-name>inject:String ref:jsonURL</param-name>
        <param-value>http://localhost:8380/unm-backend/json/</param-value>
    </init-param>
    
En intégration :

    <init-param>
        <param-name>inject:String ref:jsonURL</param-name>
        <param-value>https://univmobile-dev.univ-paris1.fr/json/</param-value>
    </init-param>

### Principaux projets Maven

  * unm-mobileweb-core — _Accès aux données JSON_
  * unm-mobileweb-app — _Servlet_
  * unm-mobileweb-app-it — _Tests d’intégration_
  
### Projets Maven pour des tests en développement

  * unm-mobileweb-app-local — —Packaging avec accès direct aux données, sans HTTP_
  * unm-mobileweb-app-local-it — _Tests d’intégration_ 

### Tous les repositories GitHub

  * [unm-devel](https://github.com/univmobile/unm-devel/blob/develop/README.md "Repository GitHub unm-devel")
  * [unm-ios](https://github.com/univmobile/unm-ios/blob/develop/README.md "Repository GitHub unm-ios")
  * [unm-android](https://github.com/univmobile/unm-android/blob/develop/README.md "Repository GitHub unm-android")
  * **unm-mobileweb**
  * [unm-backend](https://github.com/univmobile/unm-backend/blob/develop/README.md "Repository GitHub unm-backend")
  * [unm-integration](https://github.com/univmobile/unm-integration/blob/develop/README.md "Repository GitHub unm-integration")

