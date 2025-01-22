package com.gametest051;

import javax.swing.*;

/**
 * @Author Yanghe
 */
public class Tankgame051 extends JFrame {
    MyPanel mp = null;
    public static void main(String[] args) {
        Tankgame051 tankgame051 = new Tankgame051();
    }

    public Tankgame051() {
        mp = new MyPanel();
        Thread thread = new Thread(mp);
        thread.start();
        this.add(mp);//加入游戏的绘图
        this.setSize(1000,1000);
        this.setVisible(true);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        //监听mp的键盘事件
        this.addKeyListener(mp);
    }
}
