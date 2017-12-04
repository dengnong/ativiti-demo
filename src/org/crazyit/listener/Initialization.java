package org.crazyit.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

/**
 * Created by 54472 on 2017/11/28.
 */
public class Initialization implements JavaDelegate {
    private static final Logger log = Logger.getLogger(Initialization.class.getName());

    public void execute(DelegateExecution execution) {
        log.info("variavles=" + execution.getVariables());
        execution.setVariable("m:i", "Mainprocess:Initialization");
        log.info("I am Initialization in mainprocess.");

        execution.setVariable("varOutFromMainprocess", "AAAA");
        log.info("in mainprocess set(varOutFromMainprocess): " + execution.getVariable("varOutFromMainprocess"));
    }
}
