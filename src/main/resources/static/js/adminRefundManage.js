$(document).ready(function() {

    getActivities();

    function getActivities() {
        getRequest(
            '/refund/get',
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


    $("#activity-form-btn").click(function () {
        var form = {
            description: $("#activity-description-input").val(),
            sid: $("#activity-movie-input").val(),
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
        $("#schedule-edit-name-input").val(hall.sid);
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
            sid: $("#schedule-edit-name-input").val(),
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