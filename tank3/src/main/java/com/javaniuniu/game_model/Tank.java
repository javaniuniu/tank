package com.javaniuniu.game_model;


import com.javaniuniu.game_model.strategy.FireStrategy;
import com.javaniuniu.game_model.strategy.FourDirFireStrategy;

import java.awt.*;
import java.lang.reflect.Constructor;
import java.util.Random;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 7:14 PM
 */
// 坦克类
public class Tank extends GameObject {


    private static final int SPEED = Integer.parseInt((String) PropertyMgr.get("tankSpeed"));
    ; // 坦克移动的单位
    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();

    private Random random = new Random();
    public int x, y; // 坦克的在画板的位置
    public int oldX,oldY;// 坦克上一个位置
    public Dir dir = Dir.DOWN; // 坦克默认想想移动

    public Rectangle rect = new Rectangle();

    public Group group;

    private boolean moving = true; // 坦克默认是否移动
    private boolean living = true; // 坦克是否存活

    //    Rectangle rect = new Rectangle();
    FireStrategy fs;


    public Rectangle getRect() {
        return rect;
    }

    public Tank(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        if (group == Group.GOOD) {
            String goodFSName = (String) PropertyMgr.get("goodFS");
            try {
                Constructor con = Class.forName(goodFSName).getDeclaredConstructor();
                con.setAccessible(true);
                fs = (FireStrategy) con.newInstance();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        } else {
            fs = new FourDirFireStrategy();
        }

        GameModel.getInstance().add(this);
    }

    public void fire() {
        fs.fire(this);
//        int bX = this.x + Tank.WIDTH/2 - Bullet.WIDTH/2;
//        int bY = this.y + Tank.HEIGHT/2 - Bullet.HEIGHT/2;
//
//        Dir[] dirs = Dir.values();
//        for(Dir dir : dirs) {
//            new  Bullet(bX, bY, dir, group, gm);
//        }
//
//        if(group == Group.GOOD) new Thread(()->new Audio("audio/tank_fire.wav").play()).start();
//
    }


    public void paint(Graphics g) {
//        Color c = g.getColor();
//        g.setColor(Color.YELLOW);
//        g.fillRect(x, y, 50, 50);
//        g.setColor(c);

        if (!living) GameModel.getInstance().remove(this);

        switch (dir) {
            case LEFT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankL : ResourceMgr.badTankL, x, y, null);
                break;
            case UP:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankU : ResourceMgr.badTankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankR : ResourceMgr.badTankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(this.group == Group.GOOD ? ResourceMgr.goodTankD : ResourceMgr.badTankD, x, y, null);
                break;
            default:
                break;
        }
        move();
    }


    // 边界检测
    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;

    }

    private void move() {
        oldX = x;
        oldY = y;
        if (!moving) return;
        switch (dir) {
            case LEFT:
                x -= SPEED;
                break;
            case UP:
                y -= SPEED;
                break;
            case RIGHT:
                x += SPEED;
                break;
            case DOWN:
                y += SPEED;
                break;
            default:
                break;
        }

        // 随机发子弹
        if (this.group == Group.BAD && random.nextInt(100) > 95) this.fire();

        // 随机移动
        if (this.group == Group.BAD && random.nextInt(100) > 95) {
            randomDir();
        }

        boundsCheck();

        // update rect
        rect.x = this.x;
        rect.y = this.y;

    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }


    public void die() {
        this.living = false;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public boolean isMoving() {
        return moving;
    }

    public void setMoving(boolean moving) {
        this.moving = moving;
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public void back() {
        this.x = this.oldX;
        this.y = this.oldY;
    }
}
