package com.thopv.projects.ikariam.data.schema.units.properties;

public class Weapon {
    private String name;
    private String rank;
    private int dame;
    private int accuracy;
    private int munition;
    private int maxMunition;
    public Weapon(){

    }
    public Weapon(String name, String rank, int dame, int accuracy){
        this.name = name;
        this.rank = rank;
        this.dame = dame;
        this.accuracy = accuracy;
        this.munition = -1;
        maxMunition = munition;
    }
    public Weapon(String name, String rank, int dame, int accuracy, int munition){
        this(name,rank,dame,accuracy);
        this.munition = munition;
        maxMunition = munition;
    }
    public boolean hasMunition(){
        return munition > 0 || munition == -1;
    }
    public void decreaseMunition(){
        if(munition >= 1)
            munition --;
    }

    public int getMaxMunition() {
        return maxMunition;
    }

    public void printInfo(){
        System.out.println("Weapon Name: " + name + ", Rank: " + rank + ", Dame: " + dame + ", Accuracy: " + accuracy + ", Munition: " + (munition != -1 ? munition : "-"));
    }
    public int getMunition() {
        return munition;
    }

    public void setMunition(int munition) {
        this.munition = munition;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public int getDame() {
        return dame;
    }

    public void setDame(int dame) {
        this.dame = dame;
    }

    public int getAccuracy() {
        return accuracy;
    }

    public void setAccuracy(int accuracy) {
        this.accuracy = accuracy;
    }
}
