$(document).ready(function () {
    getMovieList();

    function getMovieList() {
        getRequest(
            '/ticket/get/' + sessionStorage.getItem('id'),
            function (res) {
                renderTicketList(res.content);
            },
            function (error) {
                alert(error);
            });
    }

    // TODO:填空
    function renderTicketList(list) {
        $('.movie-on-list').empty();
        var movieDomStr = '';
        list.forEach(function (movie) {
            movieDomStr +=
                "<li class='movie-item card'>" +
                //"<img class='movie-img' src='../images/defaultAvatar.jpg'/>" +
                "<div class='movie-info'>" +
                "<div class='movie-title'>" +
                "<span class='primary-text'>" + movie.id + "</span>" +
                "<span class='label "+(!movie.state ? 'primary-bg' : 'error-bg')+"'>" + (movie.state ? '已过期' : (new Date(movie.startDate)>=new Date()?'未上映':'热映中')) + "</span>" +
                "<span class='movie-want'><i class='icon-heart error-text'></i></span>" +
                "</div>" +
                "<div class='movie-description dark-text'><span></span></div>" +
                "<div>时间：" + movie.time + "</div>" +
                "<div style='display: flex'><span></span><span style='margin-left: 30px;'></span>" +
                "<div class='movie-operation'><a href='/user/ticketDetail?id="+ movie.id +"'>详情</a></div></div>" +
                "</div>"+
                "</li>";
        });
        $('.movie-on-list').append(movieDomStr);
    }


});