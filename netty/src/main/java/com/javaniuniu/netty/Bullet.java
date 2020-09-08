package com.javaniuniu.netty;

import com.javaniuniu.netty.net.BulletNewMsg;
import com.javaniuniu.netty.net.TankClient;
import com.javaniuniu.netty.net.TankDieMsg;

import java.awt.*;
import java.util.UUID;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 7:43 PM
 */
public class Bullet {
    public static final int WIDTH = ResourceMgr.bulletU.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletU.getHeight();

    private int x, y;
    private Dir dir;
    private static final int SPEED = 10;

    private UUID id = UUID.randomUUID();
    private UUID playerId;

    private boolean living = true; // 子弹是否还在窗口

    private TankFrame tf = null;
    private Group group = Group.BAD;

    Rectangle rect = new Rectangle();


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Bullet(UUID playerId,int x, int y, Dir dir, Group group, TankFrame tf) {
        this.playerId = playerId;
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

    public void paint(Graphics g) {
        if (!living) {
            tf.bullets.remove(this);
        }

        switch (dir) {
            case LEFT:
                g.drawImage(ResourceMgr.bulletL, x, y, null);
                break;
            case UP:
                g.drawImage(ResourceMgr.bulletU, x, y, null);
                break;
            case RIGHT:
                g.drawImage(ResourceMgr.bulletR, x, y, null);
                break;
            case DOWN:
                g.drawImage(ResourceMgr.bulletD, x, y, null);
                break;
            default:
                break;
        }
        move();
    }

    private void move() {
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
        // update rect
        rect.x = this.x;
        rect.y = this.y;

        if (x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;

    }

    // 碰撞测试
    public void collideWith(Tank tank) {
        if(this.playerId.equals(tank.getId())) return;
        //System.out.println("bullet rect:" + this.rect);
        //System.out.println("tank rect:" + tank.rect);
        if(this.living && tank.isLiving() && this.rect.intersects(tank.rect)) {
            tank.die();
            this.die();
            TankClient.INSTANCE.send(new TankDieMsg(this.id, tank.getId()));
        }
//        if (this.group == tank.getGroup()) return;
//
//
//        // 用一个rect来记录子弹的位置
////        Rectangle rect1 = new Rectangle(this.x,this.y,WIDTH, HEIGHT);
////        Rectangle rect2 = new Rectangle(tank.getX(),tank.getY(),Tank.WIDTH,Tank. HEIGHT);
//        if (rect.intersects(tank.rect)) {
//            tank.die();
//            this.die();
//            int ex = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
//            int ey = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
//            tf.explodes.add(new Explode(ex, ey, tf));
//        }
    }

    public void die() {
        this.living = false;
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

    public Dir getDir() {
        return dir;
    }

    public boolean isLiving() {
        return living;
    }

    public void setLiving(boolean living) {
        this.living = living;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public UUID getPlayerId() {
        return playerId;
    }

    public void setPlayerId(UUID playerId) {
        this.playerId = playerId;
    }

    @Override
    public String toString() {
        return "Bullet{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", id=" + id +
                ", playerId=" + playerId +
                ", living=" + living +
                ", tf=" + tf +
                ", group=" + group +
                ", rect=" + rect +
                '}';
    }
}
