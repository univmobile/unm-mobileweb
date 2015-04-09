<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ page pageEncoding="utf-8"%>

<div class="add-poi-wrap">
    <button class="btn show-hide-add-poi show-add-poi"><i class="icon text-hide"></i></button>
    <div class="add-poi-screen">
        <button class="btn show-hide-add-poi hide-add-poi"><i class="icon text-hide"></i></button>
        <div class="title">Soumettre un nouveau Bon Plan</div>
        <form id="add-poi" class="add-poi" method="post">
            <div class="add-poi-name-field">
                <div class="form-pad row">
                    <div class="form-group name col-xs-8 col-xs-offset-4">
                        <input type="text" class="form-control" id="InputName" placeholder="Nom du bon plan *" name="poiName" required>
                    </div>
                </div>
            </div>
            <div class="form-pad">
                <div class="fields-wrap row">
                    <div class="col-xs-4">
                        <div class="form-group category">
                            <select class="form-control" name="poiCategoryId">
                            	<c:forEach var="categoryItem" items="${allCategories}" varStatus="loop">                          		
                                	<option value="${categoryItem.id}">${categoryItem.name}</option>
                                </c:forEach>
                            </select>
                        </div>
                    </div>
                    <div class="col-xs-8">
                        <div class="form-group address">
                            <i class="fa fa-map-marker fa-fw"></i>
                            <input type="text" class="form-control" id="InputAddress" placeholder="Adresse *" name="poiAddress" required>
                        </div>
                        <div class="form-group phone">
                            <i class="fa fa-phone fa-fw"></i>
                            <input type="text" class="form-control" id="InputPhone" placeholder="Numéro de téléphone" name="poiPhone">
                        </div>
                        <div class="form-group email">
                            <i class="fa fa-envelope fa-fw"></i>
                            <input type="email" class="form-control" id="InputEmail" placeholder="Adresse email" name="poiEmail">
                        </div>
                    </div>
                </div>
                <div class="form-group message">
                    <textarea class="form-control" rows="6" placeholder="Entrez une déscription du lieux, des services proposés et des infos pratiques pouvant être utiles à la communauté*" name="poiDescription" required></textarea>
                </div>
                <div class="form-info"><p>Champs obligatoires*</p></div>
            </div>
            <button class="btn confirm long-button center-block" type="submit"
            	<c:if test="${currentUser == null}">onclick="javascript:return inviteToLogin('Vous devez vous connecter pour pouvoir poster un bon plan.');"</c:if>>Valider</button>
        </form>
    </div>
</div>