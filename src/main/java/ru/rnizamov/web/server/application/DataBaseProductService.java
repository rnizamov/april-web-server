package ru.rnizamov.web.server.application;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DataBaseProductService implements ProductService {
    private static final Logger logger = LogManager.getLogger(DataBaseProductService.class.getName());
    private final String USER;
    private final String PASSWORD;
    private final String DATABASE_URL;
    private Connection connection;
    private PreparedStatement preparedStatement;

    public DataBaseProductService(String url, String user, String password) throws SQLException {
        this.USER = user;
        this.PASSWORD = password;
        this.DATABASE_URL = url;
        this.connection = getConnection();
        logger.info("Подключение к БД под пользователем " + user);
        logger.info("Url БД: " + url);
    }

    private Connection getConnection() throws SQLException {
        return DriverManager.getConnection(DATABASE_URL, USER, PASSWORD);
    }

    @Override
    public void save(Item item) throws SQLException {
        String title = item.getTitle();
        int price = item.getPrice();
        logger.debug("Сохраняем item: " + item);
        preparedStatement = connection.prepareStatement("INSERT INTO product (title, price) VALUES (?, ?);");
        preparedStatement.setString(1, title);
        preparedStatement.setInt(2, price);
        preparedStatement.executeUpdate();
    }

    @Override
    public void updateItem(Item item) throws SQLException {
        logger.debug("Обновляем item: " + item);
        preparedStatement = connection.prepareStatement("UPDATE product SET title = (?), price = (?) WHERE id = (?)");
        preparedStatement.setString(1, item.getTitle());
        preparedStatement.setInt(2, item.getPrice());
        preparedStatement.setInt(3, item.getId());
        preparedStatement.executeUpdate();
    }

    @Override
    public Item getItemById(int id) throws SQLException {
        logger.debug("Получаем item по id: " + id);
        preparedStatement = connection.prepareStatement("SELECT * FROM product WHERE id = (?)");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            String title = resultSet.getString("title");
            int price = resultSet.getInt("price");
            return new Item(title, price, id);
        }
        return null;
    }

    @Override
    public Item getItemByTitle(String title) throws SQLException {
        logger.debug("Получаем item по title: " + title);
        preparedStatement = connection.prepareStatement("SELECT * FROM product WHERE title = (?)");
        preparedStatement.setString(1, title);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            int id = resultSet.getInt("id");
            int price = resultSet.getInt("price");
            return new Item(title, price, id);
        }
        return null;
    }

    @Override
    public boolean deleteItemById(int id) throws SQLException {
        logger.debug("Удаляем item по id: " + id);
        preparedStatement = connection.prepareStatement("DELETE FROM product WHERE id = (?) RETURNING id");
        preparedStatement.setInt(1, id);
        ResultSet resultSet = preparedStatement.executeQuery();
        if (resultSet.next()) {
            return true;
        }
        return false;
    }

    @Override
    public List<Item> getItems() throws SQLException {
        logger.debug("Получаем список item");
        List<Item> list = new ArrayList<>();
        preparedStatement = connection.prepareStatement("SELECT id, title, price FROM product;");
        ResultSet resultSet = preparedStatement.executeQuery();
        while (resultSet.next()) {
            list.add(
                    new Item(
                            resultSet.getString("title"),
                            resultSet.getInt("price"),
                            resultSet.getInt("id")
                    )
            );
        }
        return list;
    }

    @Override
    public void close() {
        try {
            if (preparedStatement != null) {
                preparedStatement.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}