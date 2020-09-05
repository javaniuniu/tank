package com.javaniuniu.game_model.strategy;

import com.javaniuniu.game_model.*;
import com.javaniuniu.game_model.decorator.RectDecorator;
import com.javaniuniu.game_model.decorator.TailDecorator;

/**
 * @auther: javaniuniu
 * @date: 2020/9/4 9:39 AM
 */
public class FourDirFireStrategy implements FireStrategy {
    @Override
    public void fire(Tank tank) {
        int bx = tank.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = tank.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        Dir[] dirs = Dir.values();
        for (Dir dir :dirs)
            GameModel.getInstance().add(
                    new RectDecorator(
                            new TailDecorator(
                                    new Bullet(bx, by, dir, tank.group))));


        if (tank.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
