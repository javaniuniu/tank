package com.javaniuniu.tank;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 5:39 PM
 */
public class Main {
    public static void main(String[] args) throws InterruptedException {
        TankFrame tank = new TankFrame();

        while (true){
            Thread.sleep(50);
            tank.repaint();
        }
    }
}
