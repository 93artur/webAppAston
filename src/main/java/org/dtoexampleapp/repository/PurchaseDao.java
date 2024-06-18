package org.dtoexampleapp.repository;

import org.dtoexampleapp.entity.Purchase;
import org.dtoexampleapp.repository.utils.ConnectionToDBImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PurchaseDao implements CrudOperations<Purchase> {
    private final String addSql = "INSERT INTO purchases (name, buyer_id) VALUES (?, ?)";
    private final String getSql = "SELECT * FROM purchases";
    private final String updateSql = "UPDATE purchases SET name=?, buyer_id=? WHERE id=?";
    private final String deleteSql = "DELETE FROM purchases WHERE id=?";
    private final ConnectionToDBImpl connectionToDB;

    public PurchaseDao(ConnectionToDBImpl connectionToDB) {
        this.connectionToDB = connectionToDB;
        createCustomersTableIfNotExists();
    }

    @Override
    public List findAll() {
        List<Purchase> listOfPurchases = new ArrayList<>();
        try (Connection connection = this.connectionToDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(getSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Purchase purchase = new Purchase();
                purchase.setId(resultSet.getInt(1));
                purchase.setName(resultSet.getString(2));
                purchase.setBuyerId(resultSet.getInt(3));
                listOfPurchases.add(purchase);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfPurchases;
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
    public void add(Purchase purchase) {
        try (Connection connection = this.connectionToDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(addSql);
            preparedStatement.setString(1, purchase.getName());
            preparedStatement.setInt(2, purchase.getBuyerId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Purchase purchase) {
        try (Connection connection = this.connectionToDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, purchase.getName());
            preparedStatement.setInt(2, purchase.getBuyerId());
            preparedStatement.setInt(3, purchase.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createCustomersTableIfNotExists() {
        try (Connection conn = this.connectionToDB.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    """
                           CREATE TABLE purchases(
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(30),
                           buyer_id INT
                           );
                            """
            );
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
