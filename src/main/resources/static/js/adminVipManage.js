$(document).ready(function() {

    getTypes();

    function getTypes() {
        getRequest(
            '/vip/getTypes',
            function (res) {
                var types = res.content;
                renderVipTypes(types);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderVipTypes(types) {
        $(".content-activity").empty();
        var typesDomStr = "";

        types.forEach(function (type) {
            typesDomStr+=
                "<div class='activity-container'>" +
                "    <div class='activity-card card'>" +
                "       <div class='activity-line'>" +
                "           <span class='title'>"+type.name+"</span>" +
                "           <span class='gray-text'>"+type.description+"</span>" +
                "       </div>" +
                "    </div>" +
                "    <div class='activity-coupon primary-bg'>" +
                "        <span class='title'>价格："+type.price+"</span>" +
                "        <span class='title'>充值满"+type.amount+"减<span class='error-text title'>"+type.discount+"</span></span>" +
                "        <span class='gray-text'><a href='/admin/vipDetail?id="+ type.id +"'>详情</a></span>" +
                "    </div>" +
                "</div>";
        });
        $(".content-activity").append(typesDomStr);
    }

    /*function getAllMovies() {
        getRequest(
            '/movie/all/exclude/off',
            function (res) {
                var movieList = res.content;
                $('#activity-movie-input').append("<option value="+ -1 +">所有电影</option>");
                movieList.forEach(function (movie) {
                    $('#activity-movie-input').append("<option value="+ movie.id +">"+movie.name+"</option>");
                });
            },
            function (error) {
                alert(error);
            }
        );
    }*/

    $("#type-form-btn").click(function () {
        var form = {
            name: $("#name").val(),
            description: $("#description").val(),
            price: $("#price").val(),
            amount: $("#amount").val(),
            discount: $("#discount").val()
    };

        postRequest(
            '/vip/addType',
            form,
            function (res) {
                if(res.success){
                    getTypes();
                    $("#typeModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    //ES6新api 不重复集合 Set
    /*var selectedMovieIds = new Set();
    var selectedMovieNames = new Set();

    $('#activity-movie-input').change(function () {
        var movieId = $('#activity-movie-input').val();
        var movieName = $('#activity-movie-input').children('option:selected').text();
        if(movieId==-1){
            selectedMovieIds.clear();
            selectedMovieNames.clear();
        } else {
            selectedMovieIds.add(movieId);
            selectedMovieNames.add(movieName);
        }
        renderSelectedMovies();
    });

    //渲染选择的参加活动的电影
    function renderSelectedMovies() {
        $('#selected-movies').empty();
        var moviesDomStr = "";
        selectedMovieNames.forEach(function (movieName) {
            moviesDomStr += "<span class='label label-primary'>"+movieName+"</span>";
        });
        $('#selected-movies').append(moviesDomStr);
    }*/
});