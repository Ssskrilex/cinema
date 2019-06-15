package com.example.cinema.bl.sales;

import com.example.cinema.vo.RefundForm;
import com.example.cinema.vo.ResponseVO;


public interface RefundService {
    /**
     *增加退票策略
     * @param refundForm  (前端不用传退票策略的id，由数据库生成)
     * @return int
     */
    ResponseVO addRefund(RefundForm refundForm);

    /**
     * 修改退票策略
     * @param refundForm
     * @return
     */
    ResponseVO updateRefund(RefundForm refundForm);

    /**
     *根据退票策略id查看退票策略
     * @param id
     * @return RefundVO
     */
    ResponseVO getRefundById(int id);


    /**
     *根据影厅id查看退票策略
     * @param sid
     * @return List<RefundVO>
     */
    ResponseVO getRefundBySchedule(int sid);

    /**
     *删除策略
     * @param id
     * @return
     */
    ResponseVO deletRefund(int id);
}
