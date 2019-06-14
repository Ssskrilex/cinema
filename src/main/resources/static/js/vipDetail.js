$(document).ready(function(){

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');

    getType();
    /*if(sessionStorage.getItem('role') === 'admin')
        getMovieLikeChart();

    function getMovieLikeChart() {
        getRequest(
            '/movie/' + movieId + '/like/date',
            function(res){
                var data = res.content,
                    dateArray = [],
                    numberArray = [];
                data.forEach(function (item) {
                    dateArray.push(item.likeTime);
                    numberArray.push(item.likeNum);
                });

                var myChart = echarts.init($("#like-date-chart")[0]);

                // 指定图表的配置项和数据
                var option = {
                    title: {
                        text: '想看人数变化表'
                    },
                    xAxis: {
                        type: 'category',
                        data: dateArray
                    },
                    yAxis: {
                        type: 'value'
                    },
                    series: [{
                        data: numberArray,
                        type: 'line'
                    }]
                };

                // 使用刚指定的配置项和数据显示图表。
                myChart.setOption(option);
            },
            function (error) {
                alert(error);
            }
        );
    }*/

    function getType() {
        getRequest(
            '/vip/'+movieId + '/' + userId,
            function(res){
                var data = res.content;
                repaintMovieDetail(data);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function repaintMovieDetail(vip) {
        $('#name').text(vip.name);
        $('#description').text(vip.description);
        $('#price').text(new Date(vip.price));
        $('#amount').text(vip.amount);
        $('#discount').text(vip.discount);
    }

    // user界面才有
    /*$('#like-btn').click(function () {
        var url = isLike ?'/movie/'+ movieId +'/unlike?userId='+ userId :'/movie/'+ movieId +'/like?userId='+ userId;
        postRequest(
            url,
            null,
            function (res) {
                isLike = !isLike;
                getMovie();
            },
            function (error) {
                alert(error);
            });
    });*/

    // admin界面才有
    $("#modify-btn").click(function () {
        alert('交给你们啦，修改时需要在对应html文件添加表单然后获取用户输入，提交给后端，别忘记对用户输入进行验证。（可参照添加电影&添加排片&修改排片）');
    });
    $("#delete-btn").click(function () {
        alert('交给你们啦，下架别忘记需要一个确认提示框，也别忘记下架之后要对用户有所提示哦');
    });

});