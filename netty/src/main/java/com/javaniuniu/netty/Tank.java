package com.javaniuniu.netty;

import com.javaniuniu.netty.net.BulletNewMsg;
import com.javaniuniu.netty.net.TankClient;
import com.javaniuniu.netty.net.TankJoinMsg;

import java.awt.*;
import java.util.Random;
import java.util.UUID;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 7:14 PM
 */
// 坦克类
public class Tank {
    private int x, y; // 坦克的在画板的位置
    private Dir dir = Dir.DOWN; // 坦克默认想想移动
    private static final int SPEED = 5; // 坦克移动的单位
    public static final int WIDTH = ResourceMgr.goodTankU.getWidth();
    public static final int HEIGHT = ResourceMgr.goodTankU.getHeight();

    private TankFrame tf = null;
    private boolean moving = false; // 坦克默认是否移动
    private boolean living = true; // 坦克是否存活

    private Random random = new Random();
    private Group group = Group.BAD;

    Rectangle rect = new Rectangle();

    private UUID id = UUID.randomUUID();



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

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }
    public Tank(TankJoinMsg msg) {
        this.x = msg.getX();
        this.y = msg.getY();
        this.dir = msg.getDir();
        this.moving = msg.isMoving();
        this.group = msg.getGroup();
        this.id = msg.getId();

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;
    }


    public void setId(UUID id) {
        this.id = id;
    }
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.YELLOW);
        g.drawString(id.toString() ,this.x, this.y-10);
        g.setColor(c);

        //draw a rect if dead!
        if(!living) {
            moving = false;
            Color cc = g.getColor();
            g.setColor(Color.WHITE);
            g.drawRect(x, y, WIDTH, HEIGHT);
            g.setColor(cc);
            return;
        }

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

    // 边界检测
    private void boundsCheck() {
        if (this.x < 2) x = 2;
        if (this.y < 28) y = 28;
        if (this.x > TankFrame.GAME_WIDTH - Tank.WIDTH - 2) x = TankFrame.GAME_WIDTH - Tank.WIDTH - 2;
        if (this.y > TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2) y = TankFrame.GAME_HEIGHT - Tank.HEIGHT - 2;

    }

    private void randomDir() {
        this.dir = Dir.values()[random.nextInt(4)];
    }

    public void fire() {
        int bx = this.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = this.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        Bullet b = new Bullet(this.id, bx, by, this.dir, this.group, this.tf);

        tf.bullets.add(b);
        TankClient.INSTANCE.send(new BulletNewMsg(b));

    }

    public void die() {
        this.living = false;
        int eX = this.getX() + Tank.WIDTH/2 - Explode.WIDTH/2;
        int eY = this.getY() + Tank.HEIGHT/2 - Explode.HEIGHT/2;
        TankFrame.INSTANCE.explodes.add(new Explode(eX, eY,tf));
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

    public UUID getId() {
        return id;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    @Override
    public String toString() {
        return "Tank{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", tf=" + tf +
                ", moving=" + moving +
                ", living=" + living +
                ", random=" + random +
                ", group=" + group +
                ", rect=" + rect +
                ", id=" + id +
                '}';
    }
}

