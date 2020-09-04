package com.javaniuniu.design_patterns;

/**
 * @auther: javaniuniu
 * @date: 2020/9/4 9:39 AM
 */
public class FourDirFireStrategy implements FireStrategy {
//    //  这里不用单例 会更好
//    static final FourDirFireStrategy fourDirFireStrategy = new FourDirFireStrategy();
//
//    private FourDirFireStrategy() {
//    }
//
//    public static FourDirFireStrategy getFourDirFireStrategy() {
//        return fourDirFireStrategy;
//    }


    @Override
    public void fire(Tank tank) {
        int bx = tank.x + Tank.WIDTH / 2 - Bullet.WIDTH / 2;
        int by = tank.y + Tank.HEIGHT / 2 - Bullet.HEIGHT / 2;

        Dir[] dirs = Dir.values();
        for (Dir dir :dirs)
            tank.tf.gf.createBullet(bx, by, dir, tank.group, tank.tf);


        if (tank.group == Group.GOOD) new Thread(() -> new Audio("audio/tank_fire.wav").play()).start();
    }
}
