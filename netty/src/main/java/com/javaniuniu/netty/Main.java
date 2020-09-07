package com.javaniuniu.netty;

import com.javaniuniu.netty.net.TankClient;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 5:39 PM
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tf = TankFrame.INSTANCE;
        tf.setVisible(true);


        // 声音
//        new Thread(()->new Audio("./audio/war1.wav").loop()).start();

        // 初始化敌方坦克
//        for (int i = 0; i < 5; i++) {
//            tf.tanks.add(new Tank(50 + i * 60, 200, Dir.DOWN, Group.BAD, tf));
//        }

        new Thread(() -> {
            while (true) { // 需要单独一个线程用来刷新画板
                try {
                    Thread.sleep(50);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                tf.repaint();
            }
        }).start();

        //or you can new a thread to run this
//        TankClient tc = new TankClient();//TankClient 会阻塞
//        tc.connect();

        TankClient.INSTANCE.connect();

    }
}
