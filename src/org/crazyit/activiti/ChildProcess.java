package org.crazyit.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by 54472 on 2017/11/27.
 */
public class ChildProcess {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    String processInstanceId = "";

    @Test
    public void deploymentProcessDefinition_inputStream() {
        Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
                .createDeployment()// 创建一个部署对象
                .name("子流程")// 添加部署的名称
                .addClasspathResource("bpmn/ChildProcess.bpmn")
                .deploy();// 完成部署
        System.out.println("部署ID：" + deployment.getId());//
        System.out.println("部署名称：" + deployment.getName());//
    }

    /** 启动流程实例 */
    @Test
    public void startProcessInstance() {
        // 流程定义的key
        String processDefinitionKey = "ChildProcess";

        ProcessInstance pi = processEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例
        System.out.println("流程实例ID:" + pi.getId());// 流程实例
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());// 流程定义ID process:1:4
        processInstanceId = pi.getProcessInstanceId();
    }

    /** 查询当前人的个人任务 */
    @Test
    public void findMyPersonalTask() {
        String assignee = "other";
        List<Task> list = processEngine.getTaskService()// 与正在执行的任务管理相关的Service
                .createTaskQuery()// 创建任务查询对象
//                .processInstanceId(processInstanceId)//使用流程实例ID查询
                /** 排序 */
                .orderByTaskCreateTime().asc()// 使用创建时间的升序排列
                /** 返回结果集 */
                // .singleResult()//返回惟一结果集
                // .count()//返回结果集的数量
                // .listPage(firstResult, maxResults);//分页查询
                .list();// 返回列表
        if (list != null && list.size() > 0) {
            for (Task task : list) {
                System.out.println("任务ID:" + task.getId());
                System.out.println("任务名称:" + task.getName());
                System.out.println("任务的创建时间:" + task.getCreateTime());
                System.out.println("任务的办理人:" + task.getAssignee());
                System.out.println("流程实例ID：" + task.getProcessInstanceId());
                System.out.println("执行对象ID:" + task.getExecutionId());
                System.out.println("流程定义ID:" + task.getProcessDefinitionId());
                System.out.println("########################################################");
            }
        } else {
            System.out.println("task none ");
        }
    }

    /** 任务流向 */
    @Test
    public void completeMyPersonalTask() {
        TaskService taskService = processEngine.getTaskService();
        // 任务ID
        String taskId = "17504";
//        Task task = taskService.createTaskQuery().singleResult();
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("value", 3);
        processEngine.getTaskService()// 与正在执行的任务管理相关的Service
                .complete(taskId, variables);
        System.out.println("完成任务：任务ID：" + taskId);
    }

    @Test
    public void completeTask() {
        String taskId = "32506";
        processEngine.getTaskService()
                .complete(taskId);
        System.out.println("完成任务：任务ID：" + taskId);
    }

    /** 完成当前所有任务 */
    @Test
    public void completeParallelTask() {
        TaskService taskService = processEngine.getTaskService();
        List<Task> tasks = taskService.createTaskQuery()
//                .processInstanceId(processInstanceId)
                .list();
        for (Task task : tasks) {
            taskService.complete(task.getId());
        }
    }
}
