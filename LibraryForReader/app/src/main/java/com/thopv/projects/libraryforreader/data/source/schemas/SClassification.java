package com.thopv.projects.libraryforreader.data.source.schemas;

import android.arch.persistence.room.Entity;
import android.arch.persistence.room.PrimaryKey;

/**
 * Created by thopv on 12/2/2017.
 */
@Entity
public class SClassification {
    @PrimaryKey(autoGenerate = true)
    private long classificationId;
    private String classificationName;

    public SClassification(long classificationId, String classificationName) {
        this.classificationId = classificationId;
        this.classificationName = classificationName;
    }

    public long getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(long classificationId) {
        this.classificationId = classificationId;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String classificationName) {
        this.classificationName = classificationName;
    }
}
