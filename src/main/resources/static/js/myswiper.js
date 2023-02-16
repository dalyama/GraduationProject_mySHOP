/*var swiper_slide = new Swiper(".top-swiper-container", {
    slidesPerView: 1,
    spaceBetween: 30,
    effect: "fade",
    loop: true,
    fadeEffect: {
            crossFade: true,
        },
        
    pagination: {
        el: ".swiper-pagination",
        clickable: true,
    },
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },

    autoplay: {
        delay: 5000,
        disableOnInteraction: false,
    },

});

*/

var suggestions_swiper = new Swiper('.offers-swiper-container', {
    slidesPerView: 3,
    //spaceBetween: 20,
    loop: true,
    watchOverflow: false,
    //centeredSlides: true,
    pagination: {
        //el: '.swiper-pagination',
        //clickable: true,
    },
    navigation: {
        nextEl: ".swiper-button-next",
        prevEl: ".swiper-button-prev",
    },
    
    breakpoints: {
        320: {
            slidesPerView: 2,
            spaceBetween: 20
        },
        480: {
            slidesPerView: 3,
            spaceBetween: 30
        },
        
        850: {
            slidesPerView: 3,
            spaceBetween: 15
        },
        1000:{
        	slidesPerView: 4,
            spaceBetween: 15
        }
        
    }
    ,
    autoplay: {
        delay: 5000,
        disableOnInteraction: false,
    },
});