package com.javaniuniu.game_model;

import java.awt.*;

/**
 * @auther: javaniuniu
 * @date: 2020/9/4 9:42 PM
 */
public abstract class GameObject {
    public int x,y;
    public abstract void paint(Graphics g);
    public abstract int getWidth();
    public abstract int getHeight();


}
