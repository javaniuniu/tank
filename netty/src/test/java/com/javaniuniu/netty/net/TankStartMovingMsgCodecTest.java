package com.javaniuniu.netty.net;

import com.javaniuniu.netty.Dir;
import com.javaniuniu.netty.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TankStartMovingMsgCodecTest {

	@Test
	public void testTankStartMovingMsgEncoder() {
		EmbeddedChannel ch = new EmbeddedChannel();

		UUID id = UUID.randomUUID();
		TankStartMovingMsg msg = new TankStartMovingMsg( id,5, 10, Dir.DOWN);
		ch.pipeline()
				.addLast(new MyMsgEncoder());

		ch.writeOutbound(msg);

		ByteBuf buf = (ByteBuf)ch.readOutbound();
		MsgType msgType = MsgType.values()[buf.readInt()];
		assertEquals(MsgType.TankJoin, msgType);

		int length = buf.readInt();
		assertEquals(28, length);

		int x = buf.readInt();
		int y = buf.readInt();
		int dirOrdinal = buf.readInt();
		Dir dir = Dir.values()[dirOrdinal];
		UUID uuid = new UUID(buf.readLong(), buf.readLong());

		assertEquals(5, x);
		assertEquals(10, y);
		assertEquals(Dir.DOWN, dir);
		assertEquals(id, uuid);
		
	}
	
	@Test
	public void testTankStartMovingMsgDecoder() {
		EmbeddedChannel ch = new EmbeddedChannel();

		UUID id = UUID.randomUUID();
		TankStartMovingMsg msg = new TankStartMovingMsg(id,5, 10, Dir.DOWN );
		ch.pipeline()
				.addLast(new MyMsgDecoder());

		ByteBuf buf = Unpooled.buffer();
		buf.writeInt(MsgType.TankStartMoving.ordinal());
		byte[] bytes = msg.toBytes();
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);

		ch.writeInbound(buf.duplicate());

		TankStartMovingMsg msgR = (TankStartMovingMsg)ch.readInbound();


		assertEquals(5, msgR.getX());
		assertEquals(10, msgR.getY());
		assertEquals(Dir.DOWN, msgR.getDir());
		assertEquals(id, msgR.getId());
		
	}

}
