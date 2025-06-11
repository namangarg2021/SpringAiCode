package com.ai.service;

import dev.langchain4j.service.SystemMessage;

public interface Friend {
	
	@SystemMessage("You are a good friend of mine. Answer using slang.")
	String chat(String userMessage);
}


