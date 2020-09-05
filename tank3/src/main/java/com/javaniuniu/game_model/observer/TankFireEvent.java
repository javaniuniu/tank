package com.javaniuniu.game_model.observer;

import com.javaniuniu.game_model.Tank;

/**
 * @auther: javaniuniu
 * @date: 2020/9/5 6:28 PM
 */
public class TankFireEvent {
    Tank tank;
    public Tank getSource(){
        return tank;
    }

    public TankFireEvent(Tank tank) {
        this.tank = tank;
    }
}
