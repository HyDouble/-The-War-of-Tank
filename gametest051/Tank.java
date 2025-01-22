package com.gametest051;

import java.util.Vector;

/**
 * @Author Yanghe
 */
public class Tank {
    private int x;
    private int y;
    //初始化坦克的存活状态
    boolean isLive = true;
    //定义方向变量
    private int direct;
    //定义速度变量
    private int speed = 3 ;

    public Tank(int x, int y) {
        this.x = x;
        this.y = y;
    }
    //创建集合
    Vector<Shot> shots  = new Vector<>();

    public int getDirect() {
        return direct;
    }

    public void setDirect(int direct) {
        this.direct = direct;
    }

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

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }

    //=========封装坦克上右下左移动方法用于被调用=====================

    public void TankMove(int direct, int speed){
        switch (direct) {
            case 0:
                moveUp();
                break;
            case 1:
                moveRight();
                break;
            case 2:
                moveDown();
                break;
            case 3:
                moveLeft();
                break;
        }
    }
    public boolean TankMove(int direct, int speed,int Enemy_X, int Enemy_Y,int mytank_X,int mytank_Y){
        return true;
    }
    //========================定义上下左右移动方法==============================
    public void moveUp(){
        if (getY()>0) {
            y -= speed;
        } else if (getY() <= 0) {
            setDirect(2);
        }
    }
    public void moveDown(){
        if (getY()+60 < 1000) {
            y += speed;
        }else if (getY()+60 >= 1000) {
            setDirect(0);
        }
    }
    public void moveRight(){
        if (getX() + 60 <1000) {
            x += speed;
        }else if (getX() + 60 >= 1000) {
            setDirect(3);
        }
    }
    public void moveLeft(){
        if (getX()>0) {
            x -= speed;
        }else if (getX() <= 0) {
            setDirect(1);
        }
    }

    //定义创建shot对象方法
    Shot shot;
    public Shot FireAumo(int direct) {
        //创建shot对象，并传入我方坦克的坐标和方向
        switch (getDirect()){
            case 0://向上发射
                shot = new Shot(getX()+18,getY(),0);
                break;
            case 1://向右发射
                shot = new Shot(getX()+55,getY()+18,1);
                break;
            case 2://向下发射
                shot = new Shot(getX()+18,getY()+55,2);
                break;
            case 3://向左发射
                shot = new Shot(getX(),getY()+18,3);
                break;
        }
        return shot;
    }
}
