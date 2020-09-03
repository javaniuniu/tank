package com.javaniuniu.tank;

import java.awt.*;
import java.util.HashMap;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 7:43 PM
 */
public class Bullet {
    private int x,y;
    private Dir dir;
    private static final int SPEED = 10;
    public static final int WIDTH = ResourceMgr.bulletD.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletD.getHeight();

    private boolean living = true; // 子弹是否还在窗口

    private TankFrame tf = null;
    private Group group = Group.BAD;


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Bullet(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
    }

    public void paint(Graphics g) {
        if (!living){
            tf.bullets.remove(this);
        }

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL,x,y,null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU,x,y,null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR,x,y,null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD,x,y,null);
                break;
            default:
                break;
        }
        move();
    }

    private void move() {
        switch (dir){
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

        if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;

    }

    // 碰撞测试
    public void collideWith(Tank tank) {
        if(this.group == tank.getGroup()) return;

        // TODO:用一个rect来记录子弹的位置
        Rectangle rect1 = new Rectangle(this.x,this.y,WIDTH, HEIGHT);
        Rectangle rect2 = new Rectangle(tank.getX(),tank.getY(),Tank.WIDTH,Tank. HEIGHT);
        if (rect1.intersects(rect2)) {
            tank.die();
            this.die();
            tf.explodes.add(new Explode(this.x,this.y,this.tf));
        }
    }

    private void die() {
        this.living = false;
    }
}
