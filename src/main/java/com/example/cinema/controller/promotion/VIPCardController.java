package com.example.cinema.controller.promotion;

import com.example.cinema.bl.promotion.VIPService;
import com.example.cinema.vo.ResponseVO;
import com.example.cinema.vo.VIPCardForm;
import com.example.cinema.vo.VIPTypeForm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * Created by liying on 2019/4/14.
 */
@RestController()
@RequestMapping("/vip")
public class VIPCardController {
    @Autowired
    VIPService vipService;

    @PostMapping("/add")
    public ResponseVO addVIP(@RequestParam int userId){
        return vipService.addVIPCard(userId);
    }
    @GetMapping("{userId}/get")
    public ResponseVO getVIP(@PathVariable int userId){
        return vipService.getCardByUserId(userId);
    }

    @GetMapping("/getVIPInfo")
    public ResponseVO getVIPInfo(){
        return vipService.getVIPInfo();
    }

    @PostMapping("/charge")
    public ResponseVO charge(@RequestBody VIPCardForm vipCardForm){
        return vipService.charge(vipCardForm);
    }

    @PostMapping("/addType")
    public ResponseVO addType(@RequestBody VIPTypeForm vipTypeForm){
        return vipService.addType(vipTypeForm);
    }

    @GetMapping("/getTypes")
    public ResponseVO getTypes(){
        return vipService.getTypes();
    }

    /*@GetMapping("{typeId}/getType")
    public ResponseVO getType(@PathVariable int typeId){
        return vipService.getType(typeId)
    }*/

    @GetMapping("/vip/{id}/{userId}")
    public ResponseVO getTypeById(@PathVariable int id,@PathVariable int userId){
        return vipService.getTypeById(id);
    }
}
