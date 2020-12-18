package com.fmi.fengshuipdfgenerator.rpc;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeoutException;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

public final class RPCCLientSingleton implements AutoCloseable {

	private static final String QUEUE_NAME = "rpc_queue";

	private static RPCCLientSingleton INSTANCE;

	private Connection connection;
	private Channel channel;

	private RPCCLientSingleton() throws IOException, TimeoutException {
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		connection = factory.newConnection();
		channel = connection.createChannel();
	}

	public static RPCCLientSingleton getInstance() throws IOException, TimeoutException {
		if (INSTANCE == null) {
			INSTANCE = new RPCCLientSingleton();
		}

		return INSTANCE;
	}

	public String call(String message) throws IOException, InterruptedException {
		final String corrId = UUID.randomUUID().toString();

		String replyQueueName = channel.queueDeclare().getQueue();
		AMQP.BasicProperties props = new AMQP.BasicProperties.Builder().correlationId(corrId).replyTo(replyQueueName)
				.build();

		channel.basicPublish("", QUEUE_NAME, props, message.getBytes("UTF-8"));

		final BlockingQueue<String> response = new ArrayBlockingQueue<>(1);

		String ctag = channel.basicConsume(replyQueueName, true, (consumerTag, delivery) -> {
			if (delivery.getProperties().getCorrelationId().equals(corrId)) {
				response.offer(new String(delivery.getBody(), "UTF-8"));
			}
		}, consumerTag -> {
		});

		String result = response.take();
		channel.basicCancel(ctag);
		return result;
	}

	@Override
	public void close() throws IOException {
		connection.close();
	}

}