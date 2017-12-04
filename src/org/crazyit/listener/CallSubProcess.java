package org.crazyit.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.TaskListener;

/**
 * Created by 54472 on 2017/11/30.
 */
public class CallSubProcess implements TaskListener {
    public void notify(DelegateTask delegateTask) {
        DelegateExecution execution =  delegateTask.getExecution();
        String message = (String) execution.getVariable("message");
        System.out.println(message);
    }
}
