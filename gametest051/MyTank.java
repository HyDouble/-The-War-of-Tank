package com.gametest051;

import java.util.Vector;

/**
 * @Author Yanghe
 */
public class MyTank extends Tank {
    Shot shot = null;
    //实现发射多颗子弹
//    //创建集合
//    Vector <Shot> shots  = new Vector<>();

    public MyTank(int x, int y) {

        super(x, y);
    }
    //射击板块
    public void ShotEnermyTanK() {
        //限制最大子弹同时发射数量
        if (shots.size() ==5){
            return;
        }
        //加入休眠，限制发射速度
        try {
            Thread.sleep(15);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        //创建shot对象，并传入我方坦克的坐标和方向
        Shot shot = FireAumo(getDirect());
//        switch (getDirect()){
//            case 0://向上发射
//                shot = new Shot(getX()+18,getY(),0);
//                break;
//            case 1://向右发射
//                shot = new Shot(getX()+55,getY()+18,1);
//                break;
//            case 2://向下发射
//                shot = new Shot(getX()+18,getY()+55,2);
//                break;
//            case 3://向左发射
//                shot = new Shot(getX(),getY()+18,3);
//                break;
//        }
        //将新创建的shot放入shots集合中
        shots.add(shot);
        //启动子弹发射线程
        new Thread(shot).start();

    }


}
