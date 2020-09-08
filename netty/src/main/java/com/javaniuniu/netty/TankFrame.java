package com.javaniuniu.netty;

import com.javaniuniu.netty.net.TankClient;
import com.javaniuniu.netty.net.TankStartMovingMsg;
import com.javaniuniu.netty.net.TankStopMsg;

import java.awt.*;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.*;
import java.util.List;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 5:47 PM
 */
// 画板
public class TankFrame extends Frame {
    public static final TankFrame INSTANCE = new TankFrame();


    static final int GAME_WIDTH = 800;
    static final int GAME_HEIGHT = 600;
    //    Tank myTank = new Tank(200, 400, Dir.DOWN, Group.GOOD, this);
    // 设置坦克初始化位置为随机
    Random r = new Random();
    Tank myTank = new Tank(r.nextInt(GAME_WIDTH), r.nextInt(GAME_HEIGHT), Dir.DOWN, Group.GOOD, this);
    List<Bullet> bullets = new ArrayList<>();
//    List<Tank> tanks = new ArrayList<>();
    Map<UUID,Tank> tanks = new HashMap<>(); // 通过UUID找坦克，只要hash以下就好了，速度很快
    List<Explode> explodes = new ArrayList<>();
//    Explode e = new Explode(100,100,this);


    private TankFrame() {
        setSize(GAME_WIDTH, GAME_HEIGHT);
        setResizable(false);
        setTitle("tank war");


        addKeyListener(new MykeyListener());

        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });
    }

    public Tank findTankByUUID(UUID id) {
        return tanks.get(id);
    }

    @Override
    public void paint(Graphics g) {
        Color c = g.getColor();
        g.setColor(Color.WHITE);
        g.drawString("bullets:" + bullets.size(), 10, 60);
        g.drawString("tanks:" + tanks.size(), 10, 80);
        g.drawString("explodes" + explodes.size(), 10, 100);
        g.setColor(c);

        myTank.paint(g);


        // 子弹
        for (int i = 0; i < bullets.size(); i++) {
            bullets.get(i).paint(g);
        }

        // 坦克
        // java8 stream API
        tanks.values().stream().forEach((e)->e.paint(g));
//        for (int i = 0; i < tanks.size(); i++) {
//            tanks.get(i).paint(g);
//        }

        // 碰撞检测
        //collision detect
        Collection<Tank> values = tanks.values();
        for(int i=0; i<bullets.size(); i++) {
            for(Tank t : values )
                bullets.get(i).collideWith(t);
        }

        // 爆炸
//        for (int i = 0; i < explodes.size(); i++) {
//            explodes.get(i).paint(g);
//        }


//        e.paint(g);

    }

    // 处理双缓冲，解决坦克和子弹闪烁问题
    Image offScreenImage = null;

    @Override
    public void update(Graphics g) {
        if (offScreenImage == null) {
            offScreenImage = this.createImage(GAME_WIDTH, GAME_HEIGHT);
        }
        Graphics gOffScreen = offScreenImage.getGraphics();
        Color c = gOffScreen.getColor();
        gOffScreen.setColor(Color.BLACK);
        gOffScreen.fillRect(0, 0, GAME_WIDTH, GAME_HEIGHT);
        gOffScreen.setColor(c);
        paint(gOffScreen);
        g.drawImage(offScreenImage, 0, 0, null);
    }

    public Tank getMainTank() {
        return this.myTank;
    }

    public void addTank(Tank t) {
        tanks.put(t.getId(), t);
    }

    public void addBullet(Bullet bullet) {
        bullets.add(bullet);
    }

    public Bullet findBulletByUUID(UUID bulletId) {
        for(int i=0; i<bullets.size(); i++) {
            if(bullets.get(i).getId().equals(bulletId))
                return bullets.get(i);
        }

        return null;
    }

    class MykeyListener extends KeyAdapter {
        boolean bL = false;
        boolean bR = false;
        boolean bU = false;
        boolean bD = false;

        @Override
        public void keyPressed(KeyEvent e) {
//            x +=200;
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = true;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = true;
                    break;
                case KeyEvent.VK_UP:
                    bU = true;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = true;
                    break;
                default:
                    break;
            }

            setMainTankDir();
        }

        @Override
        public void keyReleased(KeyEvent e) {
            int key = e.getKeyCode();
            switch (key) {
                case KeyEvent.VK_LEFT:
                    bL = false;
                    break;
                case KeyEvent.VK_RIGHT:
                    bR = false;
                    break;
                case KeyEvent.VK_UP:
                    bU = false;
                    break;
                case KeyEvent.VK_DOWN:
                    bD = false;
                    break;
                case KeyEvent.VK_CONTROL:
                    myTank.fire();
                default:
                    break;
            }
            setMainTankDir();
        }

        private void setMainTankDir() {
            if (!bL && !bR && !bU && !bD) {
                myTank.setMoving(false);
                // 坦克停止
                TankClient.INSTANCE.send(new TankStopMsg(getMainTank()));
            }

            else {
                myTank.setMoving(true);
                if (bL) myTank.setDir(Dir.LEFT);
                if (bR) myTank.setDir(Dir.RIGHT);
                if (bU) myTank.setDir(Dir.UP);
                if (bD) myTank.setDir(Dir.DOWN);
                // 发送坦克信息
                TankClient.INSTANCE.send(new TankStartMovingMsg(getMainTank()));
            }

        }
    }
}
