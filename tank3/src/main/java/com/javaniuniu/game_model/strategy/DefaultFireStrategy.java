package com.javaniuniu.game_model.strategy;

import com.javaniuniu.game_model.*;

/**
 * @auther: javaniuniu
 * @date: 2020/9/4 9:39 AM
 */
public class DefaultFireStrategy implements FireStrategy {
//  这里不用单例 会更好
//    static final DefauleFireStrategy defauleFireStrategy = new DefauleFireStrategy();
//
//    private DefauleFireStrategy() {
//    }
//
//    public static DefauleFireStrategy getDefauleFireStrategy() {
//        return defauleFireStrategy;
//    }


    @Override
    public void fire(Tank tank) {
        int bx = tank.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = tank.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;
        new Bullet(bx, by, tank.dir, tank.group, tank.gm);

        if (tank.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
