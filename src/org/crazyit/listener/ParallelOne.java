package org.crazyit.listener;

import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * Created by 54472 on 2017/11/27.
 */
public class ParallelOne implements TaskListener {
    public void notify(DelegateTask delegateTask) {
        System.out.println("task firstTrail start " + "taskId:" + delegateTask.getId());
    }
}
