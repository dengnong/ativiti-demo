package org.crazyit.listener;

import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.JavaDelegate;

import java.util.logging.Logger;

/**
 * Created by 54472 on 2017/11/28.
 */
public class DoTransaction implements JavaDelegate {
    private static final Logger log = Logger.getLogger(Initialization.class.getName());

    public void execute(DelegateExecution execution) {
        // varInSubprocess<->varOutFromSubprocess
        String varOutFromSubprocess = (String)execution.getVariable("varOutFromSubprocess");
        log.info("in mainprocess get(varOutFromSubprocess): " + varOutFromSubprocess);

        log.info("variavles=" + execution.getVariables());
        execution.setVariable("m:dt", "Mainprocess:DoTransaction");
        log.info("I am DoTransaction in mainprocess.");
    }
}
