(function($){
    
   $(document).ready(function(){
        $('select').customSelect();
        
        $('.navbar-menu-toggle').click(function(){
           $('#mobile-nav').toggleClass('open');
           $('.mask').toggleClass('show');
        });
        $('.close-btn').click(function(){
           $('#mobile-nav').toggleClass('open');
           $('.mask').toggleClass('show');
        });
        $('.category-btn').click(function(){
            $(this).toggleClass('active');
        });
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


