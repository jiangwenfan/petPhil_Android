package com.example.petphil.rabbitMQHandle;

import com.rabbitmq.client.*;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;

import java.io.IOException;
import java.util.concurrent.TimeoutException;

public class RabbitMQHandle {

    private String queueName; //队列名

    public RabbitMQHandle(String queueName){
        /*
        * 这个构造函数用来进行连接rabbitmq
        * */
        this.queueName = queueName;
    }

    public String getMessg(){
        /*
        * 这个方法用来获取该用户id的消息
        * */
        ConnectionFactory connectionFactory =  new ConnectionFactory();
        Connection connection = null;
        Channel channel = null;
        try {
            connection = connectionFactory.newConnection();
            channel = connection.createChannel();
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("连接rabbitmq失败");
            return "error";
        }

        try {
            channel.queueDeclare(this.queueName,true,false,false,null);

            Consumer consume = new DefaultConsumer(channel){
                @Override
                public void handleDelivery(String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body) throws IOException {
                    //接收消息
                    String msg = new String(body);
                    //将消发送到主线程，用来更新界面

                }
            };
            channel.basicConsume(this.queueName,true,consume);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("队列创建失败");
            return "error";
        }


        return "error";
    }

}
