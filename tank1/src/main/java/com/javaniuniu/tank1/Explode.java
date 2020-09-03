package com.javaniuniu.tank1;

import java.awt.*;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 7:43 PM
 */
// 爆炸效果
public class Explode {
    private int x, y;
    public static final int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static final int HEIGHT = ResourceMgr.explodes[0].getHeight();

//    private boolean living = true; // 爆炸图片是否还在窗口

    private TankFrame tf = null;

    private int step = 0; // 爆炸程度


    public Explode(int x, int y, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.tf = tf;
        new Thread(() -> {
            new Audio("./audio/explode.wav").play();
        }).start();

    }

    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.explodes[step++], x, y, null);
        if (step >= ResourceMgr.explodes.length)
//            step = 0;
            tf.explodes.remove(this);
    }


}
