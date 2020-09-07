package com.javaniuniu.game_model.cor;

import com.javaniuniu.game_model.GameObject;

/**
 * @auther: javaniuniu
 * @date: 2020/9/4 10:12 PM
 */
// 碰撞器
public interface Collider {
    boolean collide(GameObject o1, GameObject o2); // 在责任链中，如果返回true 继续，返回false 停止
}
