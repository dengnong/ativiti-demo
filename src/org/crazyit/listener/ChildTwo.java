package org.crazyit.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * Created by 54472 on 2017/11/28.
 */
public class ChildTwo implements TaskListener {
    public void notify(DelegateTask delegateTask) {
        System.out.println("I am Two " + "taskId:" + delegateTask.getId());
    }
}
