package com.greyhammer.erpservice.models;

import com.fasterxml.jackson.annotation.JsonView;
import com.greyhammer.erpservice.views.MaterialRequestView;
import com.greyhammer.erpservice.views.ProjectTargetScheduleView;
import com.greyhammer.erpservice.views.ScopeOfWorkView;
import lombok.*;

import javax.persistence.*;
import java.util.Set;

@Getter
@Setter
@Entity
public class ScopeOfWorkTask {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @JsonView({
            ScopeOfWorkView.L1View.class,
            ProjectTargetScheduleView.FullView.class,
            MaterialRequestView.ListView.class})
    private Long id;

    @JsonView({
            ScopeOfWorkView.L1View.class,
            ProjectTargetScheduleView.FullView.class,
            MaterialRequestView.ListView.class
    })
    private String name;

    @JsonView(ScopeOfWorkView.L1View.class)
    private String unit;

    @JsonView(ScopeOfWorkView.L1View.class)
    private Double qty;

    @JsonView(ScopeOfWorkView.L3View.class)
    private Double subconPricePerUnit;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    @JsonView(ScopeOfWorkView.L1View.class)
    private Set<ScopeOfWorkMaterial> materials;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "task")
    private Set<ProjectTargetSchedule> schedules;

    @ManyToOne
    @JsonView(MaterialRequestView.FullView.class)
    private ScopeOfWork scope;

}
