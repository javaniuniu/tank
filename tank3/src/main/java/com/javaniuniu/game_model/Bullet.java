package com.javaniuniu.game_model;


import java.awt.*;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 7:43 PM
 */
public class Bullet extends GameObject {
    private Dir dir;
    private static final int SPEED = Integer.parseInt((String)PropertyMgr.get("bulletSpeed"));
    public static final int WIDTH = ResourceMgr.bulletU.getWidth();
    public static final int HEIGHT = ResourceMgr.bulletU.getHeight();

    private boolean living = true; // 子弹是否还在窗口

    public Group group = Group.BAD;

    public Rectangle rect = new Rectangle();


    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }

    public Bullet(int x, int y, Dir dir, Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group = group;

        rect.x = this.x;
        rect.y = this.y;
        rect.width = WIDTH;
        rect.height = HEIGHT;

        GameModel.getInstance().add(this);

    }

    public void paint(Graphics g) {
        if (!living){
            GameModel.getInstance().remove(this);
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

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
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

//    // 碰撞测试
//    public boolean collideWith(Tank tank) {
//        if(this.group == tank.getGroup()) return false;
//
//
//        // 用一个rect来记录子弹的位置
////        Rectangle rect1 = new Rectangle(this.x,this.y,WIDTH, HEIGHT);
////        Rectangle rect2 = new Rectangle(tank.getX(),tank.getY(),Tank.WIDTH,Tank. HEIGHT);
//        if (rect.intersects(tank.rect)){
//            tank.die();
//            this.die();
//            int ex = tank.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
//            int ey = tank.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
//            gm.add(new Explode(ex,ey,gm));
//            return true;
//        }else
//            return false;
//    }

    public void die() {
        this.living = false;
    }
}
