package com.etbike.server.web.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.appengine.api.xmpp.JID;
import com.google.appengine.api.xmpp.Message;
import com.google.appengine.api.xmpp.MessageBuilder;
import com.google.appengine.api.xmpp.SendResponse;
import com.google.appengine.api.xmpp.XMPPService;
import com.google.appengine.api.xmpp.XMPPServiceFactory;

@Controller
public class PushingController {

	@RequestMapping(value="/request/push")
	public String requestPush(@PathVariable ModelMap map){

		
		//map.put("myBikeList", myBikeList);
		return "jsonView";
		
	}
	
	private void sendMsg(String id, String body) {
		/*
		 * idformat is 2005/doo871128
		 */
		String gid = id.substring(id.indexOf("/") + 1, id.length());

		JID jid = new JID(gid + "@gmail.com");

		Message msg = new MessageBuilder().withRecipientJids(jid)
				.withBody(body).build();
		boolean messageSent = false;

		XMPPService xmpp = XMPPServiceFactory.getXMPPService();
		if (xmpp.getPresence(jid).isAvailable()) {
			SendResponse status = xmpp.sendMessage(msg);

			messageSent = (status.getStatusMap().get(jid) == SendResponse.Status.SUCCESS);
		}
		if (!messageSent) {

		}
	}
}
