package com.javaniuniu.design_patterns.abstractfactory;

import com.javaniuniu.design_patterns.Bullet;
import com.javaniuniu.design_patterns.Dir;
import com.javaniuniu.design_patterns.Explode;
import com.javaniuniu.design_patterns.Group;
import com.javaniuniu.design_patterns.Tank;
import com.javaniuniu.design_patterns.TankFrame;

public class DefaultFactory extends GameFactory {

	@Override
	public BaseTank createTank(int x, int y, Dir dir, Group group, TankFrame tf) {
		return new Tank(x, y, dir, group, tf);
	}

	@Override
	public BaseExplode createExplode(int x, int y, TankFrame tf) {
		return new Explode(x, y, tf);
	}

	@Override
	public BaseBullet createBullet(int x, int y, Dir dir, Group group, TankFrame tf) {
		return new Bullet(x, y, dir, group, tf);
	}

}
