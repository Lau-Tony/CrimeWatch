package com.example.crimewatch;

public class crime {
    String offense;
    double x;
    double y;

    public crime(){
        this.offense = offense;
        this.x = x;
        this.y = y;
    }

    public String getOffense(){
        return this.offense;
    }

    public double getX() {
        return this.x ;
    }

    public double getY(){
        return this.y;
    }

    public void setY(double y) {
        this.y = y;
    }

    public void setX(double x) {

        this.x = x;
    }

    public void setOffense(String offense) {

        this.offense = offense;
    }

    @Override
    public String toString() {
        return "Offense" + offense + "\nx" + x + "\ny" + y;
    }
}

