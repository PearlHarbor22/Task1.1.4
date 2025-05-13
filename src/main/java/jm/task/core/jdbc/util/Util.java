package jm.task.core.jdbc.util;

import jm.task.core.jdbc.model.User;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;
import org.hibernate.cfg.Environment;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

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
    private static SessionFactory sessionFactory;

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration();
                Properties settings = new Properties();
                settings.put(Environment.DRIVER, "com.mysql.cj.jdbc.Driver");
                settings.put(Environment.URL, DB_URL);
                settings.put(Environment.USER, DB_USER);
                settings.put(Environment.PASS, DB_PASSWORD);
                settings.put(Environment.DIALECT, "org.hibernate.dialect.MySQL5Dialect");

                settings.put(Environment.SHOW_SQL, "true");
                settings.put(Environment.CURRENT_SESSION_CONTEXT_CLASS, "thread");
                settings.put(Environment.HBM2DDL_AUTO, "none");

                configuration.setProperties(settings);
                configuration.addAnnotatedClass(User.class);

                sessionFactory = configuration.buildSessionFactory();
                System.out.println("Hibernate SessionFactory создан нами успешно");
            } catch (Exception e) {
                System.out.println("Ошибка при создании Hibernate SessionFactory" + e.getMessage());
                e.printStackTrace();
            }
        }
        return sessionFactory;
    }
}
