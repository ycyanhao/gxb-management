package com.youedata.core.listener.event;
/**
 * @title: ExecuteNerEvent
 * @projectName com.youedata.core.listener.event
 * @author TC
 * @version 1.0<br>
 * @date 2020/01/06 14:04
 * @description:
 */

import lombok.Data;

/**
 *@ClassName ExecuteNerEvent
 *@Description TODO
 *@Author TC
 *@Date 2020/1/6 14:04
 *@Version 1.0
 **/
@Data
public class ExecuteNerEvent {
    private String pId;

    public ExecuteNerEvent(String pId) {
        this.pId = pId;
    }
}
