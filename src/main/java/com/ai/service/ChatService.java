package com.ai.service;

import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.UserMessage;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.input.PromptTemplate;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;

import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

@Service
public class ChatService {
	
	
	@Autowired
	private ChatModel chatModel;
	private PromptTemplate promptTemplate;
	private final Resource promptResource;
	
	public ChatService(@Value("classpath:prompts/recommend-movie.txt") Resource promptResource) {
		this.promptResource = promptResource;
	}
	
	@PostConstruct
	public void loadTemplate() throws Exception {
		String templateContent = new String(promptResource.getInputStream().readAllBytes(), StandardCharsets.UTF_8);
		this.promptTemplate = PromptTemplate.from(templateContent);
	}
	
	public String recommendMovie(String type, String year, String lang) {
		String promptText = promptTemplate.apply(Map.of(
				"type", type,
				"year", year,
				"lang", lang
		)).text();
		
		AiMessage response = chatModel.chat(List.of(UserMessage.from(promptText))).aiMessage();
		return response.text();
	}
	
	public String chat(String message) {
		return chatModel.chat(message);
	}
}
