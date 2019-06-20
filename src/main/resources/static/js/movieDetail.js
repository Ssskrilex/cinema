$(document).ready(function(){

    var movieId = parseInt(window.location.href.split('?')[1].split('&')[0].split('=')[1]);
    var userId = sessionStorage.getItem('id');
    var isLike = false;

    getMovie();
    getActivities();

    function getActivities() {
        getRequest(
            '/refund/get/'+movieId,
            function (res) {
                var activities = res.content;
                renderActivities(activities);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderActivities(activities) {
        $(".content-activity").empty();
        var activitiesDomStr = "";

        activities.forEach(function (activity) {
            activitiesDomStr+=
                "<li id='schedule-"+ activity.id +"' class='schedule-item' data-schedule='"+JSON.stringify(activity)+"'>"+
                "<div class='activity-container'>" +
                "    <div class='activity-card card'>" +
                "       <div class='activity-line'>" +
                "           <span class='title'>"+activity.id+"</span>" +
                "           <span class='gray-text'>"+activity.description+"</span>" +
                "           <span class='gray-text'>适用于"+activity.sid+"电影</span>"+
                "       </div>" +
                "    </div>" +
                "    <div class='activity-coupon primary-bg'>" +
                "        <span class='title'>开场前"+activity.time+"分钟可以退票</span>" +
                "        <span class='gray-text'>退还原价的"+activity.percent+"%</span>" +
                "    </div>" +
                "</div>"
            "</li>";
        });
        $(".content-activity").append(activitiesDomStr);
    }

    if(sessionStorage.getItem('role') === 'admin')
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
    }

    function getMovie() {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var data = res.content;
                isLike = data.islike;
                repaintMovieDetail(data);
            },
            function (error) {
                alert(error);
            }
        );
    }

    function repaintMovieDetail(movie) {
        !isLike ? $('.icon-heart').removeClass('error-text') : $('.icon-heart').addClass('error-text');
        $('#like-btn span').text(isLike ? ' 已想看' : ' 想 看');
        $('#movie-img').attr('src',movie.posterUrl);
        $('#movie-name').text(movie.name);
        $('#order-movie-name').text(movie.name);
        $('#movie-description').text(movie.description);
        $('#movie-startDate').text(new Date(movie.startDate).toLocaleDateString());
        $('#movie-type').text(movie.type);
        $('#movie-country').text(movie.country);
        $('#movie-language').text(movie.language);
        $('#movie-director').text(movie.director);
        $('#movie-starring').text(movie.starring);
        $('#movie-writer').text(movie.screenWriter);
        $('#movie-length').text(movie.length);
    }

    // user界面才有
    $('#like-btn').click(function () {
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
    });

    // admin界面才有
    $("#modify-btn").click(function () {
        getRequest(
            '/movie/'+movieId + '/' + userId,
            function(res){
                var movie = res.content;
                $('#movie-name-input').val(movie.name);
                $('#movie-date-input').val(new Date(movie.startDate).toLocaleDateString());
                $('#movie-img-input').val(movie.posterUrl);
                $('#movie-description-input').val(movie.description);
                $('#movie-type-input').val(movie.type);
                $('#movie-length-input').val(movie.length);
                $('#movie-country-input').val(movie.country);
                $('#movie-star-input').val(movie.starring);
                $('#movie-director-input').val(movie.director);
                $('#movie-writer-input').val(movie.screenWriter);
                $('#movie-language-input').val(movie.language);            },
            function (error) {
                alert(error);
            }
        );
       //alert('交给你们啦，修改时需要在对应html文件添加表单然后获取用户输入，提交给后端，别忘记对用户输入进行验证。（可参照添加电影&添加排片&修改排片）');
    });
    $("#delete-btn").click(function () {
        //alert('交给你们啦，下架别忘记需要一个确认提示框，也别忘记下架之后要对用户有所提示哦');
        confirm('确认要下架电影吗？') && getRequest('/movie/delete/'+movieId,null,function (res) {
            window.location.href='/admin/movie/manage';
        });
    });

    $("#movie-form-btn").click(function () {
        var formData = getMovieForm();
        if(!validateMovieForm(formData)) {
            return;
        }
        postRequest(
            '/movie/update',
            formData,
            function (res) {
                getMovie();
                $("#movieModal").modal('hide');
            },
            function (error) {
                alert(error);
            });
    });

    function getMovieForm() {
        return {
            id: movieId,
            name: $('#movie-name-input').val(),
            startDate: $('#movie-date-input').val(),
            posterUrl: $('#movie-img-input').val(),
            description: $('#movie-description-input').val(),
            type: $('#movie-type-input').val(),
            length: $('#movie-length-input').val(),
            country: $('#movie-country-input').val(),
            starring: $('#movie-star-input').val(),
            director: $('#movie-director-input').val(),
            screenWriter: $('#movie-writer-input').val(),
            language: $('#movie-language-input').val()
        };
    }

    function validateMovieForm(data) {
        var isValidate = true;
        if(!data.name) {
            isValidate = false;
            $('#movie-name-input').parent('.form-group').addClass('has-error');
        }
        if(!data.posterUrl) {
            isValidate = false;
            $('#movie-img-input').parent('.form-group').addClass('has-error');
        }
        if(!data.startDate) {
            isValidate = false;
            $('#movie-date-input').parent('.form-group').addClass('has-error');
        }
        return isValidate;
    }

    $("#activity-form-btn").click(function () {
        var form = {
            description: $("#activity-description-input").val(),
            sid: movieId,
            time: $("#coupon-target-input").val(),
            percent: $("#coupon-discount-input").val()
        };

        postRequest(
            '/refund/add',
            form,
            function (res) {
                if(res.success){
                    getActivities();
                    $("#activityModal").modal('hide');
                } else {
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $(document).on('click','.schedule-item',function (e) {
        var hall = JSON.parse(e.target.dataset.schedule);
        $("#schedule-edit-id-input").val(hall.id);
        $("#schedule-edit-description-input").val(hall.description);
        $("#schedule-edit-row-input").val(hall.time);
        $("#schedule-edit-column-input").val(hall.percent);
        $('#scheduleEditModal').modal('show');
        $('#scheduleEditModal')[0].dataset.scheduleId = hall.id;
        console.log(hall);
    });

    $('#schedule-edit-form-btn').click(function () {
        var form = {
            id: Number($('#scheduleEditModal')[0].dataset.scheduleId),
            sid: movieId,
            description: $("#schedule-edit-description-input").val(),
            time: $("#schedule-edit-row-input").val(),
            percent: $("#schedule-edit-column-input").val()
        };
        //todo 需要做一下表单验证？

        postRequest(
            '/refund/update',
            form,
            function (res) {
                if(res.success){
                    getActivities();
                    $("#scheduleEditModal").modal('hide');
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $("#schedule-edit-remove-btn").click(function () {
        var r=confirm("确认要删除该影厅信息吗")
        if (r) {
            getRequest(
                '/refund/delete/'+$('#scheduleEditModal')[0].dataset.scheduleId,
                function (res) {
                    if(res.success){
                        getActivities();
                        $("#scheduleEditModal").modal('hide');
                    } else{
                        alert(res.message);
                    }
                },
                function (error) {
                    alert(JSON.stringify(error));
                }
            );
        }
    })
});