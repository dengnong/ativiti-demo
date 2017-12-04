package org.crazyit.activiti;

import org.activiti.engine.ProcessEngine;
import org.activiti.engine.ProcessEngines;
import org.activiti.engine.TaskService;
import org.activiti.engine.history.HistoricTaskInstance;
import org.activiti.engine.repository.Deployment;
import org.activiti.engine.runtime.ProcessInstance;
import org.activiti.engine.task.Task;
import org.junit.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 第一个流程运行类

 */

public class BasicBranch {
    ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();
    String processInstanceId = "";

    @Test
    public void deploymentProcessDefinition() {
        Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
                .createDeployment()// 创建一个部署对象
                .name("基本分支")// 添加部署的名称
                .addClasspathResource("bpmn/BasicBranch.bpmn")
                .deploy();// 完成部署
        System.out.println("部署ID：" + deployment.getId());//
        System.out.println("部署名称：" + deployment.getName());//
    }

    /** 启动流程实例 */
    @Test
    public void startProcessInstance() {
        String processDefinitionKey = "process1";// 流程定义的key
        ProcessInstance pi = processEngine.getRuntimeService()// 与正在执行的流程实例和执行对象相关的Service
                .startProcessInstanceByKey(processDefinitionKey);// 使用流程定义的key启动流程实例
        System.out.println("流程实例ID:" + pi.getId());// 流程实例
        System.out.println("流程定义ID:" + pi.getProcessDefinitionId());// 流程定义ID process:1:4
    }

    /** 查询当前任务 */
    @Test
    public void findMyPersonalTask() {
        List<Task> list = processEngine.getTaskService()// 与正在执行的任务管理相关的Service
                .createTaskQuery()// 创建任务查询对象
                /** 查询条件（where部分） */
//                .taskAssignee(assignee)// 指定个人任务查询，指定办理人
//                .processInstanceId("97501")//使用流程实例ID查询
                /** 排序 */
                .orderByTaskCreateTime().asc()// 使用创建时间的升序排列
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
        }
    }

    /** 任务流向other */
    @Test
    public void completeMyPersonalTask() {
        TaskService taskService = processEngine.getTaskService();
        // 任务ID
        Task task = taskService.createTaskQuery().singleResult();
        // 完成任务的同时，设置流程变量，使用流程变量用来指定完成任务后，下一个连线，BasicBranch.bpmn文件中${type == 2}
        Map<String, Object> variables = new HashMap<String, Object>();
        variables.put("type", 2);
        processEngine.getTaskService()// 与正在执行的任务管理相关的Service
                .complete(task.getId(), variables);
        System.out.println("完成任务：任务ID：" + task.getId());
    }

    /**查询历史记录*/
    @Test
    public void findHistoryTask(){
        List<HistoricTaskInstance> list = processEngine.getHistoryService()//与历史数据（历史表）相关的Service
                .createHistoricTaskInstanceQuery()//创建历史任务实例查询
                .processInstanceId(processInstanceId)//
                .orderByHistoricTaskInstanceStartTime().asc()
                .list();
        if(list!=null && list.size()>0){
            for(HistoricTaskInstance hti:list){
                System.out.println(hti.getId()+"    "+hti.getName()+"    "+hti.getProcessInstanceId()+"   "+hti.getStartTime()+"   "+hti.getEndTime()+"   "+hti.getDurationInMillis());
                System.out.println("################################");
            }
        }
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
