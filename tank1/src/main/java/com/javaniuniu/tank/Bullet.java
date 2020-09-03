package com.javaniuniu.tank;

import java.awt.*;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 7:43 PM
 */
public class Bullet {
    private int x,y;
    private Dir dir;
    private static final int SPEED = 1;
    private static final int WIDTH = 20;
    private static final int HEIGHT = 20;

    public Bullet(int x, int y, Dir dir) {
        this.x = x;
        this.y = y;
        this.dir = dir;
    }

    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.RED);
        g.fillRect(x, y, WIDTH, HEIGHT);
        g.setColor(c);
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
    }
}
