package com.imooc.activitiweb;
//MyDuoshibohui

import com.imooc.activitiweb.util.ActivitiUtil;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.HistoryService;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.TaskService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;


import org.activiti.engine.history.HistoricActivityInstance;
import org.activiti.engine.history.HistoricActivityInstanceQuery;
import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;

import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.stereotype.Component;
import org.yaml.snakeyaml.util.ArrayUtils;

import java.util.Collections;
import java.util.List;

//多实例任务完成事件监听器
//触发完成事件时才会触发此监听器
@SpringBootTest
public class MyDuoshibohui {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private ActivitiUtil activitiUtil;
    @Autowired
    private SecurityUtil securityUtil;
    @Autowired
    private RepositoryService repositoryService;
    @Autowired
    private RuntimeService runtimeService;
    @Autowired
    private HistoryService historyService;

    //多实例会签驳回
    @Test
    public void bohui() throws Exception {
        securityUtil.logInAs("bajie");
        List<Task> taskList = taskService.createTaskQuery().taskId("388208de-480e-11eb-9832-aeed5c486818").list();
        taskService.setAssignee("e67fa879-479f-11eb-821c-aeed5c486818", null);//归还候选任务

        /*//获取当前的执行实例
        ExecutionQuery executionQuery = runtimeService.createExecutionQuery();
        ExecutionEntity executionEntity = (ExecutionEntity) executionQuery.executionId(task.getExecutionId()).singleResult();
        String activityId = executionEntity.getActivityId();
        //获取当前活动节点信息
        FlowNode flowNode = getFlowNode(task.getProcessDefinitionId(), activityId);

        //获取当前审批人的审批意向
        //String circulationConditions = (String) delegateTask.getVariable("circulationConditions");
        String circulationConditions = "N";
        //处理并行网关的多实例
        if ("N".equals(circulationConditions) && flowNode.getBehavior() instanceof MultiInstanceActivityBehavior) {
//            ExecutionEntity executionEntity = (ExecutionEntity)runtimeService.createExecutionQuery().executionId(delegateTask.getExecutionId()).singleResult();
            String parentId = executionEntity.getParentId();
//此处获得的Execution是包括所有Execution和他们的父Execution，减签的时候要先删除子的才能删除父的
            List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(task.getProcessInstanceId()).onlyChildExecutions().list();
            System.out.println(parentId);
            for (Execution execution : executions) {
                if (!execution.getId().equals(parentId) && !executionEntity.getId().equals(execution.getId())) {
                    System.out.println("====================================" + execution.getParentId());
                    System.out.println("====================================" + execution.getProcessInstanceId());
                    List<HistoricActivityInstance> unfinishedlist = historyService.createHistoricActivityInstanceQuery()
                            .executionId(execution.getId()).unfinished().list();
                    System.out.println(unfinishedlist);
                    List<HistoricActivityInstance> finishedList = historyService.createHistoricActivityInstanceQuery()
                            .executionId(execution.getId()).finished().list();
                    System.out.println(finishedList);
                }
            }

        }*/

    }

    //获取当前节点的节点信息
    private FlowNode getFlowNode(String processDefinitionId, String activityId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityId);
        return flowNode;
    }

}
