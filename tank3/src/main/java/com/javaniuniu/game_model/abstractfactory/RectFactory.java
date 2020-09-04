package com.javaniuniu.game_model.abstractfactory;

import com.javaniuniu.game_model.Dir;
import com.javaniuniu.game_model.GameModel;
import com.javaniuniu.game_model.Group;
import com.javaniuniu.game_model.TankFrame;

public class RectFactory extends GameFactory {

	@Override
	public BaseTank createTank(int x, int y, Dir dir, Group group, GameModel gm) {
		// TODO Auto-generated method stub
		return new RectTank(x, y, dir, group, gm);
	}

	@Override
	public BaseExplode createExplode(int x, int y, GameModel gm) {
		// TODO Auto-generated method stub
		return new RectExplode(x, y, gm);
	}

	@Override
	public BaseBullet createBullet(int x, int y, Dir dir, Group group, GameModel gm) {
		return new RectBullet(x, y, dir, group, gm);
	}

}
