package com.kyljmeeski.vacanciesbot.telegrambot;

import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:postgresql://localhost:5432/vacancies-bot";
        String user = "postgres";
        String password = "postgres";
        try {
            Connection connection = DriverManager.getConnection(url, user, password);
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            botsApi.registerBot(new VacanciesBot(connection));
        } catch (TelegramApiException | SQLException e) {
            e.printStackTrace();
        }
    }

}
