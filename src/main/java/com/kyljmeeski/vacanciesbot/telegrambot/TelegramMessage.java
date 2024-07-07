package com.kyljmeeski.vacanciesbot.telegrambot;

import com.google.gson.Gson;
import com.google.gson.JsonObject;

public class TelegramMessage {

    private final User user;
    private final Vacancy vacancy;

    public TelegramMessage(String source) {
        JsonObject jsonObject = new Gson().fromJson(source, JsonObject.class);
        user = new User(jsonObject.get("chat_id").getAsString());
        vacancy = new Vacancy(jsonObject.get("vacancy").getAsJsonObject());
    }

    public TelegramMessage(User user, Vacancy vacancy) {
        this.user = user;
        this.vacancy = vacancy;
    }

    public String chatId() {
        return user.chatId();
    }

    public String text() {
        StringBuilder text = new StringBuilder();
        vacancy.title().ifPresent(title -> {
            text.append("*").append(title).append("*").append("\r\n");
        });
        vacancy.type().ifPresent(type -> {
            text.append("Тип: ").append(type).append("\r\n");
        });
        vacancy.salary().ifPresent(salary -> {
            text.append("*").append(salary).append("*").append("\r\n");
        });
        return text.toString()
                .replace("-", "\\-")
                .replace("+", "\\+")
                .replace(">", "\\>")
                .replace(".", "\\.")
                .replace("(", "\\(")
                .replace(")", "\\)");
    }

    public String vacancyUrl() {
        return vacancy.link();
    }
}
