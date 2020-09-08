package com.javaniuniu.netty.net;


// 信息= 信息头(信息类型) + 信息长度 + 信息题 + 。。。
public abstract class Msg {
	
	public abstract void handle();
	public abstract byte[] toBytes();
	public abstract void parse(byte[] bytes);
	public abstract MsgType getMsgType();
	
}