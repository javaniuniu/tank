package com.javaniuniu.netty.net;

import com.javaniuniu.netty.Dir;
import com.javaniuniu.netty.Group;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.UUID;

public class MyMsgDecoder extends ByteToMessageDecoder {

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
		if(in.readableBytes()<8) return; //TCP 拆包解包的问题
		in.markReaderIndex(); // 标记一读位置下标
		MsgType msgType = MsgType.values()[in.readInt()];
		int length = in.readInt(); // 表示 length = length - msgTpye长度字节 - 信息长度这个字节

		if(in.readableBytes()<length){
			in.resetReaderIndex();
			return;
		}
		byte[] bytes = new byte[length];
		in.readBytes(bytes);

		Msg msg = null;

		//reflection
		msg = (Msg)Class.forName("com.javaniuniu.netty.net."+msgType.toString()+"Msg").getDeclaredConstructor().newInstance();
//		switch (msgType){
//			case TankJoin:
//				msg = new TankJoinMsg();
//				msg.parse(bytes);
//				out.add(msg);
//				break;
//			case TankStartMoving:
//				msg = new TankStartMovingMsg();
//				break;
//			case TankStop:
//				msg = new TankStopMsg();
//				break;
//			default:
//				break;
//		}
		msg.parse(bytes);
		out.add(msg);
	}

}