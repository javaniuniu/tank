package com.javaniuniu.tank;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;

/**
 * @auther: javaniuniu
 * @date: 2020/9/3 9:32 PM
 */
// 文件内容
public class ResourceMgr {
    public static BufferedImage tankL,tankR,tankU,tankD;
    static {
        try {
            tankL = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("./images/tankL.gif"));
            tankR = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("./images/tankR.gif"));
            tankU = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("./images/tankU.gif"));
            tankD = ImageIO.read(ResourceMgr.class.getClassLoader().getResourceAsStream("./images/tankD.gif"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
