package com.javaniuniu.design_patterns.abstractfactory;

import java.awt.Graphics;
import java.awt.Rectangle;

import com.javaniuniu.design_patterns.Group;

public abstract class BaseTank {
	public Group group = Group.BAD;
	public Rectangle rect = new Rectangle();
	
	public abstract void paint(Graphics g);

	public Group getGroup() {
		return this.group;
	}

	public abstract void die();

	public abstract int getX();

	public abstract int getY();
}
