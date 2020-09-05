package com.javaniuniu.game_model;


import java.awt.*;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 7:43 PM
 */
// 爆炸效果
public class Explode extends GameObject {
    public static final int WIDTH = ResourceMgr.explodes[0].getWidth();
    public static final int HEIGHT = ResourceMgr.explodes[0].getHeight();

//    private boolean living = true; // 爆炸图片是否还在窗口

//    private GameModel gm = null;

    private int step = 0; // 爆炸程度


    public Explode(int x, int y) {
        this.x = x;
        this.y = y;
        new Thread(()->{
            new Audio("./audio/explode.wav").play();
        }).start();

        GameModel.getInstance().add(this);

    }

    public void paint(Graphics g) {
        g.drawImage(ResourceMgr.explodes[step++],x,y,null);
        if (step>=ResourceMgr.explodes.length)
//            step = 0;
            GameModel.getInstance().remove(this);
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }


}
