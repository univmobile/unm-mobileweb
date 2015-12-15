(function($){
    
   $(document).ready(function(){
       if ( $("#filter-bar").length) {
           //Toggle filter
           $('#filter-bar').on('click', '.filter', function () {
               $(this).toggleClass('active');
               window.selectedFeedIds = "";
               $("#filter-bar .active").each(function() {
            	   window.selectedFeedIds = window.selectedFeedIds + $(this).data("feedid") + ",";
               });
               if (window.selectedFeedIds != "") {
	               $.get("news?feedsIds="+window.selectedFeedIds, function(data) {
	            	   $("#accordion").html(data);
	               });
               } else {
            	   $("#accordion").html("");
               }
           });
           //Filter bar scrollable
	       var filterScroll;
	       filterScroll = new IScroll('#filter-bar', { scrollX: true, scrollY: false, mouseWheel: true });
	       document.addEventListener('touchmove', function (e) { e.preventDefault(); }, false);
       }

       
        $('select').customSelect();
        
        $('.navbar-menu-toggle').click(function(){
           $('#mobile-nav').toggleClass('open');
           $('.mask').toggleClass('show');
        });
        $('.close-btn').click(function(){
           $('#mobile-nav').toggleClass('open');
           $('.mask').toggleClass('show');
        });
    	$('a[data-confirm]').click(function(ev) {
    		var href = $(this).attr('href');
    		if (!$('#dataConfirmactionModal').length) {
    			$('body').append('<div id="dataConfirmactionModal" class="modal" role="dialog" aria-labelledby="dataConfirmLabel" aria-hidden="true"><div class="modal-dialog"><div class="modal-content"><div class="modal-header"><button type="button" class="close" data-dismiss="modal" aria-hidden="true">×</button><h3 id="dataConfirmLabel">Merci de confirmer</h3></div><div class="modal-body"></div><div class="modal-footer"><button class="btn" data-dismiss="modal" aria-hidden="true">Non</button><a class="btn btn-danger" id="dataConfirmactionOK">Oui</a></div></div></div></div>');
    		}
    		$('#dataConfirmactionModal').find('.modal-body').text($(this).attr('data-confirm'));
    		$('#dataConfirmactionOK').attr('href', href);
    		$('#dataConfirmactionModal').modal({show:true});
    		return false;
    	});
//        $('.category-btn').click(function(){
//            $(this).toggleClass('active');
//        });
        $('.show-hide-category').click(function(){
            $('.category-wrap .list-wrap').toggle("slide", {direction: "down"});
            $('.category-wrap').toggleClass('open');
        });
        $('.show-hide-search').click(function(){
            $('.search-wrap').toggle("slide", {direction: "up"});
        });
        $('.show-hide-add-poi').click(function () {
            $('.add-poi-screen').toggle("slide", {direction: "down"});
            $('.add-poi-wrap').toggleClass('open');
        });
        $('.show-hide-poi').click(function () {
            $('.poi-wrap').toggle("slide", {direction: "down"});
            $('.poi-wrap').toggleClass('open');
        });
        $('.show-hide-universite-change').click(function () {
            $('.universite-list-wrap').toggle("slide", {direction: "down"});
            $('.universite-list-wrap').toggleClass('open');
        });

        $(window).resize(function () {
            $('select').trigger('render');
        });
   });
   
}(jQuery));


