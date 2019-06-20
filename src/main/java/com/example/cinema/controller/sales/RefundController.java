package com.example.cinema.controller.sales;

import com.example.cinema.bl.sales.RefundService;
import com.example.cinema.blImpl.sales.RefundServiceImpl;
import com.example.cinema.vo.RefundForm;
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

    @GetMapping("/get/{movieId}")
    public ResponseVO getRefundByMovieId(@PathVariable int movieId){
        return refundService.getRefundByMovieId(movieId);
    }

    @PostMapping("/add")
    public ResponseVO addRefund(@RequestBody RefundForm refundForm){
        return refundService.addRefund(refundForm);
    }

    @PostMapping("/update")
    public ResponseVO updateRefund(@RequestBody RefundForm refundForm){
        return refundService.updateRefund(refundForm);
    }

    @GetMapping("/delete/{id}")
    public ResponseVO deleteRefund(@PathVariable int id){
        return refundService.deletRefund(id);
    }

}
