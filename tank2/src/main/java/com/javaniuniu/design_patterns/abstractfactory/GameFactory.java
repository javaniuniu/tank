package com.javaniuniu.design_patterns.abstractfactory;

import com.javaniuniu.design_patterns.Dir;
import com.javaniuniu.design_patterns.Group;
import com.javaniuniu.design_patterns.TankFrame;

public abstract class GameFactory {
	public abstract BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf);
	public abstract BaseExplode createExplode(int x, int y, TankFrame tf);
	public abstract BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf);
}
