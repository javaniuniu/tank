package com.javaniuniu.game_model;

import com.javaniuniu.game_model.abstractfactory.BaseBullet;
import com.javaniuniu.game_model.abstractfactory.BaseTank;

import java.awt.*;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 7:43 PM
 */
public class Bullet extends BaseBullet {
    private int x,y;
    private Dir dir;
    private static final int SPEED = Integer.parseInt((String)PropertyMgr.get("bulletSpeed"));
    public static final int WIDTH = ResourceMgr.bulletU.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletU.getHeight();

    private boolean living = true; // 子弹是否还在窗口

    private GameModel gm = null;
    private Group group = Group.BAD;

    Rectangle rect = new Rectangle();


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Bullet(int x, int y, Dir dir, Group group, GameModel gm) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;
        this.gm = gm;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        gm.bullets.add(this);

    }

    public void paint(Graphics g) {
        if (!living){
            gm.bullets.remove(this);
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

//    @Override
//    public void collideWith(BaseTank tank) {
//
//    }

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

        // update rect
        rect.x = this.x;
        rect.y = this.y;

        if(x < 0 || y < 0 || x > TankFrame.GAME_WIDTH || y > TankFrame.GAME_HEIGHT) living = false;

    }

    // 碰撞测试
    public void collideWith(BaseTank tank) {
        if(this.group == tank.getGroup()) return;


        // 用一个rect来记录子弹的位置
//        Rectangle rect1 = new Rectangle(this.x,this.y,WIDTH, HEIGHT);
//        Rectangle rect2 = new Rectangle(tank.getX(),tank.getY(),Tank.WIDTH,Tank. HEIGHT);
        if (rect.intersects(tank.rect)){
            tank.die();
            this.die();
            int ex = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
            int ey = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
            gm.explodes.add(gm.gf.createExplode(ex,ey,gm));
        }
    }

    private void die() {
        this.living = false;
    }
}
