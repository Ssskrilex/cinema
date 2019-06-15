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
                "<div class='activity-container'>" +
                "    <div class='activity-card card'>" +
                "       <div class='activity-line'>" +
                "           <span class='title'>"+activity.id+"</span>" +
                "           <span class='gray-text'>"+activity.description+"</span>" +
                "       </div>" +
                "    </div>" +
                "    <div class='activity-coupon primary-bg'>" +
                "        <span class='title'>开场前"+activity.time+"分钟可以退票</span>" +
                "        <span class='gray-text'>退还原价的"+activity.percent+"%</span>" +
                "    </div>" +
                "</div>";
        });
        $(".content-activity").append(activitiesDomStr);
    }


    $("#activity-form-btn").click(function () {
        var form = {
            description: $("#activity-description-input").val(),
            time: $("#coupon-target-input").val(),
            percent: $("#coupon-discount-input").val(),
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
});