package com.javaniuniu.game_model.strategy;

import com.javaniuniu.game_model.Tank;

import java.io.Serializable;

/**
 * @auther: javaniuniu
 * @date: 2020/9/4 9:38 AM
 */
// 开火策略
public interface FireStrategy extends Serializable {
    void fire(Tank tank);
}
