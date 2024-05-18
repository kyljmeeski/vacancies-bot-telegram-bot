package com.kyljmeeski.vacanciesbot.telegrambot;

import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.objects.Update;

public class VacanciesBot extends TelegramLongPollingBot {

    @Override
    public void onUpdateReceived(Update update) {

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
