package com.example.cinema.vo;

public class ExpenseRecordVO {
    private TicketVO ticketVO;
    private VIPChargeRecordVO vipChargeRecordVO;

    public ExpenseRecordVO() {
    }

    public TicketVO getTicketVO() {
        return ticketVO;
    }

    public void setTicketVO(TicketVO ticketVO) {
        this.ticketVO = ticketVO;
    }

    public VIPChargeRecordVO getVipChargeRecordVO() {
        return vipChargeRecordVO;
    }

    public void setVipChargeRecordVO(VIPChargeRecordVO vipChargeRecordVO) {
        this.vipChargeRecordVO = vipChargeRecordVO;
    }
}
