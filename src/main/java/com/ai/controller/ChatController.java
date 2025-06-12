package com.ai.controller;

import com.ai.service.ChatService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
public class ChatController {
	
	@Autowired
	ChatService chatService;
	
	@GetMapping("/chat")
	public String model(@RequestParam(value = "message", defaultValue = "Hello") String message) {
		return chatService.chat(message);
	}
	
	@GetMapping("/recommend")
	public String recommendMovie(@RequestParam String type,
	                             @RequestParam String year,
                                 @RequestParam String lang) {
		return chatService.recommendMovie(type, year, lang);
	}
}