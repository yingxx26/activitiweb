package com.imooc.activitiweb.listener;

import org.activiti.bpmn.model.BpmnModel;
import org.activiti.bpmn.model.FlowNode;
import org.activiti.engine.RepositoryService;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.delegate.DelegateExecution;
import org.activiti.engine.delegate.DelegateTask;
import org.activiti.engine.delegate.ExecutionListener;
import org.activiti.engine.delegate.TaskListener;


import org.activiti.engine.impl.bpmn.behavior.MultiInstanceActivityBehavior;
import org.activiti.engine.impl.persistence.entity.ExecutionEntity;
import org.activiti.engine.runtime.Execution;
import org.activiti.engine.runtime.ExecutionQuery;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;

//多实例任务完成事件监听器
//触发完成事件时才会触发此监听器
@Component
public class MyBackListener0 implements TaskListener {

    private static RuntimeService runtimeService;
    private static RepositoryService repositoryService;

    @Autowired
    public void setRuntimeService(RuntimeService runtimeService) {
        this.runtimeService = runtimeService;
    }

    @Autowired
    public void setRepositoryService(RepositoryService repositoryService) {
        this.repositoryService = repositoryService;
    }


    @Override
    public void notify(DelegateTask delegateTask) {

        //获取当前的执行实例
        ExecutionQuery executionQuery = runtimeService.createExecutionQuery();
        ExecutionEntity executionEntity = (ExecutionEntity) executionQuery.executionId(delegateTask.getExecutionId()).singleResult();
        String activityId = executionEntity.getActivityId();
        //获取当前活动节点信息
        FlowNode flowNode = getFlowNode(delegateTask.getProcessDefinitionId(), activityId);

        //获取当前审批人的审批意向
        //String circulationConditions = (String) delegateTask.getVariable("circulationConditions");
        String circulationConditions = "N";
        //处理并行网关的多实例
        if ("N".equals(circulationConditions) && flowNode.getBehavior() instanceof MultiInstanceActivityBehavior) {
//            ExecutionEntity executionEntity = (ExecutionEntity)runtimeService.createExecutionQuery().executionId(delegateTask.getExecutionId()).singleResult();
            String parentId = executionEntity.getParentId();
//此处获得的Execution是包括所有Execution和他们的父Execution，减签的时候要先删除子的才能删除父的
            List<Execution> executions = runtimeService.createExecutionQuery().processInstanceId(delegateTask.getProcessInstanceId()).onlyChildExecutions().list();
            System.out.println(parentId);
            for (Execution execution : executions) {
                if (!execution.getId().equals(parentId) && !executionEntity.getId().equals(execution.getId())) {
                    System.out.println(execution.getParentId());

                    runtimeService.deleteProcessInstance(execution.getProcessInstanceId(), "");
                }
            }

        }

    }

    //获取当前节点的节点信息
    private FlowNode getFlowNode(String processDefinitionId, String activityId) {
        BpmnModel bpmnModel = repositoryService.getBpmnModel(processDefinitionId);
        FlowNode flowNode = (FlowNode) bpmnModel.getMainProcess().getFlowElement(activityId);
        return flowNode;
    }


    /*@Override
    public void notify(DelegateExecution execution) {
        execution.getCurrentFlowElement();
    }*/
}
