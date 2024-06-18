package org.dtoexampleapp.repository;

import org.dtoexampleapp.entity.Shop;
import org.dtoexampleapp.repository.utils.ConnectionToDBImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ShopDao implements CrudOperations<Shop> {
    final String addSql = "INSERT INTO shops (name, contact_number) VALUES (?, ?)";
    final String getSql = "SELECT * FROM shops";
    final String updateSql = "UPDATE shops SET name=?, contact_number=? WHERE id=?";
    final String deleteSql = "DELETE FROM shops WHERE id=?";
    private final ConnectionToDBImpl connectionToDB;

    public ShopDao(ConnectionToDBImpl connectionToDB) {
        this.connectionToDB = connectionToDB;
        createCustomersTableIfNotExists();
    }

    @Override
    public List findAll() {
        List<Shop> listOfShops = new ArrayList<>();
        try (Connection connection = this.connectionToDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(getSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Shop shop = new Shop();
                shop.setId(resultSet.getInt(1));
                shop.setName(resultSet.getString(2));
                shop.setContactNumber(resultSet.getInt(3));
                listOfShops.add(shop);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfShops;
    }

    @Override
    public void deleteById(int id) {
        try (Connection connection = this.connectionToDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(deleteSql);
            preparedStatement.setInt(1, id);
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void add(Shop shop) {
        try (Connection connection = this.connectionToDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(addSql);
            preparedStatement.setString(1, shop.getName());
            preparedStatement.setInt(2, shop.getContactNumber());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Shop shop) {
        try (Connection connection = this.connectionToDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, shop.getName());
            preparedStatement.setInt(2, shop.getContactNumber());
            preparedStatement.setInt(3, shop.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
    private void createCustomersTableIfNotExists() {
        try (Connection conn = this.connectionToDB.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    """
                           CREATE TABLE shops(
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(30),
                           contact_number INT
                           );
                            """
            );
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
