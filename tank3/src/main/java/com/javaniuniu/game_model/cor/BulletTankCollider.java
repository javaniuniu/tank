package com.javaniuniu.game_model.cor;

import com.javaniuniu.game_model.Bullet;
import com.javaniuniu.game_model.GameObject;
import com.javaniuniu.game_model.Tank;

/**
 * @auther: javaniuniu
 * @date: 2020/9/4 10:13 PM
 */
public class BulletTankCollider implements Collider {
    @Override
    public boolean collide(GameObject o1, GameObject o2) {
        if (o1 instanceof Bullet && o2 instanceof Tank) {
            Bullet b = (Bullet) o1;
            Tank t = (Tank) o2;
            // TODO copy code from method collideWith
            if (b.collideWith(t)) {
                return false;
            }

        } else if (o2 instanceof Bullet && o1 instanceof Tank) {
            return collide(o2, o1);
        }
        return true;
    }

}
