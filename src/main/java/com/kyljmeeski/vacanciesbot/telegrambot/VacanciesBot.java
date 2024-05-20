package com.kyljmeeski.vacanciesbot.telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

import java.sql.Connection;

public class VacanciesBot extends TelegramLongPollingBot {

    private final Connection connection;

    public VacanciesBot(Connection connection) {
        this.connection = connection;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String chatId = String.valueOf(update.getMessage().getChatId());
            String text = update.getMessage().getText();
            if (text.equals("/start")) {
                User user = new User(chatId);
                Users users = new Users(connection);
                if (users.add(user)) {
                    SendMessage message = new SendMessage(chatId, "Welcome! You will be notified everytime new vacancy appeared.");
                    try {
                        execute(message);
                    } catch (TelegramApiException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    @Override
    public String getBotUsername() {
        return "Vacancies Bot";
    }

    @Override
    public String getBotToken() {
        return System.getenv("TELEGRAM_BOT_TOKEN");
    }

}
