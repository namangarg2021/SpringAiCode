package com.ai.utils;

import com.ai.service.Assistant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.FileSystems;
import java.nio.file.Path;
import java.nio.file.PathMatcher;
import java.nio.file.Paths;
import java.util.Scanner;

@Component
public class Utils {
	
	@Autowired
	Assistant assistant;
	
	public static void startConversationWith(Assistant assistant) {
		Logger log = LoggerFactory.getLogger(Assistant.class);
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				log.info("==================================================");
				log.info("User: ");
				String userQuery = scanner.nextLine();
				log.info("==================================================");
				
				if ("exit".equalsIgnoreCase(userQuery)) {
					break;
				}
				
				String agentAnswer = assistant.chat(userQuery);
				log.info("==================================================");
				log.info("Assistant: " + agentAnswer);
			}
		}
	}
	
	public static PathMatcher glob(String glob) {
		return FileSystems.getDefault().getPathMatcher("glob:" + glob);
	}
	
	public static Path toPath(String relativePath) {
		try {
			URL fileUrl = Utils.class.getClassLoader().getResource(relativePath);
			return Paths.get(fileUrl.toURI());
		} catch (URISyntaxException e) {
			throw new RuntimeException(e);
		}
	}
}