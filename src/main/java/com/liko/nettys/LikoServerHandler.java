package com.liko.nettys;

import com.liko.nettys.message.RequestMsg;
import com.liko.nettys.message.ResponseMsg;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

/**
 * Created by likoguan on 5/12/18.
 */
public class LikoServerHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void channelActive(ChannelHandlerContext ctx) {
//        final ByteBuf msg = ctx.alloc().buffer(20);
//        msg.writeBytes("L".getBytes());
//        ctx.writeAndFlush(msg);
//
//        try {
//            TimeUnit.MILLISECONDS.sleep(5000);
//        } catch (InterruptedException ex) {
//
//        }
//
//        final  ByteBuf msg2 = ctx.alloc().buffer(20);
//        msg2.writeBytes("iko, hello".getBytes());
//        ctx.writeAndFlush(msg2);

//        final ChannelFuture channelFuture = ctx.writeAndFlush(msg);
//        channelFuture.addListener(new ChannelFutureListener() {
//            @Override
//            public void operationComplete(ChannelFuture future) throws Exception {
//                assert future == channelFuture;
//                System.out.println("send time completely");
//                //ctx.close();
//            }
//        });
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) {
//        ByteBuf in = (ByteBuf) msg;
//        try {
//            while (in.isReadable()) {//System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
//                System.out.println((char) in.readByte());
//                System.out.flush();
//            }
//            System.out.println(in.toString(io.netty.util.CharsetUtil.US_ASCII));
//        } finally {
//            //in.release();
//            ReferenceCountUtil.release(msg);
//        }
//        ctx.writeAndFlush(msg);

//        ByteBuf in = (ByteBuf) msg;
//        try {
//            System.out.println(in.toString());
//            ByteBuf out = ctx.alloc().buffer(20);
//            out.writeBytes("收到！".getBytes());
//            ctx.writeAndFlush(out);
//        } finally {
//            in.release();
//        }

        RequestMsg requestMsg = (RequestMsg) msg;
        System.out.println(requestMsg.toString());

        ResponseMsg responseMsg = new ResponseMsg();
        responseMsg.setNo(requestMsg.getNo());
        responseMsg.setName("Liko Guan");
        responseMsg.setGender("male");
        responseMsg.setAge(35);
        responseMsg.setMarried(true);
        ctx.writeAndFlush(responseMsg);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
