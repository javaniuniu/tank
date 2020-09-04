package com.javaniuniu.game_model.cor;

import com.javaniuniu.game_model.Bullet;
import com.javaniuniu.game_model.GameObject;
import com.javaniuniu.game_model.Tank;

/**
 * @auther: javaniuniu
 * @date: 2020/9/4 10:13 PM
 */
public class TankTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Tank && o2 instanceof Tank) {
            Tank t1 = (Tank) o1;
            Tank t2 = (Tank) o2;
            if (t1.getRect().intersects(t2.getRect())) {
                t1.back();
                t2.back();
            }

        }
        return true;
    }

}
