package com.javaniuniu.design_patterns.abstractfactory;

import java.awt.Graphics;

import com.javaniuniu.design_patterns.Tank;

public abstract class BaseBullet {
	public abstract void paint(Graphics g);

	public abstract void collideWith(BaseTank tank);
}
