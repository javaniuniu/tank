package com.javaniuniu.game_model.observer;

import java.io.Serializable;

public interface TankFireObserver extends Serializable {
	void actionOnFire(TankFireEvent e);
}