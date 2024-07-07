package com.kyljmeeski.vacanciesbot.telegrambot;

import java.util.function.Consumer;

public class SendTelegramMessageJob implements Consumer<String> {

    private final VacanciesBot bot;

    public SendTelegramMessageJob(VacanciesBot bot) {
        this.bot = bot;
    }

    @Override
    public void accept(String message) {
        bot.sendMessage(new TelegramMessage(message));
    }

    @Override
    public Consumer<String> andThen(Consumer<? super String> after) {
        return Consumer.super.andThen(after);
    }

}
