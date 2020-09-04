package com.javaniuniu.game_model;


import com.javaniuniu.game_model.cor.BulletTankCollider;
import com.javaniuniu.game_model.cor.Collider;
import com.javaniuniu.game_model.cor.ColliderChain;
import com.javaniuniu.game_model.cor.TankTankCollider;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther: javaniuniu
 * @date: 2020/9/4 7:12 PM
 */
public class GameModel {
    Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);
//    public List<Bullet> bullets = new ArrayList<>();
//    public List<Tank> tanks = new ArrayList<>();
//    public List<Explode> explodes = new ArrayList<>();

    ColliderChain chain = new ColliderChain();


    private List<GameObject> object = new ArrayList<>();

    public GameModel() {
        int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));
        // 初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            add(new Tank(50+i*60,200,Dir.DOWN,Group.BAD,this));
        }
    }

    public void add(GameObject go) {
        this.object.add(go);
    }

    public void remove(GameObject go) {
        this.object.remove(go);
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
//        g.drawString("bullets:" + bullets.size(), 10, 60);
//        g.drawString("tanks:" + tanks.size(), 10, 80);
//        g.drawString("explodes" + explodes.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);

        for (int i = 0; i < object.size(); i++) {
            object.get(i).paint(g);
        }

        for (int i = 0; i < object.size(); i++) {
            for (int j = i+1; j < object.size(); j++) {
                GameObject o1 = object.get(i);
                GameObject o2 = object.get(j);
            
                chain.collide(o1,o2);
            }
        }

//
//        // 碰撞检测
//        for (int i = 0; i < bullets.size(); i++) {
//            for (int j = 0; j < tanks.size(); j++) {
//                bullets.get(i).collideWith(tanks.get(j));
//            }
//
//        }

    }

    public Tank getMyTank() {
        return myTank;

    }
}
