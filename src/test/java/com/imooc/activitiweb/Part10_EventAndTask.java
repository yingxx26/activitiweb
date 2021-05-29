package com.imooc.activitiweb;

import org.activiti.api.process.model.ProcessInstance;
import org.activiti.api.process.model.builders.StartMessagePayloadBuilder;
import org.activiti.api.process.runtime.ProcessRuntime;
import org.activiti.engine.RuntimeService;
import org.activiti.engine.runtime.Execution;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class Part10_EventAndTask {

    @Autowired
    private RuntimeService runtimeService;

    @Autowired
    private SecurityUtil securityUtil;

    @Autowired
    private ProcessRuntime processRuntime;
    @Test
    public void signalStart() {
        runtimeService.signalEventReceived("Signal_0igedde");
    }

    @Test
    public void msgBack() {
        Execution exec = runtimeService.createExecutionQuery()
                .messageEventSubscriptionName("Message_29ab382")
                .processInstanceId("19c5ed8b-4741-11eb-9896-aeed5c486818")
                .singleResult();
        runtimeService.messageEventReceived("Message_29ab382",exec.getId());

       // runtimeService.startProcessInstanceByMessage("Message_2qvor1p");

//        ProcessInstance pi = processRuntime.start(StartMessagePayloadBuilder
//                        .start("Message_2qvor1p")
//                .build()
//                );

    }


}
