package third.feishu.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.ai.chat.ChatResponse;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.document.Document;
import org.springframework.ai.embedding.EmbeddingClient;
import org.springframework.ai.embedding.EmbeddingResponse;
import org.springframework.ai.ollama.OllamaChatClient;
import org.springframework.ai.ollama.OllamaEmbeddingClient;
import org.springframework.ai.ollama.api.OllamaApi;
import org.springframework.ai.ollama.api.OllamaOptions;
import org.springframework.ai.vectorstore.SimpleVectorStore;
import org.springframework.ai.vectorstore.VectorStore;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;

import java.util.List;
import java.util.Map;
import java.util.Scanner;

/**
 * Created by IntelliJ IDEA.
 *
 * @Author : JIA
 */
@RestController
@Slf4j
public class OllamaController {

    private String MODEL = "qwen:14b";

    @Autowired
    private EmbeddingClient embeddingClient;
    @Autowired
    private OllamaChatClient chatClient;


    @GetMapping("/ai/embedding")
    public Map embed(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        EmbeddingResponse embeddingResponse = this.embeddingClient.embedForResponse(List.of(message));
        return Map.of("embedding", embeddingResponse);
    }

    @GetMapping("/ai/generate")
    public Map generate(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return Map.of("generation", chatClient.call(message));
    }

    @GetMapping("/ai/generateStream")
    public Flux<ChatResponse> generateStream(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        Prompt prompt = new Prompt(new UserMessage(message));
        return chatClient.stream(prompt);
    }


    @GetMapping("/ai/name")
    public String name(@RequestParam(value = "message", defaultValue = "Tell me a joke") String message) {
        return chatClient.call("生成5个著名海盗的姓名。");
    }


    //手动色荷治

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public String test(@RequestParam String value) {
        var ollamaApi = new OllamaApi();
        var chatClient = new OllamaChatClient(ollamaApi).withModel(MODEL).withDefaultOptions(OllamaOptions.create().withModel(MODEL).withTemperature(0.9f));
        return chatClient.call(value);
    }


    @RequestMapping(value = "/test2", method = RequestMethod.GET)
    public String test2(@RequestParam String value) {
        var ollamaApi = new OllamaApi();
        var embeddingClient = new OllamaEmbeddingClient(ollamaApi).withDefaultOptions(OllamaOptions.create().withModel(MODEL));
        VectorStore vectorStore = new SimpleVectorStore(embeddingClient);
        vectorStore.add(List.of(new Document("白日依山尽，黄河入海流。欲穷千里目，更上一层楼。"), new Document("青山依旧在，几度夕阳红。白发渔樵江渚上，惯看秋月春风。"), new Document("一片孤城万仞山，羌笛何须怨杨柳。春风不度玉门关。"), new Document("危楼高百尺，手可摘星辰。不敢高声语，恐惊天上人。")));
        List<Document> documents = vectorStore.similaritySearch(value);
        StringBuffer sb = new StringBuffer();
        for (Document doc : documents) {
            sb.append(doc.getContent()).append("\r\n");
        }
        return sb.toString();
    }
}
