package com.example.crimewatch;

public class crime {
    String offense;
    int x;
    int y;


    public crime(String offense, int x, int y){
        this.offense = offense;
        this.x = x;
        this.y = y;
    }

    public String getOffense(){
        return this.offense;
    }

    public int getX() {
        return this.x ;
    }

    public int getY(){
        return this.y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void setX(int x) {

        this.x = x;
    }

    public void setOffense(String offense) {

        this.offense = offense;
    }
}

