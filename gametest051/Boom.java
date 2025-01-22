package com.gametest051;

/**
 * @Author Yanghe
 */
public class Boom {
    int x;
    int y;
    int life = 18;
    boolean isliveBoom = true;

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Boom(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public void lifeDown(){
        if (life > 0) {
            life--;
        }else{
            isliveBoom = false;
        }
    }
}
