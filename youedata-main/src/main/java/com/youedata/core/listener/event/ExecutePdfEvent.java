package com.youedata.core.listener.event;
/**
 * @title: ExecutePdfEvent
 * @projectName com.youedata.core.listener.event
 * @author TC
 * @version 1.0<br>
 * @date 2020/01/06 14:11
 * @description:
 */

import lombok.Data;

/**
 *@ClassName ExecutePdfEvent
 *@Description TODO
 *@Author TC
 *@Date 2020/1/6 14:11
 *@Version 1.0
 **/
@Data
public class ExecutePdfEvent {
    private String bankId;
    private String bankName;
    private String pId;
    private String year;

    public ExecutePdfEvent(String bankId, String bankName, String pId, String year) {
        this.bankId = bankId;
        this.bankName = bankName;
        this.pId = pId;
        this.year = year;
    }
}
