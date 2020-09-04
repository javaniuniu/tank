package com.javaniuniu.design_patterns;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 5:39 PM
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = new TankFrame();

        int initTankCount = Integer.parseInt((String)PropertyMgr.get("initTankCount"));

        // 声音
//        new Thread(()->new Audio("./audio/war1.wav").loop()).start();

        // 初始化敌方坦克
        for (int i = 0; i < initTankCount; i++) {
            tf.tanks.add(tf.gf.createTank(50+i*60,200,Dir.DOWN,Group.BAD,tf));
        }


        while (true){
            Thread.sleep(50);
            tf.repaint();
        }
    }
}
