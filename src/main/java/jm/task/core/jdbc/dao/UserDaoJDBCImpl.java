package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class UserDaoJDBCImpl extends Util implements UserDao {
    Connection connection = getConnection();
    public UserDaoJDBCImpl() {

    }

    public void createUsersTable() {
        String sql = "CREATE TABLE IF NOT EXISTS users(" +
                "ID BIGINT NOT NULL AUTO_INCREMENT, NAME VARCHAR(100), " +
                "LASTNAME VARCHAR(100), AGE INT, PRIMARY KEY (ID) )";

        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {

            stat.executeUpdate(sql);
            System.out.println("Table was created!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();

        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS users";

        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {

            stat.executeUpdate(sql);
            System.out.println("Table was dropped");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {
        String sql = "INSERT INTO users (NAME, LASTNAME, AGE) VALUES(?, ?, ?)";

        try (Connection connection = getConnection();
             PreparedStatement preStat = connection.prepareStatement(sql)) {

            //preStat.setLong(1, 1);
            preStat.setString(1, name);
            preStat.setString(2, lastName);
            preStat.setByte(3, age);

            preStat.executeUpdate();
            System.out.println("User was added!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void removeUserById(long id) {
        String sql = "DELETE FROM users WHERE ID=?";

        try (Connection connection = getConnection();
             PreparedStatement preStat = connection.prepareStatement(sql)) {

            preStat.setLong(1, id);

            preStat.executeUpdate();
            System.out.println("User was removed!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public List<User> getAllUsers() {
        List<User> userList = new ArrayList<>();

        String sql = "SELECT ID, NAME, LASTNAME, AGE FROM users";

        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {

            ResultSet resultSet = stat.executeQuery(sql);
            while (resultSet.next()) {
                User user = new User();
                user.setId(resultSet.getLong("ID"));
                user.setName(resultSet.getString("NAME"));
                user.setLastName(resultSet.getString("LASTNAME"));
                user.setAge(resultSet.getByte("AGE"));

                userList.add(user);
            }
            System.out.println("List of users is ready!");
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return userList;
    }

    public void cleanUsersTable() {
        String sql = "DELETE FROM users";
        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {

            stat.executeUpdate(sql);
            System.out.println("Table was cleaned!");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
