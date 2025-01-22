package com.gametest051;

import java.util.Random;
import java.util.Vector;

/**
 * @Author Yanghe
 */
public class EnemyTank extends Tank implements Runnable {
    private MyTank myTank;

    public EnemyTank(int x, int y, MyTank myTank) {
        super(x, y);
        this.myTank = myTank;
    }

    public MyTank getMyTank() {
        return myTank;
    }

    public void setMyTank(MyTank myTank) {
        this.myTank = myTank;
    }
    //    //初始化敌方坦克子弹
//    Vector<Shot> shots = new Vector<>();

    @Override
    public void run() {
        while (true) {
            //设置敌方坦克更新子弹发射,设置子弹发射条件，坦克存活
//            if (isLive && shots.size()==0){
//                Shot shot = FireAumo(getDirect());
//                //将生成的shot加入到shots集合里
//                shots.add(shot);
//                //启动该新生成的shot线程
//                new Thread(shot).start();
//            }
            //设置敌方坦克最大射击数
            if (isLive && shots.size()<3) {
                Shot shot = FireAumo(getDirect());
                shots.add(shot);
                //初始化了shot后要启动shot线程
                new Thread(shot).start();
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
            //随机步长
            int movenum = Math.abs((int)(50+Math.random() * (100-20)));
            //随机方向
            //=============================索敌判断机制==============================
            boolean MyTankisCloser = findDirect(getDirect(),getSpeed(),getX(),getY(),myTank.getX(),myTank.getY());
            if (MyTankisCloser){
                Random random = new Random();
                setDirect(random.nextInt(2) == 0 ? 1 : 3);
            }else{
                setDirect((int) (Math.random() * 4));
            }
            //实现坦克方向的随机移动
                while (movenum > 0) {
                    //根据坦克方向继续移动
                    boolean IfIsMove = TankMove(getDirect(),getSpeed(),getX(),getY(),myTank.getX(),myTank.getY());
                    if (!IfIsMove){
                        break;
                    }
                    System.out.println(getDirect());
                    movenum--;
                    try {
                        Thread.sleep(15);
                    } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                    }
                }

            //退出线程机制，如何退出while循环
            if (!isLive){
                break;
            }
        }
    }

    @Override
    public boolean TankMove(int direct, int speed,int Enemy_X, int Enemy_Y,int mytank_X,int mytank_Y) {
        boolean isMove = true;
        if (isMove) {
            switch (direct) {
                case 0:
                    if (Enemy_Y > mytank_Y) {
                        moveUp();
                    } else if (Enemy_Y < mytank_Y) {
                        setDirect(2);
                        moveDown();
                        isMove = false;
                        break;
                    }
                    break;
                case 1:
                    moveRight();
                    break;
                case 2:
                    if (Enemy_Y < mytank_Y) {
                        moveDown();
                    } else if (Enemy_Y > mytank_Y) {
                        setDirect(0);
                        moveUp();
                        isMove = false;
                        break;
                    }
                    break;
                case 3:
                    moveLeft();
                    break;
            }
        }
        return isMove;
    }
    public boolean findDirect(int direct, int speed,int Enemy_X, int Enemy_Y,int mytank_X,int mytank_Y){
            return (Enemy_Y > mytank_Y-20 && Enemy_Y < mytank_Y+60);

    }
}
