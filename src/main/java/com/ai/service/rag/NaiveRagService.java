package com.ai.service.rag;

import com.ai.service.Assistant;
import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.ChatMemory;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

import static com.ai.utils.Utils.startConversationWith;
import static com.ai.utils.Utils.toPath;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Service
public class NaiveRagService {
	
	@Autowired
	ChatModel chatModel;
	
	public void naiveRag() {
		Assistant assistant = createAssistant("documents/miles-of-smiles-terms-of-use.txt");
		
		// Now, let's start the conversation with the assistant. We can ask questions like:
		// - Can I cancel my reservation?
		// - I had an accident, should I pay extra?
		startConversationWith(assistant);
	}
	
	private Assistant createAssistant(String documentPath) {
		DocumentParser documentParser = new TextDocumentParser();
		Document document = loadDocument(toPath(documentPath), documentParser);
		
		DocumentSplitter splitter = DocumentSplitters.recursive(300, 0);
		List<TextSegment> segments = splitter.split(document);
		
		EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();
		List<Embedding> embeddings = embeddingModel.embedAll(segments).content();
		
		EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
		embeddingStore.addAll(embeddings, segments);
		
		ContentRetriever contentRetriever = EmbeddingStoreContentRetriever.builder()
				.embeddingStore(embeddingStore)
				.embeddingModel(embeddingModel)
				.maxResults(2)
				.minScore(0.5)
				.build();
		
		ChatMemory chatMemory = MessageWindowChatMemory.withMaxMessages(10);
		
		return AiServices.builder(Assistant.class)
				.chatModel(chatModel)
				.contentRetriever(contentRetriever)
				.chatMemory(chatMemory)
				.build();
	}
}
