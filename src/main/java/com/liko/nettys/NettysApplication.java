package com.liko.nettys;

import com.liko.nettys.service.HelloService;
import org.apache.rocketmq.client.consumer.DefaultMQPushConsumer;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyContext;
import org.apache.rocketmq.client.consumer.listener.ConsumeConcurrentlyStatus;
import org.apache.rocketmq.client.consumer.listener.MessageListenerConcurrently;
import org.apache.rocketmq.client.producer.DefaultMQProducer;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.common.consumer.ConsumeFromWhere;
import org.apache.rocketmq.common.message.Message;
import org.apache.rocketmq.common.message.MessageExt;
import org.apache.rocketmq.remoting.common.RemotingHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.nio.ByteBuffer;
import java.nio.channels.*;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Created by likoguan on 5/12/18.
 */
@ComponentScan(basePackages = {"com.liko.nettys.service"})
@SpringBootApplication
public class NettysApplication implements CommandLineRunner {
    @Autowired
    private HelloService helloService;

    public static void main(String[] args) {
        SpringApplication.run(NettysApplication.class, args);
    }

//    private void testProducer(String groupName, String namesrvAddr, String topic) throws Exception {
//        DefaultMQProducer producer = new DefaultMQProducer(groupName);
//        producer.setNamesrvAddr(namesrvAddr);
//        producer.start();
//
//        for (int i=0; i<100; i++) {
//            Message message = new Message(topic, "TagA",
//                    ("Hello RocketMQ " + i).getBytes(RemotingHelper.DEFAULT_CHARSET));
//            SendResult sendResult = producer.send(message);
//            System.out.println(sendResult);
//        }
//
//        producer.shutdown();
//    }
//
//    private void testConsumer(String groupName, String namesrvAddr, String topic) throws Exception {
//        DefaultMQPushConsumer consumer = new DefaultMQPushConsumer(groupName);
//        consumer.setNamesrvAddr(namesrvAddr);
//        consumer.setConsumeFromWhere(ConsumeFromWhere.CONSUME_FROM_FIRST_OFFSET);
//        consumer.subscribe(topic, "*");
//        consumer.registerMessageListener(new MessageListenerConcurrently() {
//            @Override
//            public ConsumeConcurrentlyStatus consumeMessage(List<MessageExt> list, ConsumeConcurrentlyContext consumeConcurrentlyContext) {
//                System.out.println(Thread.currentThread () .getName () + "Receive New Messages:" + list);
//                return ConsumeConcurrentlyStatus.CONSUME_SUCCESS;
//            }
//        });
//        consumer.start();
//
//        TimeUnit.MILLISECONDS.sleep(10000);
//    }

    @Override
    public void run(String... args) throws Exception {
        System.out.println("NettysApplication run");
        helloService.hello();

        new LikoServer(7777).run();


//        testProducer("liko", "127.0.0.1:9876", "mytest");
//        testConsumer("liko", "127.0.0.1:9876", "mytest");


//        System.out.println("Listening for connection on port " + 7777);
//
//        ServerSocketChannel serverChannel = ServerSocketChannel.open();
//        ServerSocket ss = serverChannel.socket();
//        ss.bind(new InetSocketAddress(7777));
//        serverChannel.configureBlocking(false);
//
//        Selector selector = Selector.open();
//        serverChannel.register(selector, SelectionKey.OP_ACCEPT);
//
//
//        while (true) {
//            selector.select();
//
//            for (Iterator<SelectionKey> it = selector.selectedKeys().iterator(); it.hasNext(); ) {
//                SelectionKey key = (SelectionKey) it.next();
//                it.remove();
//
//                try {
//                    if (key.isAcceptable()) {
//                        ServerSocketChannel server = (ServerSocketChannel) key.channel();
//                        SocketChannel client = server.accept();
//                        System.out.println("Accepted connection from " + client);
//                        client.configureBlocking(false);
//                        SelectionKey clientKey = client.register(selector, SelectionKey.OP_READ);
//                        ByteBuffer buffer = ByteBuffer.allocate(100);
//                        clientKey.attach(buffer);
//                    }
//
//                    if (key.isReadable()) {
//                        SocketChannel client = (SocketChannel) key.channel();
////                        ByteBuffer buffer = (ByteBuffer) key.attachment();
////                        client.read(buffer);
//
//                        ByteBuffer buf = ByteBuffer.allocate(2);
//                        client.read(buf);
//                        buf.flip();
//                        byte[] xxx = new byte[2];
//                        buf.get(xxx);
//                        System.out.println(new String(xxx));
//                    }
//
//                    if (key.isWritable()) {
//                        SocketChannel client = (SocketChannel) key.channel();
//                        ByteBuffer buffer = (ByteBuffer) key.attachment();
//                        buffer.flip();
//                        client.write(buffer);
//                        buffer.compact();
//                    }
//                } catch (IOException e) {
//                    key.cancel();
//                    try {
//                        key.channel().close();
//                    } catch (IOException ioe) {
//                    }
//                }
//            }
//        }

    }
}
