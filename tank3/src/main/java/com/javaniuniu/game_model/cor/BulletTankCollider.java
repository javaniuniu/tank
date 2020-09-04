package com.javaniuniu.game_model.cor;

import com.javaniuniu.game_model.Bullet;
import com.javaniuniu.game_model.Explode;
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
            if(b.group == t.getGroup()) return false;

            if (b.rect.intersects(t.rect)){
                b.die();
                t.die();
                int ex = t.getX() + Tank.WIDTH / 2 - Explode.WIDTH / 2;
                int ey = t.getY() + Tank.HEIGHT / 2 - Explode.HEIGHT / 2;
                new Explode(ex,ey);
                return true;
            }else
                return false;

        } else if (o2 instanceof Bullet && o1 instanceof Tank) {
            return collide(o2, o1);
        }
        return true;
    }

}
