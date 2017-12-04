package org.crazyit.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * Created by 54472 on 2017/11/28.
 */
public class ChildOne implements TaskListener {
    public void notify(DelegateTask delegateTask) {
        System.out.println("I am One " + "taskId:" + delegateTask.getId());
    }
}
