package com.greyhammer.erpservice.events;

import com.greyhammer.erpservice.models.Project;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.ApplicationEvent;

@Getter
@Setter
public class CompleteClientApprovalEvent extends ApplicationEvent {
    private Project project;

    public CompleteClientApprovalEvent(Object source) {
        super(source);
    }
}
