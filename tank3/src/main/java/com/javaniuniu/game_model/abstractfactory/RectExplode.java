package com.javaniuniu.game_model.abstractfactory;

import com.javaniuniu.game_model.Audio;
import com.javaniuniu.game_model.GameModel;
import com.javaniuniu.game_model.ResourceMgr;
import com.javaniuniu.game_model.TankFrame;
import com.sun.org.glassfish.gmbal.GmbalException;

import java.awt.*;

public class RectExplode extends BaseExplode {

	public static int WIDTH = ResourceMgr.explodes[0].getWidth();
	public static int HEIGHT = ResourceMgr.explodes[0].getHeight();
	
	private int x, y;
	
	//private boolean living = true;
	GameModel gm = null;
	
	private int step = 0;
	
	public RectExplode(int x, int y, GameModel gm) {
		this.x = x;
		this.y = y;
		this.gm = gm;
		
		new Thread(()->new Audio("audio/explode.wav").play()).start();
	}
	
	
	@Override
	public void paint(Graphics g) {
		
		//g.drawImage(ResourceMgr.explodes[step++], x, y, null);
		Color c = g.getColor();
		g.setColor(Color.RED);
		g.fillRect(x, y, 10*step, 10*step);
		step++;
		
		if(step >= 15)
			gm.explodes.remove(this);
		
		g.setColor(c);
		
		
	}

}
