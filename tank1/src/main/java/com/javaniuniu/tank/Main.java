package com.javaniuniu.tank;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 5:39 PM
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();

        // 初始化敌方坦克
        for (int i = 0; i < 5; i++) {
            tf.tanks.add(new Tank(50+i*60,200,Dir.DOWN,Group.BAD,tf));
        }

        while (true){
            Thread.sleep(50);
            tf.repaint();
        }
    }
}
