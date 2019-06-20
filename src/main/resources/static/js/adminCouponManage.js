$(document).ready(function() {

    var userAmount = 0;
    $("#can-see-num").text(userAmount);
    $('#canview-modify-btn').click(function () {
        $("#canview-modify-btn").hide();
        $("#canview-set-input").val(userAmount);
        $("#canview-set-input").show();
        $("#canview-confirm-btn").show();
    });
    $('#canview-confirm-btn').click(function () {
        userAmount = $("#canview-set-input").val();
        $("#canview-modify-btn").show();
        $("#canview-set-input").hide();
        $("#canview-confirm-btn").hide();
        $("#can-see-num").text(userAmount);
        getUsers();

    });//显示修改会员金额代码
    getCoupon();
    getUsers();



    function getCoupon() {
        var coupons = [];
        getRequest(
            '/coupon/all',
            function (res) {
                coupons = res.content;
                couponId = coupons[0].id;
                coupons.forEach(function (coupon) {
                    $('#hall-select').append("<option value="+ coupon.id +">"+coupon.name+"  "+coupon.description+"</option>");
                });

            },
            function (error) {
                alert(JSON.stringify(error));
            }
        );
    }//显示优惠券代码


    function getUsers(){
        var userBoxs = [];
        postRequest(
            '/users/beyondamount',
            userAmount,
            function (res) {
                userBoxs = res.content;

            },
            function (error) {
            alert(JSON.stringify(error));
        }

        )
    }





});