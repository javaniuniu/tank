package com.javaniuniu.tank;

import java.awt.*;
import java.util.Random;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 7:14 PM
 */
// 坦克类
public class Tank {
    private int x, y; // 坦克的在画板的位置
    private Dir dir = Dir.DOWN; // 坦克默认想想移动
    private static final int SPEED = 1; // 坦克移动的单位
    public static final int WIDTH = ResourceMgr.tankD.getWidth();
    public static final int HEIGHT = ResourceMgr.tankD.getHeight();

    private TankFrame tf = null;
    private boolean moving = true; // 坦克默认是否移动
    private boolean living = true; // 坦克是否存活

    private Random random = new Random();
    private Group group = Group.BAD;


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Tank(int x, int y, Dir dir, Group group, TankFrame tf) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.tf = tf;
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

    public void paint(Graphics g) {
//        Color c = g.getColor();
//        g.setColor(Color.YELLOW);
//        g.fillRect(x, y, 50, 50);
//        g.setColor(c);

        if (!living) tf.tanks.remove(this);

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.tankL, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.tankU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.tankR, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.tankD, x, y, null);
                break;
            default:
                break;
        }

        move();
    }


    private void move() {
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

        if (random.nextInt(10)>8) this.fire();
    }

    public void fire() {
        int bx = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        tf.bullets.add(new Bullet(bx, by, this.dir,this.group, this.tf));

    }

    public void die() {
        this.living = false;
    }
}
