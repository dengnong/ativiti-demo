package org.crazyit.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.junit.Test;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by 54472 on 2017/11/28.
 */
public class MainprocessTest {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();

    @Test
    public void deploymentProcessDefinition() {
        Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
                .createDeployment()// 创建一个部署对象
                .name("部署主流程")// 添加部署的名称
                .addClasspathResource("bpmn/MainProcess.bpmn")
                .deploy();// 完成部署

        deployment = processEngine.getRepositoryService()
                .createDeployment()
                .name("部署子流程")
                .addClasspathResource("bpmn/SubProcess.bpmn")
                .deploy();

        Map<String, Object> approveParam  = new HashMap<String, Object>();
        approveParam.put("message", "子流程输出");
        //启动流程
        ProcessInstance pi = processEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey("Mainprocess", approveParam);
    }


    @Test
    public void TestChildProcess() {
// prepare data packet
        Map<String, Object> approveParam  = new HashMap<String, Object>();
        approveParam.put("message", "子流程输出");
    }
}
