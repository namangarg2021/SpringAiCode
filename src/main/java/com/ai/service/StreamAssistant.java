package com.ai.service;

import dev.langchain4j.service.TokenStream;

public interface StreamAssistant {
	
	TokenStream chat(String message);
}
