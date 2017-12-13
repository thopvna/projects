package com.thopv.projects.ikariam.data.schema.units.properties;

import android.arch.persistence.room.Ignore;

public class Coordinate {
    private float x, y;

    public Coordinate(float x, float y){
        this.x = x;
        this. y = y;

    }
    @Ignore
    public Coordinate(){

    }
    public float getDistance(Coordinate coordinate){
        float tempX = (coordinate.getX() - x) * (coordinate.getX() - x);
        float tempY = (coordinate.getY() - y) * (coordinate.getY() - y);
        return (float) Math.sqrt(tempX + tempY);
    }
    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void setX(float x) {
        this.x = x;
    }

    public void setY(float y) {
        this.y = y;
    }

    @Override
    public String toString() {
        return "(" + x + ", " +y  + ")";
    }
}
