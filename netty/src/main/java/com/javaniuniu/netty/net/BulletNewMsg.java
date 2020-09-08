package com.javaniuniu.netty.net;

import com.javaniuniu.netty.*;

import java.io.*;
import java.util.UUID;

/**
 * @auther: javaniuniu
 * @date: 2020/9/8 11:35 AM
 */
public class BulletNewMsg extends Msg {
    private int x, y;
    private Dir dir;
    private Group group;
//    private boolean living;
    private UUID id; // 128位 16个字节
    private UUID playerID;




    public BulletNewMsg() {
    }

    public BulletNewMsg(UUID playerID,UUID id,int x, int y, Dir dir,Group group) {
        this.x = x;
        this.y = y;
        this.dir = dir;
        this.group=group;
        this.id = id;
        this.playerID = playerID;
    }
    public BulletNewMsg(Bullet t) {
        this.x = t.getX();
        this.y = t.getY();
        this.dir = t.getDir();
        this.group = t.getGroup();
        this.id = t.getId();
        this.playerID = t.getPlayerId();
    }


    @Override
    public MsgType getMsgType() {
        return MsgType.BulletNew;
    }

    public byte[] toBytes() {
        ByteArrayOutputStream baos = null;
        DataOutputStream dos = null;
        byte[] bytes = null;
        try {
            baos = new ByteArrayOutputStream();
            dos = new DataOutputStream(baos);

            //dos.writeInt(TYPE.ordinal());
            dos.writeInt(x);
            dos.writeInt(y);
            dos.writeInt(dir.ordinal());
            dos.writeInt(group.ordinal());
            dos.writeLong(playerID.getMostSignificantBits()); // uuid 默认是128位 ，分成两个 64位
            dos.writeLong(playerID.getLeastSignificantBits());
            dos.writeLong(id.getMostSignificantBits()); // uuid 默认是128位 ，分成两个 64位
            dos.writeLong(id.getLeastSignificantBits());
            //dos.writeUTF(name);
            dos.flush();
            bytes = baos.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if(baos != null) {
                    baos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            try {
                if(dos != null) {
                    dos.close();
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return bytes;
    }

    @Override
    public void parse(byte[] bytes) {
        DataInputStream dis = new DataInputStream(new ByteArrayInputStream(bytes));
        try {
            this.x = dis.readInt();
            this.y = dis.readInt();
            this.dir = Dir.values()[dis.readInt()];
            this.group = Group.values()[dis.readInt()];
            this.playerID = new UUID(dis.readLong(),dis.readLong());
            this.id = new UUID(dis.readLong(),dis.readLong());
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                dis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }



    @Override
    public String toString() {
        return "BulletNewMsg{" +
                "x=" + x +
                ", y=" + y +
                ", dir=" + dir +
                ", group=" + group +
                ", playerID=" + playerID +
                ", id=" + id +
                '}';
    }

    @Override
    public void handle() {
        if (this.playerID.equals(TankFrame.INSTANCE.getMainTank().getId()))
            return;

        Bullet bullet = new Bullet(this.playerID, x, y, dir, group, TankFrame.INSTANCE);
        bullet.setId(this.id);
        TankFrame.INSTANCE.addBullet(bullet);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Dir getDir() {
        return dir;
    }

    public void setDir(Dir dir) {
        this.dir = dir;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public Group getGroup() {
        return group;
    }

    public void setGroup(Group group) {
        this.group = group;
    }


    public UUID getPlayerID() {
        return playerID;
    }

    public void setPlayerID(UUID playerID) {
        this.playerID = playerID;
    }
}
