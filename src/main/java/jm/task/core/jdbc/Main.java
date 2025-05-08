package jm.task.core.jdbc;

import jm.task.core.jdbc.service.UserService;
import jm.task.core.jdbc.service.UserServiceImpl;
import jm.task.core.jdbc.util.Util;

public class Main {
    public static void main(String[] args) {
        Util.getConnection();
        UserService userService = new UserServiceImpl();
        userService.createUsersTable();
        userService.saveUser("Лёха", "Штырь", (byte) 70);
        userService.saveUser("Витя", "Сиплый", (byte) 12);
        userService.saveUser("Киборг", "Убийца", (byte) 100);
        userService.saveUser("Вова", "Вист", (byte) 34);
        userService.getAllUsers().forEach(System.out::println);
        userService.cleanUsersTable();
        userService.dropUsersTable();
    }
}
