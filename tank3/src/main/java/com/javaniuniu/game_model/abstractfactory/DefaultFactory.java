package com.javaniuniu.game_model.abstractfactory;

import com.javaniuniu.game_model.*;

public class DefaultFactory extends GameFactory {

	@Override
	public BaseTank createTank(int x, int y, Dir dir, Group group, GameModel gm) {
		return new Tank(x, y, dir, group, gm);
	}

	@Override
	public BaseExplode createExplode(int x, int y, GameModel gm) {
		return new Explode(x, y, gm);
	}

	@Override
	public BaseBullet createBullet(int x, int y, Dir dir, Group group, GameModel gm) {
		return new Bullet(x, y, dir, group, gm);
	}

}
