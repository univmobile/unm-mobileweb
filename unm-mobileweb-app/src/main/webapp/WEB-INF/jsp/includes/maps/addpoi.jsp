<div class="add-poi-wrap">
    <button class="btn show-hide-add-poi show-add-poi"><i class="icon text-hide"></i></button>
    <div class="add-poi-screen">
        <button class="btn show-hide-add-poi hide-add-poi"><i class="icon text-hide"></i></button>
        <div class="title">Soumettre un nouveau Bon Plan</div>
        <form id="add-poi" class="add-poi">
            <div class="add-poi-name-field">
                <div class="form-pad row">
                    <div class="form-group name col-xs-8 col-xs-offset-4">
                        <input type="text" class="form-control" id="InputName" placeholder="Nom du bon plan *">
                    </div>
                </div>
            </div>
            <div class="form-pad">
                <div class="fields-wrap row">
                    <div class="col-xs-4">
                        <div class="form-group category">
                            <select class="form-control">
                                <option>Séléctionnez une catégorie*</option>
                                <option>1</option>
                                <option>2</option>
                                <option>3</option>
                            </select>
                        </div>
                    </div>
                    <div class="col-xs-8">
                        <div class="form-group address">
                            <i class="fa fa-map-marker fa-fw"></i>
                            <input type="text" class="form-control" id="InputAddress" placeholder="Adresse *">
                        </div>
                        <div class="form-group phone">
                            <i class="fa fa-phone fa-fw"></i>
                            <input type="text" class="form-control" id="InputPhone" placeholder="Numéro de téléphone">
                        </div>
                        <div class="form-group email">
                            <i class="fa fa-envelope fa-fw"></i>
                            <input type="email" class="form-control" id="InputEmail" placeholder="Adresse email">
                        </div>
                    </div>
                </div>
                <div class="form-group message">
                    <textarea class="form-control" rows="6" placeholder="Entrez une déscription du lieux, des services proposés et des infos pratiques pouvant être utiles à la communauté*"></textarea>
                </div>
                <div class="form-info"><p>Champs obligatoires*</p></div>
            </div>
            <button class="btn confirm long-button center-block" type="submit">Valider</button>
        </form>
    </div>
</div>