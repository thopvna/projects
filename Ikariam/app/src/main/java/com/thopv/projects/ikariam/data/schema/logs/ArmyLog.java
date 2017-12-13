package com.thopv.projects.ikariam.data.schema.logs;

import android.arch.persistence.room.Entity;

/**
 * Created by thopv on 10/27/2017.
 */
@Entity(primaryKeys = {"startTime"})
public class ArmyLog {
    private long startTime;
    private String content;
    private String type;

    public ArmyLog(long startTime, String content, String type) {
        this.startTime = startTime;
        this.content = content;
        this.type = type;
    }



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

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }


    public static class Builder {
        private long startTime;
        private String content;
        private String type;

        public Builder setStartTime(long startTime) {
            this.startTime = startTime;
            return this;
        }

        public Builder setContent(String content) {
            this.content = content;
            return this;
        }

        public Builder setType(String type) {
            this.type = type;
            return this;
        }

        public ArmyLog build() {
            return new ArmyLog(startTime, content, type);
        }
    }
}
