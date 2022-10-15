package com.yedam.mohobby.web.chat;

import java.util.HashMap;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.stereotype.Controller;
import com.yedam.mohobby.service.chat.ContentVO;
import com.yedam.mohobby.service.chat.SendNoticeVO;
import com.yedam.mohobby.service.chat.RevNoticeVO;

@Controller
public class MessageController {

	@Autowired
	SimpMessageSendingOperations sendTemplate;

	@MessageMapping("/getSubscribeInfo")
	public void getSubscribeInfo(String RoomNo) {
		sendTemplate.convertAndSend("/topic/room/"+RoomNo,RoomNo);
	}
	@MessageMapping("/send")
	public void send(ContentVO content) {
		System.out.println(content);
		sendTemplate.convertAndSend("/topic/room/" + content.getRoomNo(), content);
	}

	@MessageMapping("/getSubscribeId")
	public void rev(String RoomNo) {
		sendTemplate.convertAndSend("/topic/room/" + RoomNo, RoomNo);
	}
	@MessageMapping("/test234")
	public void test(String memberId)
	{
		for(int i=0;i<100;i++) {
		System.out.println(memberId);}
	sendTemplate.convertAndSend("/queue/"+memberId,memberId);	
	}
	@MessageMapping("/sendNotice")
	public void sendNotice(SendNoticeVO sendNotice) {

		RevNoticeVO revNotice = new RevNoticeVO();
		revNotice.setContent(sendNotice.getContent());
		revNotice.setRoomNo(sendNotice.getRoomNo());
		revNotice.setMsgTime(sendNotice.getMsgTime());
		for (int i = 0; i < sendNotice.getMemberId().size(); i++) {
			sendTemplate.convertAndSend("/queue/" + sendNotice.getMemberId().get(i), revNotice);
		}
	}
	@MessageMapping("/chatNotice")
	public void chatNotice(ContentVO content) {
		sendTemplate.convertAndSend("/"+content.getMemberId(),content);
	}
	
}
