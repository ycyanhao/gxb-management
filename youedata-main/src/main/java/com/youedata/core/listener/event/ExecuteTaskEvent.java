package com.youedata.core.listener.event;

import com.youedata.modular.model.vo.User;
import lombok.Getter;
import lombok.Setter;

/**
 * 执行任务
 *
 * @author hao.yan
 */
@Getter
@Setter
public class ExecuteTaskEvent {

    private String taskId;

    private String type;

    private User user;

    public ExecuteTaskEvent(String taskId, User user) {
        this.taskId = taskId;
        this.user = user;
    }

}
