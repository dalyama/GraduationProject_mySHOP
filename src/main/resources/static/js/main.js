var marginLeft = '280px',
    restMarginLeft = '-280px',
    marginRight = '220px',
    restMarginRight = '-220px';

var dashboard_top = '0px';
var width = (window.innerWidth > 0) ? window.innerWidth : screen.width;
var header_height;

/* Start NEW Variables */
var isHeaderFullWidth = true,
    isHeaderScrollActive = false,
    isDashboardOpened = false,
    isDeviceWidthSmall;


/* Style Header With Small Width */
if (width < 768) {
    header_height = $('#header-sm').height();
    header_height = Math.floor(header_height) + 'px';
    isDeviceWidthSmall = true;
    isDashboardOpened = false;
    if (document.getElementById('sideMenu') != null) {
        document.getElementById('sideMenu').style.top = '0px';
    }

} else {
    header_height = $('#header').height();
    header_height = Math.floor(header_height) + 'px';
    dashboard_top = header_height;
    isDeviceWidthSmall = false;
    if (isHeaderFullWidth) {
        dashboard_top = header_height;
    } else {
        dashboard_top = '0px';
    }
    if (document.getElementById('sideMenu') != null) {
        document.getElementById('sideMenu').style.top = dashboard_top;
    }

}

if (isDeviceWidthSmall) {

}

/* Style Scroll Effect */
if (isHeaderScrollActive) {
    $(document).scroll(function() {
        var y = $(this).scrollBottom();
        var x = Document.getElementById('filter-btn-container');
        if (y > 800) {
            x.style.display = "block";
        } else {
            $('.filter-btn-container').fadeOut();
        }
    });

    var prevScrollpos = window.pageYOffset;
    window.onscroll = function() {
        var currentScrollPos = window.pageYOffset;
        if (prevScrollpos > currentScrollPos) {
            // Shoe header
            document.getElementById("header").style.top = "0";
            document.getElementById("header-sm").style.top = "0";
            var x = document.getElementById("filter-bar");
            if (x != null) {

                document.getElementById("filter-bar").style.top = header_height;
                document.getElementById("search-container").style.top = "80px";

            }
            if (document.getElementById('sideMenu') != null) {
                if (!isDeviceWidthSmall) {
                    document.getElementById('sideMenu').style.top = dashboard_top;
                }
                //
            }

            if (document.getElementById('cart-card-info') != null) {
                document.getElementById('cart-card-info').style.top = "80px";
            }

        } else {
            // hide header

            var x1 = document.getElementById("profile-card");
            var y1 = document.getElementById('profile-card-btn');
            if (y1 != null && y1.value != "closed") {

                x1.style.marginTop = '-525px';
                y1.value = "closed";
            }
            document.getElementById("header").style.top = "-80px";
            document.getElementById("header-sm").style.top = "-80px";
            var x = document.getElementById("filter-bar");
            if (x != null) {
                document.getElementById("filter-bar").style.top = "0";
                document.getElementById("search-container").style.top = "-500px";
            }
            if (document.getElementById('sideMenu') != null) {
                document.getElementById('sideMenu').style.top = "0";
            }
            if (document.getElementById('cart-card-info') != null) {
                document.getElementById('cart-card-info').style.top = "0";
            }


        }

        prevScrollpos = currentScrollPos;
    }

}

/* Style Dashboard */
if (isDashboardOpened) {
    openDashboard();
}

/* END NEW Variables */

function toggleSidebar() {
    document.getElementById("sidebar").classList.toggle('active');
}

function openDashboard() {
    openLeftSide();
}

function openDashboardWithoutMargin() {
    openLeftSide();
}

function closeDashboard() {
    closeLeftSide();
}

function showMenu() {
    var x = document.getElementById("main-menu");
    if (x.style.display === "none") {
        x.style.display = "block";
    } else {
        x.style.display = "none";
    }
}

function showShortProfile() {
    var x = document.getElementById("profile-card");
    var y = document.getElementById('profile-card-btn');

    if (y.value === "closed") {
        // x.style.display="block";
        x.style.marginTop = '100';
        x.style.marginTop = '0';
        y.value = "open";
        document.getElementById('filter-btn').value = 'open';
        document.getElementById('filter-bar').style.marginRight = restMarginRight;
        document.getElementById('filter-btn-container').style.marginRight = '0';
        document.getElementById('main').style.marginRight = '0';
        document.getElementById('footer').style.marginRight = '0';
    } else {
        x.style.marginTop = '-525px';
        // x.style.display = "none";
        y.value = "closed";
    }
}

function showAdvancedSearch() {
    var x = document.getElementById("advance-search");
    var y = document.getElementById("advance");
    if (x.style.display === "none") {

        x.style.display = "block";
        y.value = "Hide advanced search box";
    } else {
        x.style.display = "none";
        y.value = "Show advanced search box";
    }
}

$(document).ready(function() {
    $('.search').on('keyup', function() {
        var searchTerm = $(this).val().toLowerCase();
        $('#userTbl tbody tr').each(function() {
            var lineStr = $(this).text().toLowerCase();
            if (lineStr.indexOf(searchTerm) === -1) {
                $(this).hide();
            } else {
                $(this).show();
            }
        });
    });
});

function openFilterMargin() {
    var x = document.getElementById('filter-btn');
    var y = document.getElementById('filter-btn-container');

    if (x.value === 'open') {
        x.value = 'close';
        y.style.marginRight = marginRight;
        var r = document.getElementById("profile-card");
        var z = document.getElementById('profile-card-btn');
        if (r != null) {
            r.style.marginTop = '-525px';
            z.value = "closed";
        }
        document.getElementById('filter-bar').style.marginRight = '0';
        document.getElementById('main').style.marginRight = marginRight;
        document.getElementById('footer').style.marginRight = marginRight;
        closeDashboard();
    } else {
        x.value = 'open';
        document.getElementById('filter-bar').style.marginRight = restMarginRight;
        y.style.marginRight = '0';
        document.getElementById('filter-btn').style.marginRight = '0';
        document.getElementById('main').style.marginRight = '0';
        document.getElementById('footer').style.marginRight = '0';
    }
}

function openFilterWithoutMargin() {
    var x = document.getElementById('filter-btn');
    var y = document.getElementById('filter-btn-container');

    if (x.value === 'open') {
        x.value = 'close';
        y.style.marginRight = marginRight;
        document.getElementById('filter-bar').style.marginRight = '0';
        closeDashboard();
        document.getElementById('main').style.marginLeft = '0';
        document.getElementById('header').style.marginLeft = '0';
        document.getElementById('footer').style.marginLeft = '0';
    } else {
        x.value = 'open';
        document.getElementById('filter-bar').style.marginRight = restMarginRight;
        y.style.marginRight = '0';
    }
}

/*
 * function display(){ console.log("display"); }
 */



function showCategoriesMain() {
    console.log('showcategory');
    //document.getElementById('categories-main').style.display = 'block';
    document.getElementById('categories-main').style.marginTop = '0px';
}

function hideCategoriesMain() {
    document.getElementById('categories-main').style.marginTop = '-500px';

}

/************************/
var restMargin = '-280px';
var dir = document.getElementsByTagName("BODY")[0].getAttribute("dir");
$(window).resize(function() {
    var current_width = $(window).width();
    if (document.getElementById('sideMenu') != null) {
        initViewDashboard(current_width)
    }

});

function initViewDashboard(current_width) {
    if (current_width < 768) {
        console.log("init view close Left side width is : " + current_width)
        closeLeftSide();
        document.getElementById('sideMenu').style.top = '0';
    } else {
        console.log("init view open Left side width is : " + current_width)
        document.getElementById('sideMenu').style.top = '80px';
        openLeftSide();
    }
}

function openLeftSide() {

    // with out any change in toggle btns
    if (dir == 'ltr') {
        document.getElementById('sideMenu').style.marginLeft = '0';
    } else {
        document.getElementById('sideMenu').style.marginRight = '0';
    }

    if ($(window).width() >= 768) {
        // with change toggle btns
        document.getElementById('header-open-dashboard').style.display = "none";
        document.getElementById('header-close-dashboard').style.display = "block";
    }
}

function closeLeftSide() {
    if (dir == 'ltr') {
        // margin back left 
        document.getElementById('sideMenu').style.marginLeft = restMargin;
    } else {
        // margin back right
        document.getElementById('sideMenu').style.marginRight = restMargin;
    }
    if ($(window).width() >= 768) {
        // toggle header btn ( open ---> close )
        document.getElementById('header-open-dashboard').style.display = "block"
        document.getElementById('header-close-dashboard').style.display = "none";
    }
}