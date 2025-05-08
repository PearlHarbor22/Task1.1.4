package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl implements UserDao {
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("""
                    CREATE TABLE IF NOT EXISTS users (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        name VARCHAR(50),
                        last_name VARCHAR(50),
                        age TINYINT
                    )""");
        } catch (SQLException e) {
            System.err.println("Ошибка создания таблицы: " + e.getMessage());
        }
    }

    public void dropUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("DROP TABLE IF EXISTS users");
        } catch (SQLException e) {
            System.err.println("Ошибка в удалении таблицы: " + e.getMessage());
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        try (Connection connection = Util.getConnection();
             PreparedStatement pstate = connection.prepareStatement(
                     "INSERT INTO users (name, last_name, age) VALUES (?, ?, ?)")) {
            pstate.setString(1, name);
            pstate.setString(2, lastName);
            pstate.setByte(3, age);
            pstate.executeUpdate();
            System.out.println("Пользователь " + name + " добавлен в базу данных");
        } catch (SQLException e) {
            System.err.println("Ошибка во время сохранения пользователя: " + e.getMessage());
        }
    }

    public void removeUserById(long id) {
        try (Connection connection = Util.getConnection();
             PreparedStatement ps = connection.prepareStatement(
                     "DELETE FROM users WHERE id = ?")) {
            ps.setLong(1, id);
            ps.executeUpdate();
        } catch (SQLException e) {
            System.err.println("Ошибка при удалении пользователя: " + e.getMessage());
        }
    }

    public List<User> getAllUsers() {
        List<User> users = new ArrayList<>();
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement();
             ResultSet rs = statement.executeQuery("SELECT * FROM users")) {

            while (rs.next()) {
                User user = new User();
                user.setId(rs.getLong("id"));
                user.setName(rs.getString("name"));
                user.setLastName(rs.getString("last_name"));
                user.setAge(rs.getByte("age"));
                users.add(user);
            }

        } catch (SQLException e) {
            System.err.println("Ошибка при вызове пользователей: " + e.getMessage());
        }

        return users;
    }

    public void cleanUsersTable() {
        try (Connection connection = Util.getConnection();
             Statement statement = connection.createStatement()) {
            statement.execute("TRUNCATE TABLE users");
        } catch (SQLException e) {
            System.err.println("Ошибка при очистке: " + e.getMessage());
        }
    }
}
