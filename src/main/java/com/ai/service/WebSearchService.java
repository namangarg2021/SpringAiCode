package com.ai.service;

import dev.langchain4j.data.document.*;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.embedding.Embedding;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.EmbeddingModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.rag.DefaultRetrievalAugmentor;
import dev.langchain4j.rag.RetrievalAugmentor;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.rag.content.retriever.WebSearchContentRetriever;
import dev.langchain4j.rag.query.router.DefaultQueryRouter;
import dev.langchain4j.rag.query.router.QueryRouter;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import dev.langchain4j.web.search.WebSearchEngine;
import dev.langchain4j.web.search.tavily.TavilyWebSearchEngine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.nio.file.Path;
import java.util.List;

import static com.ai.utils.Utils.startConversationWith;
import static com.ai.utils.Utils.toPath;
import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Service
public class WebSearchService {
	
	@Autowired
	static ChatModel model;
	
	public void webSearch() {
		
		Assistant assistant = createAssistant();
		
		startConversationWith(assistant);
	}
	
	private static Assistant createAssistant() {
		
		EmbeddingModel embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();
		
		EmbeddingStore<TextSegment> embeddingStore =
				embed(toPath("documents/miles-of-smiles-terms-of-use.txt"), embeddingModel);
		
		ContentRetriever embeddingStoreContentRetriever = EmbeddingStoreContentRetriever.builder()
				.embeddingStore(embeddingStore)
				.embeddingModel(embeddingModel)
				.maxResults(2)
				.minScore(0.6)
				.build();
		
		WebSearchEngine webSearchEngine = TavilyWebSearchEngine.builder()
				.apiKey(System.getenv("TAVILY_API_KEY"))
				.build();
		
		ContentRetriever webSearchContentRetriever = WebSearchContentRetriever.builder()
				.webSearchEngine(webSearchEngine)
				.maxResults(3)
				.build();
		
		QueryRouter queryRouter = new DefaultQueryRouter(embeddingStoreContentRetriever, webSearchContentRetriever);
		
		RetrievalAugmentor retrievalAugmentor = DefaultRetrievalAugmentor.builder()
				.queryRouter(queryRouter)
				.build();
		
		return AiServices.builder(Assistant.class)
				.chatModel(model)
				.retrievalAugmentor(retrievalAugmentor)
				.chatMemory(MessageWindowChatMemory.withMaxMessages(10))
				.build();
	}
	
	private static EmbeddingStore<TextSegment> embed(Path documentPath, EmbeddingModel embeddingModel) {
		DocumentParser documentParser = new TextDocumentParser();
		Document document = loadDocument(documentPath, documentParser);
		
		DocumentSplitter splitter = DocumentSplitters.recursive(300, 0);
		List<TextSegment> segments = splitter.split(document);
		
		List<Embedding> embeddings = embeddingModel.embedAll(segments).content();
		
		EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
		embeddingStore.addAll(embeddings, segments);
		return embeddingStore;
	}
}
