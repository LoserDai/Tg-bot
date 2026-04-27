package com.superbot.io.bot.service;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.SendMessage;
import org.springframework.stereotype.Service;

/**
 * @author feng_dai
 */
@Service
public class TelegramService {

    private final TelegramBot bot;

    public TelegramService(TelegramBot bot) {
        this.bot = bot;
    }

    public void handleUpdate(Update update) {
        if (update.message() != null && update.message().text() != null) {

            String text = update.message().text();
            Long chatId = update.message().chat().id();

            // 简单回复逻辑
            String reply = "你发送了: " + text;

            bot.execute(new SendMessage(chatId, reply));
        }
    }
}