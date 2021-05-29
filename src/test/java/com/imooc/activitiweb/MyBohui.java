package com.imooc.activitiweb;

import com.imooc.activitiweb.util.ActivitiUtil;
import org.activiti.api.runtime.shared.query.Page;
import org.activiti.api.runtime.shared.query.Pageable;
import org.activiti.api.task.runtime.TaskRuntime;
import org.activiti.engine.TaskService;
import org.activiti.engine.task.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class MyBohui {

    @Autowired
    private TaskService taskService;
    @Autowired
    private TaskRuntime taskRuntime;
    @Autowired
    private ActivitiUtil activitiUtil;
    @Autowired
    private SecurityUtil securityUtil;

    //任务查询
    @Test
    public void getTasks() {
        List<Task> list = taskService.createTaskQuery().list();
        for (Task tk : list) {
            System.out.println("Id：" + tk.getId());
            System.out.println("Name：" + tk.getName());
            System.out.println("Assignee：" + tk.getAssignee());
        }
    }

    //查询我的代办任务
    @Test
    public void getTasksByAssignee() {
        List<Task> list = taskService.createTaskQuery()
                .taskAssignee("bajie")
                .list();
        for (Task tk : list) {
            System.out.println("Id：" + tk.getId());
            System.out.println("Name：" + tk.getName());
            System.out.println("Assignee：" + tk.getAssignee());
        }

    }

    @Test
    public void getTasksByAssignee2() {
        Page<org.activiti.api.task.model.Task> tasks = taskRuntime.tasks(Pageable.of(0, 100));
    }


    //执行任务
    @Test
    public void completeTask() {
        taskService.complete("d07d6026-cef8-11ea-a5f7-dcfb4875e032");
        System.out.println("完成任务");

    }

    //拾取任务
    @Test
    public void claimTask() {
        Task task = taskService.createTaskQuery().taskId("36161c71-4795-11eb-95eb-aeed5c486818").singleResult();
        taskService.claim("36161c71-4795-11eb-95eb-aeed5c486818", "bajie");
    }

    //归还与交办任务
    @Test
    public void setTaskAssignee() {
        Task task = taskService.createTaskQuery().taskId("36161c71-4795-11eb-95eb-aeed5c486818").singleResult();
        taskService.setAssignee("e67fa879-479f-11eb-821c-aeed5c486818", null);//归还候选任务
    }

    //普通驳回
    @Test
    public void setTaskAssignee2() throws Exception {
        securityUtil.logInAs("wukong");
        Task task = taskService.createTaskQuery().taskId("b38d5c56-479f-11eb-821c-aeed5c486818").singleResult();
        String processInstanceId = task.getProcessInstanceId();
        String id = task.getId();
        activitiUtil.backProcess(processInstanceId, id);
    }


}
