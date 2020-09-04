package com.javaniuniu.game_model.cor;

import com.javaniuniu.game_model.GameObject;

import java.util.LinkedList;
import java.util.List;

/**
 * @auther: javaniuniu
 * @date: 2020/9/4 11:01 PM
 */
// 为什么用链表
    // 我不需要像数组一样，拿小标的值的访问
    // 我不需要固定空间加一个新的，我只需要在尾巴把加一个
    // 数组固定长度，如果加满了后，会在复制，并分配两倍的空间
public class ColliderChain implements Collider {
    private List<Collider> colliders = new LinkedList<>();

    public ColliderChain() {
        add(new BulletTankCollider());
        add(new TankTankCollider());
    }

    public void add(Collider c) {
        colliders.add(c);
    }

    public boolean collide(GameObject o1, GameObject o2) {
        for (int i = 0; i < colliders.size(); i++) {
            if (!colliders.get(i).collide(o1,o2)) {
                return false;
            }
        }
        return true;
    }
}
