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

        $(window).resize(function () {
            $('select').trigger('render');
        });
   });
   
}(jQuery));


