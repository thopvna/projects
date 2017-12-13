package com.thopv.projects.ikariam.data.schema.units.properties;

public class Resources {
    private int farmerCost;
    private int woodCost;
    private int sulfurCost;
    private int timeCost;
    private int crystalGlassCost;
    public Resources(int farmerCost, int woodCost, int sulfurCost, int timeCost){
        this.farmerCost = farmerCost;
        this.woodCost = woodCost;
        this.sulfurCost = sulfurCost;
        this.timeCost = timeCost;
        this.crystalGlassCost = 0;
    }
    public Resources(){

    }
    public Resources(int farmerCost, int woodCost, int crystalGlassCost, int sulfur, int timeCost){
        this(farmerCost,woodCost,sulfur,timeCost);
        this.crystalGlassCost = crystalGlassCost;
    }
    public void printInfo(){
        System.out.println("Farmer Cost: " +farmerCost );
        System.out.println("Wood Cost: " + woodCost);
        System.out.println("Sulfur Cost: " + sulfurCost);
        System.out.println("Time Cost: " + timeCost);
        System.out.println("Crystal Glass Cost: " + crystalGlassCost);
    }
    public int getFarmerCost() {
        return farmerCost;
    }

    public void setFarmerCost(int farmerCost) {
        this.farmerCost = farmerCost;
    }

    public int getWoodCost() {
        return woodCost;
    }

    public void setWoodCost(int woodCost) {
        this.woodCost = woodCost;
    }

    public int getSulfur() {
        return sulfurCost;
    }

    public void setSulfur(int sulfurCost) {
        this.sulfurCost = sulfurCost;
    }

    public int getTimeCost() {
        return timeCost;
    }

    public void setTimeCost(int timeCost) {
        this.timeCost = timeCost;
    }
}
