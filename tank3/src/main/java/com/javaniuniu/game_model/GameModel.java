package com.javaniuniu.game_model;

import com.javaniuniu.game_model.abstractfactory.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther: javaniuniu
 * @date: 2020/9/4 7:12 PM
 */
public class GameModel {
    Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);
    public java.util.List<BaseBullet> bullets = new ArrayList<>();
    public java.util.List<BaseTank> tanks = new ArrayList<>();
    public List<BaseExplode> explodes = new ArrayList<>();

    public GameFactory gf = new RectFactory();
    public GameModel() {
        int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));
        // 初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            tanks.add(gf.createTank(50+i*60,200,Dir.DOWN,Group.BAD,this));
        }
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("bullets:" + bullets.size(), 10, 60);
        g.drawString("tanks:" + tanks.size(), 10, 80);
        g.drawString("explodes" + explodes.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);


        // 子弹
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        // 坦克
        for (int i = 0; i < tanks.size(); i++) {
            tanks.get(i).paint(g);
        }

        // 碰撞检测
        for (int i = 0; i < bullets.size(); i++) {
            for (int j = 0; j < tanks.size(); j++) {
                bullets.get(i).collideWith(tanks.get(j));
            }

        }

        // 爆炸
        for (int i = 0; i < explodes.size(); i++) {
            explodes.get(i).paint(g);
        }
    }

    public Tank getMyTank() {
        return myTank;

    }
}
