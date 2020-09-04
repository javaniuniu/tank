package com.javaniuniu.game_model.abstractfactory;

import com.javaniuniu.game_model.Dir;
import com.javaniuniu.game_model.GameModel;
import com.javaniuniu.game_model.Group;

public abstract class GameFactory {
	public abstract BaseTank createTank(int x, int y, Dir dir, Group group, GameModel gm);
	public abstract BaseExplode createExplode(int x, int y, GameModel gm);
	public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, GameModel gm);
}
