package com.kyljmeeski.vacanciesbot.telegrambot;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Users {

    private final Connection connection;

    public Users(Connection connection) {
        this.connection = connection;
    }

    public boolean add(User user) {
        String sql = "INSERT INTO users(chat_id) VALUES(?)";
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, user.chatId());
            int rowsInserted = preparedStatement.executeUpdate();
            if (rowsInserted > 0) {
                return true;
            }
        } catch (SQLException e) {
            if (e.getSQLState().equals("23505")) {
                System.out.println(user.chatId() + " is already registered");
                return false;
            }
            throw new RuntimeException(e);
        }
        return false;
    }

}
