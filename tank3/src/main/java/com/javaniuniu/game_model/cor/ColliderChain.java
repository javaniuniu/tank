package com.javaniuniu.game_model.cor;

import com.javaniuniu.game_model.GameObject;
import com.javaniuniu.game_model.PropertyMgr;
import com.javaniuniu.game_model.ResourceMgr;
import com.javaniuniu.game_model.strategy.FireStrategy;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
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
//        String collidersNames = (String) PropertyMgr.get("colliders");
//        String[] collidersArray = collidersNames.split(",");
//        for (String collider:collidersArray){
//            String[] classNames = collidersNames.split(".");
//            String className = classNames[classNames.length-1];
////            if ()
//
//
//
//
//            Constructor con = null;
//            try {
//                con = Class.forName(collider).getDeclaredConstructor();
//                Collider   fs = (className) con.newInstance();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//
//        }




        add(new BulletTankCollider());
        add(new TankTankCollider());
        add(new BulletWallCollider());
        add(new TankWallCollider());
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
