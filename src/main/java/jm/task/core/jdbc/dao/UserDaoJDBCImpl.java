package jm.task.core.jdbc.dao;

import jm.task.core.jdbc.model.User;
import jm.task.core.jdbc.util.Util;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static jm.task.core.jdbc.util.Util.getConnection;

public class UserDaoJDBCImpl implements UserDao {

   private final Connection connection = Util.getConnection();

    public UserDaoJDBCImpl() {

    } 

    public void createUsersTable() {
        String sql = """
                CREATE TABLE IF NOT Exists user
                (
                    Id INT AUTO_INCREMENT PRIMARY KEY,
                    Name VARCHAR(20),
                    LastName VARCHAR(20),
                    Age TINYINT
                );""";

        try (PreparedStatement stat = connection.prepareStatement(sql)) {

            stat.execute();

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void dropUsersTable() {
        String sql = "DROP TABLE IF EXISTS USER";

        try (Connection connection = getConnection();
             Statement stat = connection.createStatement()) {

            stat.executeUpdate(sql);
            System.out.println("Table was dropped");

        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    public void saveUser(String name, String lastName, byte age) {

        String sql = "INSERT INTO User (name, lastName, age) VALUES(?, ?, ?)";

        try (   PreparedStatement preStat = connection.prepareStatement(sql)) {

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
        String sql = "DELETE FROM USER WHERE ID=    ?";

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

        String sql = "SELECT * FROM USER";

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
        String sql = "TRUNCATE TABLE USER";
        try (PreparedStatement preparedStatement = connection.prepareStatement(sql)){
            preparedStatement.execute();

        } catch (SQLException e) {
            System.out.println("ERROR");;
        }
    }
}
