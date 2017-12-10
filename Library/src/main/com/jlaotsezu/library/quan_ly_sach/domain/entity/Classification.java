package main.com.jlaotsezu.library.quan_ly_sach.domain.entity;


import javax.persistence.*;

@Entity
public class Classification {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int classificationId = 0;
    private String classificationName = null;
    public int getClassificationId() {
        return classificationId;
    }

    public void setClassificationId(int id) {
        this.classificationId = id;
    }

    public String getClassificationName() {
        return classificationName;
    }

    public void setClassificationName(String name) {
        this.classificationName = name;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj instanceof Classification){
            return classificationId == ((Classification) obj).getClassificationId()
                    && classificationName.equalsIgnoreCase(((Classification) obj).getClassificationName());
        }
        return super.equals(obj);
    }

    @Override
    public String toString() {
        return classificationName;
    }
}
