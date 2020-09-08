package com.javaniuniu.netty.net;

import com.javaniuniu.netty.Dir;
import com.javaniuniu.netty.Group;
import com.javaniuniu.netty.Tank;
import com.javaniuniu.netty.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankJoinMsg extends Msg {
	public int x, y;
	public Dir dir;
	public boolean moving; // boolean 1个字节
	public Group group;
	public UUID id; // 128位 16个字节

	public TankJoinMsg(Tank t) {
		this.x = t.getX();
		this.y = t.getY();
		this.dir = t.getDir();
		this.group = t.getGroup();
		this.id = t.getId();
		this.moving = t.isMoving();
	}


	public TankJoinMsg(int x, int y, Dir dir, boolean moving, Group group, UUID id) {
		this.x = x;
		this.y = y;
		this.dir = dir;
		this.moving = moving;
		this.group = group;
		this.id = id;
	}

	public TankJoinMsg() {
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
			dos.writeBoolean(moving);
			dos.writeInt(group.ordinal()); // 表示枚举里面最后下标的最后一个
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
			this.moving = dis.readBoolean();
			this.group = Group.values()[dis.readInt()];
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
	public MsgType getMsgType() {
		return MsgType.TankJoin;
	}

	@Override
	public String toString() {
		return "TankJoinMsg{" +
				"x=" + x +
				", y=" + y +
				", dir=" + dir +
				", moving=" + moving +
				", group=" + group +
				", id=" + id +
				'}';
	}

    public void handle() {
		if(this.id.equals(TankFrame.INSTANCE.getMainTank().getId()) ||  TankFrame.INSTANCE.findTankByUUID(this.id)!=null) return ;
		System.out.println(this);
		Tank t = new Tank(this);//这里new了一个新的坦克，所以会重新初始化一个坦克，
		TankFrame.INSTANCE.addTank(t);
		TankClient.INSTANCE.send(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }
}