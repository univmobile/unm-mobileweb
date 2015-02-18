<div class="poi-wrap" style="display:none;">
    <div class="poi-screen">
        <div class="poi-header">
            <span class="category-icon category-7"><i class="icon text-hide"></i></span>
            <div class="title"></div>
            <button class="btn show-hide-poi hide-poi"><i class="icon text-hide"></i></button>
        </div>
        <div class="poi-info">
            <address><i class="fa fa-map-marker fa-fw"></i><span id="addressPOI"></span></address>
            <div class="phone"><i class="fa fa-phone fa-fw"></i><span id="phonePOI"></span></div>
            <div class="email"><i class="fa fa-envelope fa-fw"></i><span id="emailPOI"></span></div>
        </div>
        <div class="tabpanel" role="tabpanel">
            <ul class="nav nav-tabs" role="tablist">
                <li role="presentation" class="one active"><a href="#plan" aria-controls="plan" role="tab" data-toggle="tab"><i class="fa fa-info-circle"></i></a></li>
                <li role="presentation" class="two"><a href="#comments" aria-controls="comments" role="tab" data-toggle="tab"><i class="fa fa-comments"></i></a></li>
                <li id="menuTab" role="presentation" class="three"><a href="#menu" aria-controls="menu" role="tab" data-toggle="tab"><i class="fa fa-cutlery"></i></a></li>
            </ul>
            <div class="tab-content">
                <div role="tabpanel" class="tab-pane fade in active" id="plan">
                    <div class="body"></div>
                    <button class="btn show-hide-search show-search-long"><i class="icon"></i>Rechercher</button>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="comments">
                    <ul class="comments-list">
                        <li class="comment-item">
                            <div class="author">Jeremy D.</div>
                            <time>il y a 8 heures</time>
                            <p class="comment-text">« Très bon accueil, personnel serviable et très à l’écoute. Prise en charge très rapide ! »</p>
                        </li>
                        <li class="comment-item">
                            <div class="author">Jeremy D.</div>
                            <time>il y a 8 heures</time>
                            <p class="comment-text">« Très bon accueil, personnel serviable et très à l’écoute. Prise en charge très rapide ! »</p>
                        </li>
                    </ul>
                    <div class="new-comment-wrap">
                        <form id="new-comment" class="new-comment">
                            <div class="form-group comment-field">
                                <textarea class="form-control" rows="6" placeholder=""></textarea>
                            </div>
                            <button type="submit" class="btn long-button submit center-block">Valider</button>
                        </form>
                    </div>
                </div>
                <div role="tabpanel" class="tab-pane fade" id="menu">...menu</div>
            </div>
        </div>
    </div>
</div>