package com.javaniuniu.game_model.observer;

import com.javaniuniu.game_model.Tank;

/**
 * @auther: javaniuniu
 * @date: 2020/9/5 6:30 PM
 */
// 坦克开会事件，同时事件拿到坦克，并开会
    // 坦克观察者负责连接坦克和开会事件
public class TankFireHandler implements  TankFireObserver {
    @Override
    public void actionOnFire(TankFireEvent e) {
        Tank t = e.getSource();
        t.fire();
    }
}
