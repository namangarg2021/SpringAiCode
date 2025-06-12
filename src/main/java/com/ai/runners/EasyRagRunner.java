package com.ai.runners;

import com.ai.service.rag.EasyRagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class EasyRagRunner implements CommandLineRunner {
	
	@Autowired
	EasyRagService easyRagService;
	
	@Override
	public void run(String... args) throws Exception {
		//Easy RAG
		easyRagService.easyRag();
	}
}
