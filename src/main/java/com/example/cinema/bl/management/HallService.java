package com.example.cinema.bl.management;

import com.example.cinema.vo.HallVO;
import com.example.cinema.vo.ResponseVO;

/**
 * @author fjj
 * @date 2019/4/12 2:01 PM
 */
public interface HallService {
    /**
     * 搜索所有影厅
     * @return
     */
    ResponseVO searchAllHall();
    ResponseVO addHall(HallVO hallVO);
    ResponseVO updateHall(HallVO hallVO);
    ResponseVO deleteHall(int id);
}
