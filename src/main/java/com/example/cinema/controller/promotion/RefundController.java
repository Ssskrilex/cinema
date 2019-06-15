package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.CouponService;
import com.example.cinema.vo.ResponseVO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/16.
 */
@RestController
@RequestMapping("/refund")
public class RefundController {

    @Autowired
    RefundService refundService;

    @GetMapping("/get")
    public ResponseVO getRefunds(){
        return refundService.getRefunds();
    }

    @PostMapping("/add")
    public ResponseVO addRefund(@RequestBody RefundForm refundForm){
        return refundService.addRefund(refundForm);
    }

}
