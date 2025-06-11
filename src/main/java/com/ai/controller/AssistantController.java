package com.ai.controller;

import com.ai.model.Person;
import com.ai.service.Assistant;
import com.ai.service.Friend;
import com.ai.service.PersonExtractor;
import com.ai.service.StreamAssistant;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.TokenStream;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;

import java.io.IOException;
import java.util.Map;

@RestController
class AssistantController {
	
	@Autowired
	Assistant assistant;
	
	@Autowired
	ChatModel chatModel;
	
	@Autowired
	StreamingChatModel streamingChatModel;
	
	@GetMapping("/assistent-chat")
	public String assistentChat(@RequestParam(value = "message", defaultValue = "booking details") String message) {
		return assistant.chat(message);
	}
	
	@GetMapping("/friend-chat")
	public String friendChat(@RequestParam(value = "message", defaultValue = "Hello") String message) {
		Friend friend = AiServices.builder(Friend.class)
				.chatModel(chatModel)
				.systemMessageProvider(chatMemoryId -> "You are a good friend of mine. Answer using slang.")
				.build();
		return friend.chat(message);
	}
	
	@PostMapping("/person-chat")
	public Person personChat(@RequestBody Map<String, String> map) {
		PersonExtractor personExtractor = AiServices.create(PersonExtractor.class, chatModel);
		return personExtractor.extractPersonFrom(map.getOrDefault("text", ""));
	}
	
	@GetMapping("/stream-chat")
	public SseEmitter streamChat() {
		SseEmitter emitter = new SseEmitter(0L);
		
		StreamAssistant assistant = AiServices.create(StreamAssistant.class, streamingChatModel);
		
		TokenStream stream = assistant.chat("Tell me a joke");
		
		stream.onPartialResponse(token -> {
			try {
				emitter.send(SseEmitter.event().data(token));
			} catch (IOException e) {
				emitter.completeWithError(e);
			}
		});
		
		stream.onCompleteResponse(response -> {
			try {
				emitter.send(SseEmitter.event().data("✅ Complete"));
				emitter.complete();
			} catch (IOException e) {
				emitter.completeWithError(e);
			}
		});
		
		stream.onError(error -> {
			try {
				emitter.send(SseEmitter.event().data("❌ Error: " + error.getMessage()));
				emitter.completeWithError(error);
			} catch (IOException e) {
				emitter.completeWithError(e);
			}
		});
		
		stream.start();
		
		return emitter;
	}
	
}