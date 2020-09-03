package com.javaniuniu.tank;

import java.awt.*;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 7:14 PM
 */
// 坦克类
public class Tank {
    private int x,y; // 坦克的在画板的位置
    private Dir dir = Dir.DOWN; // 坦克默认想想移动
    private static final int SPEED = 10; // 坦克移动的单位

    private boolean moving = false; // 坦克是否移动



    public Tank(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
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

    public void paint(Graphics g) {
        g.fillRect(x, y, 50, 50);
        move();
    }

    private void move() {
        if (!moving) return;
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
    }
}
