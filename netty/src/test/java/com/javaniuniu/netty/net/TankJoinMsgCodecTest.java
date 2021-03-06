package com.javaniuniu.netty.net;

import com.javaniuniu.netty.Dir;
import com.javaniuniu.netty.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;


import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TankJoinMsgCodecTest {

	@Test
	public void testTankJoinMsgEncoder() {
		EmbeddedChannel ch = new EmbeddedChannel();

		UUID id = UUID.randomUUID();
		TankJoinMsg msg = new TankJoinMsg(5, 10, Dir.DOWN, true, Group.BAD, id);
		ch.pipeline()
				.addLast(new MyMsgEncoder());

		ch.writeOutbound(msg);

		ByteBuf buf = (ByteBuf)ch.readOutbound();
		MsgType msgType = MsgType.values()[buf.readInt()];
		assertEquals(MsgType.TankJoin, msgType);

		int length = buf.readInt();
		assertEquals(33, length);

		int x = buf.readInt();
		int y = buf.readInt();
		int dirOrdinal = buf.readInt();
		Dir dir = Dir.values()[dirOrdinal];
		boolean moving = buf.readBoolean();
		int groupOrdinal = buf.readInt();
		Group g = Group.values()[groupOrdinal];
		UUID uuid = new UUID(buf.readLong(), buf.readLong());

		assertEquals(5, x);
		assertEquals(10, y);
		assertEquals(Dir.DOWN, dir);
		assertEquals(true, moving);
		assertEquals(Group.BAD, g);
		assertEquals(id, uuid);
		
	}
	
	@Test
	public void testTankJoinMsgDecoder() {
		EmbeddedChannel ch = new EmbeddedChannel();

		UUID id = UUID.randomUUID();
		TankJoinMsg msg = new TankJoinMsg(5, 10, Dir.DOWN, true, Group.BAD, id);
		ch.pipeline()
				.addLast(new MyMsgDecoder());

		ByteBuf buf = Unpooled.buffer();
		buf.writeInt(MsgType.TankJoin.ordinal());
		byte[] bytes = msg.toBytes();
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);

		ch.writeInbound(buf.duplicate());

		TankJoinMsg msgR = (TankJoinMsg)ch.readInbound();



		assertEquals(5, msgR.getX());
		assertEquals(10, msgR.getY());
		assertEquals(Dir.DOWN, msgR.getDir());
		assertEquals(true, msgR.isMoving());
		assertEquals(Group.BAD, msgR.getGroup());
		assertEquals(id, msgR.getId());
		
	}

}
