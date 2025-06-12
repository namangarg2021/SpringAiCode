package com.ai.runners;

import com.ai.service.rag.NaiveRagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class NaiveRagRunner implements CommandLineRunner {
	
	@Autowired
	NaiveRagService naiveRagService;
	
	@Override
	public void run(String... args) throws Exception {
		//Naive RAG
		naiveRagService.naiveRag();
	}
}
