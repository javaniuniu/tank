package com.javaniuniu.game_model.decorator;

import com.javaniuniu.game_model.GameObject;

import java.awt.*;

public abstract class GODecorator extends GameObject {

	
	GameObject go;
	
	public GODecorator(GameObject go) {
		this.go = go;
	}

	@Override
	public abstract void paint(Graphics g);

}