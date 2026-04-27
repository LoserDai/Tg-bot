package com.superbot.io.bot.controller;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pengrad.telegrambot.TelegramBot;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class WebhookController {

    @Autowired
    private TelegramBot bot;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @PostMapping("${telegram.bot.webhookPath}")
    public void receiveUpdate(@RequestBody String json) {
        log.info("Received update payload: {}", json);

        try {
            JsonNode node = objectMapper.readTree(json);
            JsonNode messageNode = node.get("message");

            if (messageNode == null) {
                return;
            }

            long userId = messageNode.get("chat").get("id").asLong();
            String firstName = "未知用户";
            if (messageNode.has("from") && messageNode.get("from").has("first_name")) {
                firstName = messageNode.get("from").get("first_name").asText();
            }

            String text = null;
            if (messageNode.has("text") && messageNode.get("text") != null) {
                text = messageNode.get("text").asText();
            }

            log.info("✅ 用户[{}->{}] >>> {}", userId,firstName,text);

        } catch (Exception e) {
            log.error("Error Msg:", e);
        }
    }
}
