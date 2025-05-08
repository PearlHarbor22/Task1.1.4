package jm.task.core.jdbc.util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Util {
    private static final String DB_URL = "jdbc:mysql://localhost:3306/db_bipki";
    private static final String DB_USER = "root";
    private static final String DB_PASSWORD = "root";

    public static Connection getConnection() {
        try {
            Connection connection = DriverManager.getConnection(DB_URL, DB_USER, DB_PASSWORD);
            if ((connection != null && !connection.isClosed())) {
                System.out.println("Подключение к базе данных успешно");
            }
            return connection;
        } catch (SQLException e) {
            System.err.println("Ошибка подключения к базе данных" + e.getMessage());
        }
    return null;
    }
}
