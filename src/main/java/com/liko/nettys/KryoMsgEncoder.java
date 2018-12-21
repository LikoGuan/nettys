package com.liko.nettys;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.KryoException;
import com.esotericsoftware.kryo.io.Output;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created by likoguan on 7/12/18.
 */
public class KryoMsgEncoder<T> extends MessageToByteEncoder<T> {
    private Kryo kryo = new Kryo();

    public KryoMsgEncoder(Class<? extends T> clazz) {
        super(clazz);
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, T msg, ByteBuf out) throws Exception {
        byte[] body = convertToBytes(msg);
        if (body != null) {
            int bodyLength = body.length;
            if (bodyLength > 0) {
                out.writeInt(body.length);
                out.writeBytes(body);
            }
        }
    }

    private byte[] convertToBytes(T obj) {
        ByteArrayOutputStream byteArrayOutputStream = null;
        Output output = null;
        try {
            byteArrayOutputStream = new ByteArrayOutputStream();
            output = new Output(byteArrayOutputStream);
            kryo.writeObject(output, obj);
            output.flush();
            return byteArrayOutputStream.toByteArray();
        } catch (KryoException ex) {
            ex.printStackTrace();
        } catch (Exception ex) {
            ex.printStackTrace();
        } finally {
            try {
                if (output != null)
                    output.close();
                if (byteArrayOutputStream != null)
                    byteArrayOutputStream.close();
            } catch (KryoException ex) {
                ex.printStackTrace();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
        return null;
    }
}
