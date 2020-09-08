package com.javaniuniu.netty.net;

import com.javaniuniu.netty.Bullet;
import com.javaniuniu.netty.Dir;
import com.javaniuniu.netty.Group;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.embedded.EmbeddedChannel;
import org.junit.jupiter.api.Test;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BulletNewMsgCodecTest {

	@Test
	public void testBulletNewEncoder() {
		EmbeddedChannel ch = new EmbeddedChannel();

		UUID playerId = UUID.randomUUID();
		UUID bulletId = UUID.randomUUID();
		BulletNewMsg msg = new BulletNewMsg( playerId,bulletId,5, 10, Dir.DOWN, Group.GOOD);
		ch.pipeline()
				.addLast(new MyMsgEncoder());

		ch.writeOutbound(msg);

		ByteBuf buf = (ByteBuf)ch.readOutbound();
		MsgType msgType = MsgType.values()[buf.readInt()];
		assertEquals(MsgType.BulletNew, msgType);

		int length = buf.readInt();
		assertEquals(48, length);

		int x = buf.readInt();
		int y = buf.readInt();
		Dir dir = Dir.values()[buf.readInt()];
		Group group = Group.values()[buf.readInt()];
		UUID pid = new UUID(buf.readLong(), buf.readLong());
		UUID bid = new UUID(buf.readLong(), buf.readLong());


		assertEquals(5, x);
		assertEquals(10, y);
		assertEquals(Dir.DOWN, dir);
		assertEquals(Group.GOOD, group);
		assertEquals(playerId, pid);
		assertEquals(bulletId, bid);
		
	}
	
	@Test
	public void testBulletNewDecoder() {
		EmbeddedChannel ch = new EmbeddedChannel();

		UUID playerId = UUID.randomUUID();
		UUID bulletId = UUID.randomUUID();
		BulletNewMsg msg = new BulletNewMsg( playerId,bulletId,5, 10, Dir.DOWN, Group.GOOD);
		ch.pipeline()
				.addLast(new MyMsgDecoder());

		ByteBuf buf = Unpooled.buffer();
		buf.writeInt(MsgType.BulletNew.ordinal());
		byte[] bytes = msg.toBytes();
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);

		ch.writeInbound(buf.duplicate());

		BulletNewMsg msgR = (BulletNewMsg)ch.readInbound();


		assertEquals(5, msgR.getX());
		assertEquals(10, msgR.getY());
		assertEquals(Dir.DOWN, msgR.getDir());
		assertEquals(Group.GOOD, msgR.getGroup());
		assertEquals(playerId,msgR.getPlayerID());
		assertEquals(bulletId,msgR.getId());
		
	}

}
