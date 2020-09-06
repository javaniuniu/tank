package com.javaniuniu.game_model;


import com.javaniuniu.game_model.cor.ColliderChain;

import java.awt.*;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

/**
 * @auther: javaniuniu
 * @date: 2020/9/4 7:12 PM
 */
public class GameModel {
    private static final GameModel INSTANCE = new GameModel();

    static {
        INSTANCE.init();
    }

    Tank myTank;

    //    public List<Bullet> bullets = new ArrayList<>();
//    public List<Tank> tanks = new ArrayList<>();
//    public List<Explode> explodes = new ArrayList<>();
    ColliderChain chain = new ColliderChain();


    private List<GameObject> object = new ArrayList<>();

    public static GameModel getInstance() {
        return INSTANCE;
    }

    private GameModel() {
    }

    public void init() {
        myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD);
        int initTankCount = Integer.parseInt((String) PropertyMgr.get("initTankCount"));
        // 初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            new Tank(50 + i * 60, 200, Dir.DOWN, Group.BAD);
        }
        // 初始化墙
        new Wall(150, 150, 200, 50);
        new Wall(550, 150, 200, 50);
        new Wall(300, 300, 50, 200);
        new Wall(550, 300, 50, 200);
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
            for (int j = i + 1; j < object.size(); j++) {
                GameObject o1 = object.get(i);
                GameObject o2 = object.get(j);

                chain.collide(o1, o2);
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


    public void save() {
        File file = new File("/Users/minp/mashibing/tank.data");
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(new FileOutputStream(file));
            oos.writeObject(myTank);
            oos.writeObject(object);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                try {
                    oos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public void load() {
        File file = new File("/Users/minp/mashibing/tank.data");
        ObjectInputStream ois = null;
        try {
            ois = new ObjectInputStream(new FileInputStream(file));
            myTank = (Tank) ois.readObject();
            object = (List) ois.readObject();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (null != null) {
                try {
                    ois.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
