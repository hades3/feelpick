package yctw.feelpick.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.ai.vertexai.gemini.VertexAiGeminiChatModel;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/api")
@RestController
@RequiredArgsConstructor
public class ChatController {

    private final VertexAiGeminiChatModel vertexAiGeminiChatModel;

    @GetMapping("/chat")
    public String chat(@RequestBody String message) {
        String vertexAiGeminiResponse = vertexAiGeminiChatModel.call(message);
        System.out.println("vertexAiGeminiResponse = " + vertexAiGeminiResponse);
        return vertexAiGeminiResponse;
    }

}
