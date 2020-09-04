package com.javaniuniu.game_model;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 5:39 PM
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();

        // 声音
//        new Thread(()->new Audio("./audio/war1.wav").loop()).start();

        while (true){
            Thread.sleep(50);
            tf.repaint();
        }
    }
}
