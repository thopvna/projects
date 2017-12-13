package com.thopv.projects.ikariam.home.presentation.models;

import com.thopv.projects.ikariam.data.schema.logs.ArmyLogType;

import java.util.List;

/**
 * Created by thopv on 11/27/2017.
 */

public class LogModel {
    private long startTime;
    private String content;
    private ArmyLogType type;
    private List<BaseTroop> troops;
    private long endTime;

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public ArmyLogType getType() {
        return type;
    }

    public void setType(ArmyLogType type) {
        this.type = type;
    }

    public List<BaseTroop> getTroops() {
        return troops;
    }

    public void setTroops(List<BaseTroop> troops) {
        this.troops = troops;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public LogModel(long startTime, String content, ArmyLogType type, List<BaseTroop> troops, long endTime) {
        this.startTime = startTime;
        this.content = content;
        this.type = type;
        this.troops = troops;
        this.endTime = endTime;
    }
}
