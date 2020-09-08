package com.javaniuniu.netty.net;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class TankJoinMsgEncoder extends MessageToByteEncoder<TankJoinMsg> {

	@Override
	protected void encode(ChannelHandlerContext ctx, TankJoinMsg msg, ByteBuf buf) throws Exception {
		buf.writeInt(msg.getMsgType().ordinal());
		byte[] bytes = msg.toBytes();
		buf.writeInt(bytes.length);
		buf.writeBytes(bytes);

	}
	

}
