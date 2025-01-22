package com.gametest051;

/**
 * @Author Yanghe
 * 该类实现子弹射击，创建线程
 */
public class Shot implements Runnable{
    int x;
    int y;
    int direct;
    int speed = 15;
    boolean AumoisLive = true;//判断子弹是否存活的属性

    public Shot(int x, int y, int direct) {
        this.x = x;
        this.y = y;
        this.direct = direct;
    }

    @Override
    public void run() {
        while(AumoisLive){

            //线程休眠，降速可视化子弹路径
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            //设置子弹方向
            switch(direct){
                case 0:
                    y -= speed;
                    break;
                case 1:
                    x += speed;
                    break;
                case 2:
                    y += speed;
                    break;
                case 3:
                    x -= speed;
                    break;

            }
            //设置子弹消失触发机制板块/超出边界、打到敌方坦克、障碍物
            if (!(x >= 0 && x<=1000 && y >= 0 && y<=1000/*超过地图边界*/&& AumoisLive/*子弹击中*/)){
                AumoisLive = false;
                break;
            }

        }

    }
}
