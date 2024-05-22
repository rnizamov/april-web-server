package ru.rnizamov.web.server.application;

import java.sql.SQLException;
import java.util.List;

public interface ProductService {
    void save(Item item) throws SQLException;

    void updateItem(Item item) throws SQLException;

    boolean deleteItemById(int id) throws SQLException;

    Item getItemById(int id) throws SQLException;
    Item getItemByTitle(String title) throws SQLException;

    List<Item> getItems() throws SQLException;

    void close();
}
