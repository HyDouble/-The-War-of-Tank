package com.gametest051;

import javax.swing.*;
import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Vector;

/**
 * @Author Yanghe
 *
 * 1.这里是坦克大战游戏的绘图区域
 * 坦克的转向，switch,Tank.java设置方向变量
 * 监听，Mypanel.java 实现KeyListener接口。重写了三个监听方法，Tankgame02.java中设置监听事件
 * 坦克移动,封装成方法放置在Tank.java
 * 坦克初始化中设置了速度，
 *
 * 2.===================绘制敌人坦克===================
 * 定义Enemytank.java子类
 * Mypanel中定义Vector集合(考虑多线程问题)
 * 初始化敌人坦克
 * 绘制地方坦克，注意遍历绘制，设置不是num而是size，因为地方坦克会被消灭，不能让他一直画
 *
 * 3.==================绘制子弹，并引入线程===========================================
 * 3.定义子弹类（子弹方向、我方坦克射击子弹方法）子弹线程
 * 3.1子弹类 Shot.java文件,设置shot的属性，实现shot根据坦克方向，实现自身的坐标系变化，创建线程，设置子弹消失触发机制（AumoisLive、
 * 地图边界等）
 * 设置监测机制，当按下J时发射子弹，在Mypanel类中加入
 * if (e.getKeyCode() == KeyEvent.VK_J) {
 *             mytank.ShotEnermyTanK();
 *         }
 * 调用ShotEnermyTanK()方法，实现子弹的初始化和线程开启
 * 3.2 Mytank.java中设置ShotEnermyTanK射击板块，实现Shot类的shot对象初始化，并启动子弹线程
 * 3.3 Mypanel类中设置子弹绘制方法drawAumo,参数是shot对象
 * 3.4 在paint中调用drawAumo方法，用if语句判断是否符合绘制子弹的条件
 * 3.5 为了实现子弹的不断重绘，即当玩家不控制坦克移动时也能实现repaint，设置Mypanel类线程，改写run方法
 * 3.6 启动线程（在创建面板的地方启动线程）
 *
 * 4.==========================四版=============================================
 * 4.1实现敌方坦克发射子弹，并可发射多颗子弹，目前先只发射一颗子弹
 * EnemyTank类中定义shotsVector
 * 在初始化敌方坦克的敌方初始化敌方坦克的子弹,并启动线程
 * 判断发射条件，绘制发射的子弹
 * 4.2实现我方坦克子弹击中敌方坦克后，敌方坦克消失，我方子弹消失
 * 4.2.1子弹击中目标方法
 * 新建hitTank方法，传入shot对象和坦克对象，设置子弹坐标位置接触坦克坐标位置就算击中目标
 * 上下和左右两组，有着不同的坐标位置范围
 * Tank类设置isLive属性，初始化每一个坦克都是true
 * hitTank方法中，当子弹击中目标后，设置isLive、AumoisLive为false
 * 4.2.2 调用hitTank方法，实现实时判断子弹是否击中目标，在Mypanel线程中的Run方法中调用hitTank方法
 * 4.2.2.1 处理调用hitTank方法传入shot对象是空指针异常，因为当我方坦克未发射子弹时，shot为null，需设置调用条件
 * if (mytank.shot != null && mytank.shot.AumoisLive) {}
 * 4.2.3 在paint方法中，加入isLive属性条件，绘制存活的坦克
 * if (enemyTank.isLive == true) {}
 * 4.3实现子弹击中坦克的爆炸效果
 * 4.3.1 定义boom类，设置属性x,y,life（生命周期，实现图片的动画播放）,isliveboom
 * Mypanel类中定义Boom类的vector集合
 * 初始化三个image
 * 将三个image指向图片，图片以相对路径保存，保存到软件包中的src根目录
 * 4.3.2创建Boom类对象
 * 在hitTank方法初始化boom对象，并加入到Vector中的booms
 * 4.3.3绘制坦克爆炸效果
 * 在paint()方法中绘制
 * 首先遍历booms集合，取出boom对象，为处理运行速度导致的第一个坦克爆炸不显示的问题
 * 在取出boom对象后，加入
 * try {
 *                 Thread.sleep(20);
 *             } catch (InterruptedException e) {
 *                 throw new RuntimeException(e);
 *             }
 *之后就是绘制坦克爆炸，三个if，确保动画显示,再life--，再删去booms中的life==0的boom
 *
 * 4.4 实现坦克的随机移动
 * 4.4.1坦克自由移动即实现敌方坦克线程
 * EnemyTank类实现Runnable，重写run方法，双重while循环随机步长和方向，实现敌方坦克随机移动
 * 注意switch不要忘了break
 * 注意设置敌方坦克的休眠，以及退出while的islive属性变化
 * 4.5限制坦克移动范围
 * 4.5.1在Tank类修改四个移动方法，加入if条件，小于四个边界再执行+=speed等，再写个else if 调转方向
 * 4.5.2在Tank类定义Tankmove方法，实现封装
 * 4.5.3在EnemyTank重写Tankmove，传入int direct, int speed,int Enemy_X, int Enemy_Y,int mytank_X,int mytank_Y
 * 4.5.4 在EnemyTank定义finddirect方法，实现索敌
 * random.nextInt(2) == 0 ? 1 : 3 实现在0或3中随机生成
 * 4.5.5 当敌方坦克和我方坦克的y相等时，会触发索敌机制即调转左右方向，但调转左右方向后敌方坦克的Y和我方坦克的Y不同了，又重新随机方向，导致鬼畜
 * 解决办法
 * 造成该bug的本质原因是当Y==Y时，敌方坦克触发调转上下方法，调用后进入下一次循环带着初始的方向继续移动，让后又触发调转方向，造成鬼畜
 * EnemyTank类中改写Tankmove方法类型，输出布尔值，返回一个isMove，判断坦克是否在移动状态，为假则break，退出当前的while循环进
 * 入方向选择机制模块
 *
 * 5.============================================五版====================================
 * 5.1发射多颗子弹并，限制我方坦克子弹发射数量
 * 在Mytank中设置shots Vector集合，加入shot进入集合中，并实现shot生成条件，限制子弹发射数量
 * 5.1.2在Mypanel中绘制子弹区域遍历shots集合绘制，同步绘制限制条件，并加入shots删除销毁的子弹机制
 * 5.1.3修改Mypanel的hitTank方法，实现shots子弹遍历，保证每颗子弹都可以使敌方坦克爆炸
 * 5.2实现敌方坦克发射多颗子弹
 * 5.2.1 在Tank类中定义FireAumo方法，创建shot对象，并返回shot
 * 5.2.2 在EnemyTankle类中，设置敌方坦克更新子弹发射,设置子弹发射条件，坦克存活
 * 将新生成的shot加入到shots集合中，并启动该子弹线程
 *
 * 5.3实现敌人子弹可以击中我方坦克并形成爆炸
 * 先在Tank类中定义Vector，为后续封装方法做准备
 * 5.3.1定义Hitted方法，实现子弹击中反馈
 * 5.3.2重写hitTank方法，传入父类Tank，并使用instanceof判断类型，进行类型强转，并调用hitted方法
 * 5.3.3重新在run中，分别遍历我方坦克子弹和敌方坦克即其子弹，调用hitTank方法（注意子弹是对方，对象是自己，防止陷入死循环不能repaint）
 * 5.3.4在绘制我方坦克和我方坦克发射子弹部分加入if条件，判断mytank的是否存活
 * 5.3.5 在run方法中设置复活机制，并更新敌方坦克传入的mytank对象，更新索敌机制
 */
@SuppressWarnings("ALL")
public class MyPanel extends JPanel implements KeyListener,Runnable {
    //定义我方坦克
    MyTank mytank = null;
    //定义敌方坦克
    Vector<EnemyTank> enemyTanks = new Vector<>();
    int EnemyTanksNum = 3;
    //定义炸弹效果
    //定义一个Vector，用于存放炸弹
    //当子弹击中坦克时，加入boom对象到集合中，hitTank方法中
    Vector<Boom> booms = new Vector<>();
    //定义三张炸弹图片，用于显示爆炸效果
    Image image1 = null;
    Image image2 = null;
    Image image3 = null;



    //=====================初始化坦克=========================
    //设置坦克的基本属性
    public MyPanel() {
        //初始化我方坦克
        mytank = new MyTank(100,800);
        mytank.setSpeed(7);
        //初始化敌人坦克
        for (int num = 0; num < EnemyTanksNum; num++) {
            EnemyTank enemyTank = new EnemyTank(100*(num+1), 0,mytank);
            enemyTank.setDirect(2);//初始化敌人坦克向下
            //启动坦克线程
            new Thread(enemyTank).start();
            //初始化敌方坦克的一个shot
//            Shot shot = new Shot(enemyTank.getX()+18,enemyTank.getY()+55,enemyTank.getDirect());
//            while (enemyTank.shots.size()<2) {
//                Shot shot = enemyTank.FireAumo(enemyTank.getDirect());
//                enemyTank.shots.add(shot);
//                //初始化了shot后要启动shot线程
//                new Thread(shot).start();
//            }
            //加入敌方坦克
            enemyTanks.add(enemyTank);
        }
        //初始化图片对象
        image1 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/boom1.png"));
        image2 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/boom2.png"));
        image3 = Toolkit.getDefaultToolkit().getImage(MyPanel.class.getResource("/boom3.png"));


    }
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        //绘制地图
        g.fillRect(0, 0, 1000, 1000);

        //=====================绘制坦克的爆炸效果===============================
        for (int i = 0; i < booms.size(); i++) {
            //取出boom对象
            //sleep一下，解决第一个坦克爆炸不显示的问题
            try {
                Thread.sleep(25);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            Boom boom = booms.get(i);
            if (boom.life > 14) {
                g.drawImage(image1, boom.x, boom.y, 80, 80, this);
            } else if (boom.life > 7) {
                g.drawImage(image2, boom.x, boom.y, 80, 80, this);
            } else {
                g.drawImage(image3, boom.x, boom.y, 80, 80, this);
            }
            //减少boom对象的life
            boom.lifeDown();
            //如果boom life == 0,就从booms中删除
            if (boom.life == 0) {
                booms.remove(boom);
            }


        }
        //=========================绘制我方坦克====================
        //判断我方坦克绘制条件
        if (mytank.isLive){
            drawTank(mytank.getX(), mytank.getY(), g, mytank.getDirect(), 0);
         }
        //发出我方坦克发射的子弹
        //判断是否发出了子弹
        //判断AumoisLive!=False
        for (int i = 0; i < mytank.shots.size(); i++) {
            Shot shot = mytank.shots.get(i);
            if (shot != null && shot.AumoisLive == true) {
                drawAumo(shot, g);
            }else {//若子弹销毁，则将子弹从集合中去除
                mytank.shots.remove(shot);
            }
        }
        //=========================绘制敌方坦克====================
        for (int i = 0; i < enemyTanks.size(); i++) {
            EnemyTank enemyTank = enemyTanks.get(i);
            //判断敌方坦克是否存活，再绘制坦克
                if (enemyTank.isLive == true) {
                    drawTank(enemyTank.getX(), enemyTank.getY(), g, enemyTank.getDirect(), 1);
                    //绘制敌方坦克子弹
                    for (int j = 0; j < enemyTank.shots.size(); j++) {
                        //取出遍历的子弹对象
                        Shot shot = enemyTank.shots.get(j);
                        //绘制子弹与从Vector中删除不存活的子弹
                        //判断子弹是否存活
                        if (shot.AumoisLive) {
                            drawAumo(shot, g);
                        } else {
                            //从Vector中移除不存活的shot
                            enemyTank.shots.remove(shot);
                        }

                    }
                }else {
                    enemyTanks.remove(enemyTank);
                }



        }



    }
    //==============绘制坦克================
    //==============封装方法================

    @Override
    public void keyTyped(KeyEvent e) {

    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_W) {
            mytank.setDirect(0);
//            mytank.moveUp();
            mytank.TankMove(mytank.getDirect(),mytank.getSpeed());
        } else if (e.getKeyCode() == KeyEvent.VK_D) {
            mytank.setDirect(1);
//            mytank.moveRight();
            mytank.TankMove(mytank.getDirect(),mytank.getSpeed());
        } else if (e.getKeyCode() == KeyEvent.VK_S) {
            mytank.setDirect(2);
//            mytank.moveDown();
            mytank.TankMove(mytank.getDirect(),mytank.getSpeed());
        } else if (e.getKeyCode() == KeyEvent.VK_A) {
            mytank.setDirect(3);
//            mytank.moveLeft();
            mytank.TankMove(mytank.getDirect(),mytank.getSpeed());
        }

        //发射子弹触发机制
        //实现子弹未击中目标则不能发射新子弹的机制
        if (e.getKeyCode() == KeyEvent.VK_J) {
            //发射一颗子弹的代码实现
//            if (mytank.shot==null || !mytank.shot.AumoisLive) {
//                mytank.ShotEnermyTanK();
//            }
            //判断我方坦克是否存活
            if (mytank.isLive) {
                mytank.ShotEnermyTanK();
            }
        }
        repaint();
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * @param x 坦克的左上角x坐标
     * @param y 坦克的左上角y坐标
     * @param g 画笔
     * @param direct 坦克方向（上下左右）
     * @param type 坦克类型
     */
    public void drawTank(int x,int y,Graphics g ,int direct,int type){
        //坦克类型
        switch (type){
            case 0://我方坦克
                g.setColor(Color.green);
                break;
            case 1://敌方坦克
                g.setColor(Color.YELLOW);
                break;
        }
        //坦克方向
        switch (direct){
            case 0://向上
                //绘制左履带
                g.fill3DRect(x,y,10,60,false);
                //绘制右履带
                g.fill3DRect(x+30,y,10,60,false);
                //绘制基座
                g.fill3DRect(x+10,y+10,20,40,false);
                //绘制炮塔
                g.fillOval(x+10,y+20,20,20);
                //绘制炮管
                g.fillRect(x+18,y+5,4,20);
                break;

            case 1://向右
                //绘制左履带
                g.fill3DRect(x,y,60,10,false);
                //绘制右履带
                g.fill3DRect(x,y+30,60,10,false);
                //绘制基座
                g.fill3DRect(x+10,y+10,40,20,false);
                //绘制炮塔
                g.fillOval(x+20,y+10,20,20);
                //绘制炮管
                g.fillRect(x+35,y+18,20,4);
                break;

            case 2://向下
                //绘制左履带
                g.fill3DRect(x,y,10,60,false);
                //绘制右履带
                g.fill3DRect(x+30,y,10,60,false);
                //绘制基座
                g.fill3DRect(x+10,y+10,20,40,false);
                //绘制炮塔
                g.fillOval(x+10,y+20,20,20);
                //绘制炮管
                g.fillRect(x+18,y+35,4,20);
                break;

            case 3://向左
                //绘制左履带
                g.fill3DRect(x,y,60,10,false);
                //绘制右履带
                g.fill3DRect(x,y+30,60,10,false);
                //绘制基座
                g.fill3DRect(x+10,y+10,40,20,false);
                //绘制炮塔
                g.fillOval(x+20,y+10,20,20);
                //绘制炮管
                g.fillRect(x+5,y+18,20,4);
            default:
                System.out.println("坦克方向不在0-4");
        }


    }
    public void drawAumo(Shot shot, Graphics g){
        g.setColor(Color.white);
        g.fillOval(shot.x, shot.y,5,5);
    }

    // 实现不断重绘画板,子弹就动起来了
    //====================画板线程===========================
    @Override
    public void run() {
        while (true) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //我方坦克调用hitTank，实现实时判断子弹是否击中敌方坦克
            for (int b = 0; b <mytank.shots.size(); b++) {
                Shot shot = mytank.shots.get(b);
                if (shot != null && shot.AumoisLive) {
                    for (int c = 0; c < enemyTanks.size(); c++) {
                        EnemyTank enemyTank = enemyTanks.get(c);
                        hitTank(shot, enemyTank);
                    }
                }
            }
            //敌方坦克调用hitTank，实现实时判断子弹是否击中我方坦克
            //遍历敌方所有坦克
            for (int i = 0; i < enemyTanks.size(); i++) {
                //取出敌方坦克
                EnemyTank enemyTank = enemyTanks.get(i);
                //遍历取出坦克的所有子弹
                for (int a = 0; a <enemyTank.shots.size(); a++) {
                    //取出子弹
                    Shot shot = enemyTank.shots.get(a);
                    if ( mytank.isLive && shot.AumoisLive) {
                        //调用hitTank方法
                        hitTank(shot, mytank);
                    }
                }
            }
            //设置我方坦克复活机制
            if (!mytank.isLive) {
                //将之前的坦克删去
                mytank = null;
                //创建新mytank对象
                mytank = new MyTank(100,800);
                mytank.setSpeed(7);
                //更新传入enermTank的mytank对象，实现其索敌更新
                for (int i = 0; i < enemyTanks.size(); i++) {
                    EnemyTank enemyTank = enemyTanks.get(i);
                    enemyTank.setMyTank(mytank);
                }
            }
            repaint();
        }
    }

    //定义方法，判断我方子弹打中敌方坦克
    //什么时候调用hitTank？ 随时调用，可以放在线程里（自己创建一个线程或者放在Mypanel已有的线程）
//    public  void  hitTank(Shot shot, EnemyTank enemyTank){
//        //判断子弹击中坦克
//        //子弹进入坦克区域就算击中，坦克按上下、左右，分成两组区域
//        switch (enemyTank.getDirect()){
//            case 0:
//            case 2:
//                if (shot.x > enemyTank.getX() && shot.x < enemyTank.getX()+40 && shot.y > enemyTank.getY() && shot.y < enemyTank.getY()+60){
//                     shot.AumoisLive = false;
//                     enemyTank.isLive = false;
//                     //创建Boom对象，加入到booms中
//                    Boom boom = new Boom(enemyTank.getX()-20, enemyTank.getY()-10);
//                    booms.add(boom);
//                }
//                break;
//            case 1:
//            case 3:
//                if (shot.x > enemyTank.getX() && shot.x < enemyTank.getX()+60 && shot.y > enemyTank.getY() && shot.y < enemyTank.getY()+40){
//                    shot.AumoisLive = false;
//                    enemyTank.isLive = false;
//                    //创建Boom对象，加入到booms中
//                    Boom boom = new Boom(enemyTank.getX()-10, enemyTank.getY()-20);
//                    booms.add(boom);
//                }
//                break;
//        }
//    }
    //重写hitTank方法，Tank共用子弹击中判断方法
    public  void  hitTank(Shot shot, Tank Tank){
        //判断被击中的坦克类型
        if (Tank instanceof EnemyTank){
            EnemyTank enemyTank = (EnemyTank) Tank;
            Hitted(shot, enemyTank);
        }else if (Tank instanceof MyTank){
            MyTank mytank = (MyTank) Tank;
            Hitted(shot, mytank);
        }
    }
    //定义绘制爆炸和击中反馈机制
    public void Hitted(Shot shot,Tank Tank){
        switch (Tank.getDirect()){
            case 0:
            case 2:
                if (shot.x > Tank.getX() && shot.x < Tank.getX()+40 && shot.y > Tank.getY() && shot.y < Tank.getY()+60){
                    shot.AumoisLive = false;
                    Tank.isLive = false;
                    //创建Boom对象，加入到booms中
                    Boom boom = new Boom(Tank.getX()-20, Tank.getY()-10);
                    booms.add(boom);
                }
                break;
            case 1:
            case 3:
                if (shot.x > Tank.getX() && shot.x < Tank.getX()+60 && shot.y > Tank.getY() && shot.y < Tank.getY()+40){
                    shot.AumoisLive = false;
                    Tank.isLive = false;
                    //创建Boom对象，加入到booms中
                    Boom boom = new Boom(Tank.getX()-10, Tank.getY()-20);
                    booms.add(boom);
                }
                break;
        }
    }
}
