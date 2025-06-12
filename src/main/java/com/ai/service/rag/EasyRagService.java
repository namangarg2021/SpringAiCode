package com.ai.service.rag;

import com.ai.service.Assistant;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStoreIngestor;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ai.utils.Utils.*;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocuments;

@Service
public class EasyRagService {
	
	@Autowired
	ChatModel chatModel;
	
	public void easyRag(){
		List<Document> documents = loadDocuments(toPath("documents/"), glob("*.txt"));
		Assistant assistant = AiServices.builder(Assistant.class)
				.chatModel(chatModel)
				.chatMemory(MessageWindowChatMemory.withMaxMessages(10))
				.contentRetriever(createContentRetriever(documents))
				.build();
		startConversationWith(assistant);
	}
	
	private static ContentRetriever createContentRetriever(List<Document> documents) {
		InMemoryEmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
		
		EmbeddingStoreIngestor.ingest(documents, embeddingStore);
		
		return EmbeddingStoreContentRetriever.from(embeddingStore);
	}
}
