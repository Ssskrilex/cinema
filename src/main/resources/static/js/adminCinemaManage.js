$(document).ready(function() {

    var canSeeDate = 0;

    getCanSeeDayNum();
    getCinemaHalls();

    function getCinemaHalls() {
        var halls = [];
        getRequest(
            '/hall/all',
            function (res) {
                halls = res.content;
                renderHall(halls);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    function renderHall(halls){
        $('#hall-card').empty();
        var hallDomStr = "";
        halls.forEach(function (hall) {
            var seat = "";
            for(var i =0;i<hall.row;i++){
                var temp = ""
                for(var j =0;j<hall.column;j++){
                    temp+="<div class='cinema-hall-seat'></div>";
                }
                seat+= "<div>"+temp+"</div>";
            }
            var hallDom =
                "<li id='schedule-"+ hall.id +"' class='schedule-item' data-schedule='"+JSON.stringify(hall)+"'>"+
                "<div>" +
                "<span class='cinema-hall-name'>"+ hall.name +"</span>" +
                "<span class='cinema-hall-size'>"+ hall.column +'*'+ hall.row +"</span>" +
                "</div>" +
                "<div class='cinema-seat'>" + seat +
                "</div>" +
                "</li>";
            hallDomStr+=hallDom;
        });
        $('#hall-card').append(hallDomStr);
    }

    function getCanSeeDayNum() {
        getRequest(
            '/schedule/view',
            function (res) {
                canSeeDate = res.content;
                $("#can-see-num").text(canSeeDate);
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }

    $('#canview-modify-btn').click(function () {
       $("#canview-modify-btn").hide();
       $("#canview-set-input").val(canSeeDate);
       $("#canview-set-input").show();
       $("#canview-confirm-btn").show();
    });

    $('#canview-confirm-btn').click(function () {
        var dayNum = $("#canview-set-input").val();
        // 验证一下是否为数字
        postRequest(
            '/schedule/view/set',
            {day:dayNum},
            function (res) {
                if(res.success){
                    getCanSeeDayNum();
                    canSeeDate = dayNum;
                    $("#canview-modify-btn").show();
                    $("#canview-set-input").hide();
                    $("#canview-confirm-btn").hide();
                } else{
                    alert(res.message);
                }
            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    });

    $("#movie-form-btn").click(function () {
        var formData = getMovieForm();
        /*if(!validateMovieForm(formData)) {
            return;
        }*/
        postRequest(
            '/hall/add',
            formData,
            function (res) {
                getCinemaHalls();
                $("#movieModal").modal('hide');
            },
            function (error) {
                alert(error);
            });
    });

    function getMovieForm() {
        return {
            name: $('#movie-name-input').val(),
            row: $('#movie-row-input').val(),
            column: $('#movie-column-input').val()
        };
    }

    $(document).on('click','.schedule-item',function (e) {
        var hall = JSON.parse(e.target.dataset.schedule);
        $("#schedule-edit-id-input").val(hall.id);
        $("#schedule-edit-name-input").val(hall.name);
        $("#schedule-edit-row-input").val(hall.row);
        $("#schedule-edit-column-input").val(hall.column);
        $('#scheduleEditModal').modal('show');
        $('#scheduleEditModal')[0].dataset.scheduleId = hall.id;
        console.log(hall);
    });

    $('#schedule-edit-form-btn').click(function () {
        var form = {
            id: Number($('#scheduleEditModal')[0].dataset.id),
            name: $("#schedule-edit-name-input").val(),
            row: $("#schedule-edit-row-input").val(),
            column: $("#schedule-edit-column-input").val()
        };
        //todo 需要做一下表单验证？

        postRequest(
            '/hall/update',
            form,
            function (res) {
                if(res.success){
                    getCinemaHalls();
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
                '/hall/delete'+$('#scheduleEditModal')[0].dataset.id,
                function (res) {
                    if(res.success){
                        getCinemaHalls();
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