package com.dehe.Interceptor;

import com.dehe.controller.v1.V1ChatRoomController;
import com.dehe.utils.RedisClient;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.ChannelInterceptorAdapter;

	public class SocketChannelIntecepter implements ChannelInterceptor {

	private RedisClient redisClient;

	public SocketChannelIntecepter(RedisClient redisClient) {
		this.redisClient = redisClient;
	}

	/**
	 * 在完成发送之后进行调用，不管是否有异常发生，一般用于资源清理
	 */
	@Override
	public void afterSendCompletion(Message<?> message, MessageChannel channel,
									boolean sent, Exception ex) {
		ChannelInterceptor.super.afterSendCompletion(message, channel, sent, ex);
	}


	/**
	 * 在消息被实际发送到频道之前调用
	 */
	@Override
	public Message<?> preSend(Message<?> message, MessageChannel channel) {
		return ChannelInterceptor.super.preSend(message, channel);
	}

	/**
	 * 发送消息调用后立即调用
	 */
	@Override
	public void postSend(Message<?> message, MessageChannel channel,
						 boolean sent) {

		StompHeaderAccessor headerAccessor = StompHeaderAccessor.wrap(message);//消息头访问器

		if (headerAccessor.getCommand() == null ) return ;// 避免非stomp消息类型，例如心跳检测

		String sessionId = headerAccessor.getSessionAttributes().get("sessionId").toString();

		switch (headerAccessor.getCommand()) {
			case CONNECT:
				connect(sessionId);
				break;
			case DISCONNECT:
				disconnect(sessionId);
				break;
			case SUBSCRIBE:

				break;

			case UNSUBSCRIBE:

				break;
			default:
				break;
		}

	}


	//连接成功
	private void connect(String sessionId){
	}


	//断开连接
	private void disconnect(String sessionId){
		System.out.println("删除用户sessionid");
		//用户下线操作
		redisClient.delete(sessionId);
		V1ChatRoomController.onlineUser.remove(sessionId);
	}





}