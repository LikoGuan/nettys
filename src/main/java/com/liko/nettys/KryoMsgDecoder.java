package com.liko.nettys;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Input;
import com.liko.nettys.message.RequestMsg;
import com.liko.nettys.message.ResponseMsg;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * Created by likoguan on 7/12/18.
 */
public class KryoMsgDecoder extends ByteToMessageDecoder {
    private static final int HEAD_LENGTH = 4;

    private Kryo kryo = new Kryo();

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < HEAD_LENGTH) {
            return;
        }

        in.markReaderIndex();

        int bodyLength = in.readInt();
        if (bodyLength < 0) {
            ctx.close();
            return;
        }

        if (in.readableBytes() < bodyLength) {
            in.resetReaderIndex();
            return;
        }

        byte[] body = new byte[bodyLength];
        in.readBytes(body);
        Object object = convertToObject(body, RequestMsg.class);
        out.add(object);
    }

    private Object convertToObject(byte[] body, Class clazz) {
        ByteArrayInputStream byteArrayInputStream = null;
        Input input = null;
        try {
            byteArrayInputStream = new ByteArrayInputStream(body);
            input = new Input(byteArrayInputStream);
            return kryo.readObject(input, clazz);
        } catch (KryoException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (input != null)
                    input.close();
                if (byteArrayInputStream != null)
                    byteArrayInputStream.close();
            } catch (KryoException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
