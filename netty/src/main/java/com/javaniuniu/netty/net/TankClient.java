package com.javaniuniu.netty.net;

import com.javaniuniu.netty.Dir;
import com.javaniuniu.netty.Group;
import com.javaniuniu.netty.Tank;
import com.javaniuniu.netty.TankFrame;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.ReferenceCountUtil;

import java.util.UUID;


/**
 * @auther: javaniuniu
 * @date: 2020/9/7 10:14 AM
 * Netty默认多线程
 */
public class TankClient {
    public static final TankClient INSTANCE = new TankClient();
    private Channel channel = null;
    private TankClient(){}

    public void connect() {
        //事件处理线程池
        EventLoopGroup group = new NioEventLoopGroup(1);//1表示一个线程
        Bootstrap b = new Bootstrap();
        try {
            ChannelFuture f = b.group(group)
                    .channel(NioSocketChannel.class)//线程类型
                    .handler(new ClientChannelInitializer())
                    .connect("localhost", 8888);
            f.addListener((ChannelFutureListener) future -> {
                if (!future.isSuccess()) {
                    System.out.println("not connect...");
                } else {
                    System.out.println("connect success!");
                    //channel initial
                    channel = future.channel();

                }
            });
            f.sync();

            //ClientFrame.INSTANCE.updateText("client started!!");
            f.channel().closeFuture().sync(); // close() -> ChannelFuture closeFuture()会一直阻塞
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            group.shutdownGracefully();//关闭客户端
        }
    }

    public void send(String msg) {
        ByteBuf buf = Unpooled.copiedBuffer(msg.getBytes());
        channel.writeAndFlush(buf);
    }

    public static void main(String[] args) {
        TankClient c = new TankClient();
        c.connect();
    }


    public void closeConnect() {
        this.send("_bye_");
    }
}

class ClientChannelInitializer extends ChannelInitializer<SocketChannel> {
    @Override
    protected void initChannel(SocketChannel ch) throws Exception {
        ch.pipeline()
                .addLast(new TankJoinMsgEncoder()) // 现将编码的hander 做编码处理
                .addLast(new TankJoinMsgDecoder()) // 两个codec都要加，服务器会将数据传过来
                .addLast(new ClientChildHandler()); // 在做相应的业务处理
    }
}
// codec 在编解码的时候都是传递TankJoinMsg 即可用  SimpleChannelInboundHandler<TankJoinMsg> + 范型的模式，直接使用
class ClientChildHandler extends SimpleChannelInboundHandler<TankJoinMsg> {

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, TankJoinMsg tankJoinMsg) throws Exception {

        if(tankJoinMsg.id.equals(TankFrame.INSTANCE.getMainTank().getId()) ||  TankFrame.INSTANCE.findTankByUUID(tankJoinMsg.id)!=null) return ;
        System.out.println(tankJoinMsg);
        Tank t = new Tank(tankJoinMsg);//这里new了一个新的坦克，所以会重新初始化一个坦克，
        TankFrame.INSTANCE.addTank(t);
        channelHandlerContext.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
//
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        ctx.writeAndFlush(new TankJoinMsg(TankFrame.INSTANCE.getMainTank()));
    }
}