/*
 * The MIT License (MIT)
 *
 * Copyright (c) 2024 Amir Syrgabaev
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */
package com.kyljmeeski.vacanciesbot.telegrambot;

import com.kyljmeeski.rabbitmqwrapper.Consumer;
import com.kyljmeeski.rabbitmqwrapper.PlainConsumer;
import com.kyljmeeski.rabbitmqwrapper.Queues;
import com.kyljmeeski.rabbitmqwrapper.RabbitQueue;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import java.io.IOException;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.TimeoutException;

public class Main {

    public static void main(String[] args) {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setHost("localhost");
        factory.setPort(5672);

        String url = "jdbc:postgresql://localhost:5432/vacancies-bot";
        String user = "postgres";
        String password = "postgres";

        VacanciesBot bot = null;

        try {
            java.sql.Connection connection = DriverManager.getConnection(url, user, password);
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            bot = new VacanciesBot(connection);
            botsApi.registerBot(bot);
        } catch (TelegramApiException | SQLException e) {
            e.printStackTrace();
        }

        try {
            Connection rabbitMQConnection = factory.newConnection();
            Queues queues = new Queues(rabbitMQConnection);
            RabbitQueue queue = queues.declare(
                    "telegram-messages", false, false, false, null
            );
            Consumer consumer = new PlainConsumer(rabbitMQConnection, queue, new SendTelegramMessageJob(bot));
            consumer.startConsuming();
        } catch (IOException | TimeoutException e) {
            throw new RuntimeException(e);
        }
    }

}
