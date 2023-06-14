package com.shaun.chatgptproject.controller;

import com.shaun.chatgptproject.dto.ChatGPTRequest;
import com.shaun.chatgptproject.dto.ChatGPTResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@RestController
@RequestMapping("/bot")
public class CustomBotController {

  @Value("${openai.model}")
  private String model;

  @Value("${openai.api.url}")
  private String apiURL;

  @Autowired
  private RestTemplate template;

  @GetMapping("/chat")
  public String chat(@RequestParam("prompt") String prompt) {
    ChatGPTRequest request = new ChatGPTRequest(model, prompt);
    ChatGPTResponse chatGPTResponse = template.postForObject(apiURL, request,
        ChatGPTResponse.class);

    if (chatGPTResponse == null) {
      throw new AssertionError();
    }
    return chatGPTResponse.getChoices().get(0).getMessage().getContent();
  }
}
