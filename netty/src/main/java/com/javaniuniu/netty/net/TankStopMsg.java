package com.javaniuniu.netty.net;

import com.javaniuniu.netty.Dir;
import com.javaniuniu.netty.Tank;
import com.javaniuniu.netty.TankFrame;

import java.io.*;
import java.util.UUID;

public class TankStopMsg extends Msg {
	public int x, y;
	public UUID id; // 128位 16个字节


	public TankStopMsg() {
	}

	public TankStopMsg(UUID id, int x, int y, Dir dir) {
		this.x = x;
		this.y = y;
		this.id = id;
	}
	public TankStopMsg(Tank t) {
		this.x = t.getX();
		this.y = t.getY();
		this.id = t.getId();
	}


	@Override
	public MsgType getMsgType() {
		return MsgType.TankStop;
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
		return "TankJoinMsg{" +
				"x=" + x +
				", y=" + y +
				", id=" + id +
				'}';
	}

	public void handle() {
		if (this.id.equals(TankFrame.INSTANCE.getMainTank().getId()))
			return;

		Tank t = TankFrame.INSTANCE.findTankByUUID(this.id);

		if (t != null) {
			t.setMoving(false);
			t.setX(this.x);
			t.setY(this.y);
		}
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



	public UUID getId() {
		return id;
	}

	public void setId(UUID id) {
		this.id = id;
	}
}