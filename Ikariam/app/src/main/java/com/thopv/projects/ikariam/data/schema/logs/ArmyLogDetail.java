package com.thopv.projects.ikariam.data.schema.logs;

import com.thopv.projects.ikariam.home.presentation.models.BaseTroop;

import java.util.List;

/**
 * Created by thopv on 11/27/2017.
 */

public class ArmyLogDetail {
    private long startTime;
    private long endTime;
    private String content;
    private String type;
    private List<BaseTroop> troops;

    public ArmyLogDetail(long startTime, long endTime, String content, String type, List<BaseTroop> troops) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.content = content;
        this.type = type;
        this.troops = troops;
    }

    public long getStartTime() {
        return startTime;
    }

    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }

    public long getEndTime() {
        return endTime;
    }

    public void setEndTime(long endTime) {
        this.endTime = endTime;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<BaseTroop> getTroops() {
        return troops;
    }

    public void setTroops(List<BaseTroop> troops) {
        this.troops = troops;
    }

    public static class Builder {
        private long startTime;
        private long endTime;
        private String content;
        private String type;
        private List<BaseTroop> troops;

        public Builder(ArmyLog armyLog){
            this.startTime = armyLog.getStartTime();
            this.content = armyLog.getContent();
            this.type = armyLog.getType();
        }

        public Builder setEndTime(long endTime) {
            this.endTime = endTime;
            return this;
        }


        public Builder setTroops(List<BaseTroop> troops) {
            this.troops = troops;
            return this;
        }

        public ArmyLogDetail build() {
            return new ArmyLogDetail(startTime, endTime, content, type, troops);
        }
    }
}
