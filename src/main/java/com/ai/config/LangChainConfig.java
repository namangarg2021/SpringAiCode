package com.ai.config;

import dev.langchain4j.model.chat.StreamingChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.model.openai.OpenAiStreamingChatModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

@Configuration
public class LangChainConfig {
	
	@Autowired
	private ApiConfig config;
	
	@Bean
	public OpenAiChatModel chatLanguageModel() {
		return OpenAiChatModel.builder()
				.baseUrl("https://api.deepinfra.com/v1/openai")
				.apiKey(config.getApiKey())
				.modelName("Qwen/Qwen3-14B")
				.logRequests(true)
				.logResponses(true)
				.timeout(Duration.ofMinutes(1))
				.build();
	}
	
	@Bean
	public StreamingChatModel streamingChatModel() {
		StreamingChatModel model = OpenAiStreamingChatModel.builder()
				.baseUrl("https://api.deepinfra.com/v1/openai")
				.apiKey(config.getApiKey())
				.modelName("Qwen/Qwen3-14B")
				.logRequests(true)
				.logResponses(true)
				.build();
		return model;
	}
}
