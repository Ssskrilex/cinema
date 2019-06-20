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
        $(".content-activity").html("");
        var userBoxs = [];
        postRequest(
            '/users/beyondamount',
            userAmount,
            function (res) {
                userBoxs = res.content;
                var userDomStr = "";
                userBoxs.forEach(function (userBox) {
                    // language=HTML
                    userDomStr+=
                        "<div class='activity-container'>" +
                        "    <div class='activity-card card'>" +
                        "       <div class='activity-line'>" +
                        "           <li>用户id： </li>"+
                        "           <span class='title'>"+userBox.id+"</span>" +
                        "           <li>  用户名： </li>"+
                        "           <span class='gray-text'>"+userBox.username+"</span>" +
                        "           <li>  用户消费金额： </li>"+
                        "           <span class='amount'>"+userBox.amount+"</span>"+
                        "           <button type='button' class='btn btn-primary' id='give-coupon' onclick='givecoupon("+userBox.id+");'>赠送优惠券</button>"+
                        "<script>function givecoupon(userid){\n" +
                        "        var data ={}\n" +
                        "        data.userId = userid;\n" +
                        "        data.couponId = parseInt($(\"#hall-select option:selected\").prop('value'))\n" +
                        "        console.log(data);\n" +
                        "        postRequest(\n" +
                        "            '/coupon/give',\n" +
                        "            data,\n" +
                        "            function (res) {\n" +
                        "                alert('赠送成功')\n" +
                        "            },\n" +
                        "            function (error) {\n" +
                        "                alert('赠送失败')\n" +
                        "            }\n" +
                        "        )\n" +
                        "    }</script>"+
                        "       </div>" +
                        "    </div>" +
                        "</div>";
                });
                $(".content-activity").append(userDomStr);

            },
            function (error) {
                alert(JSON.stringify(error));
            }

        )
    }






});