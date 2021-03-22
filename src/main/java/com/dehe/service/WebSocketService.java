package com.dehe.service;

import com.dehe.model.InMessage;
import com.dehe.model.OutMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;



/**
 * 
 * 功能描述：简单消息模板，用来推送消息
 *
 * @author 佳旺
 * @since 0.0.1
 */
@Service
public class WebSocketService {

	
	@Autowired
	private SimpMessagingTemplate template;



	public void sendChatMessage(InMessage message){
	template.convertAndSend("/chat/single/"+message.getAddressee(),
			new OutMessage(message.getUserid(),message.getInfrom(),message.getContent(),message.getTime(), message.getFileurl()));

	}
	public void sendOnlineUser(String msg) {
		template.convertAndSend("/topic/onlineuser",msg);
	}


	/**
	 * 获取系统实时消息
	 * */
//	public void sendServerInfo() {
//
//		int processors = Runtime.getRuntime().availableProcessors();
//
//		Long freeMem = Runtime.getRuntime().freeMemory();
//
//		Long maxMem = Runtime.getRuntime().maxMemory();
//
//		String message = String.format("服务器可用处理器:%s; 虚拟机空闲内容大小: %s; 最大内存大小: %s", processors,freeMem,maxMem );
//
//		template.convertAndSend("/topic/server_info",new OutMessage(message));
//
//	}

}
