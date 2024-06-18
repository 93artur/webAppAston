package org.dtoexampleapp.repository;

import org.dtoexampleapp.entity.BuyerShop;
import org.dtoexampleapp.repository.utils.ConnectionToDBImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyerShopDao implements CrudOperations<BuyerShop> {
    private final String addSql = "INSERT INTO buyer_shop (buyer_id, shop_id) VALUES (?, ?)";
    private final String getSql = "SELECT * FROM buyer_shop";
    private final String updateSql = "UPDATE buyer_shop SET buyer_id=?, shop_id=? WHERE id=?";
    private final String deleteSql = "DELETE FROM buyer_shop WHERE id=?";
    private final ConnectionToDBImpl connectionToDB;

    public BuyerShopDao(ConnectionToDBImpl connectionToDB) {
        this.connectionToDB = connectionToDB;
        createCustomersTableIfNotExists();
    }

    @Override
    public List findAll() {
        List<BuyerShop> buyerShopList = new ArrayList<>();
        try (Connection connection = this.connectionToDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(getSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                BuyerShop buyerShop = new BuyerShop();
                buyerShop.setId(resultSet.getInt(1));
                buyerShop.setBuyerId(resultSet.getInt(2));
                buyerShop.setShopId(resultSet.getInt(3));
                buyerShopList.add(buyerShop);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return buyerShopList;
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
    public void add(BuyerShop buyerShop) {
        try (Connection connection = this.connectionToDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(addSql);
            preparedStatement.setInt(1, buyerShop.getBuyerId());
            preparedStatement.setInt(2, buyerShop.getShopId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(BuyerShop buyerShop) {
        try (Connection connection = this.connectionToDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setInt(1, buyerShop.getBuyerId());
            preparedStatement.setInt(2, buyerShop.getShopId());
            preparedStatement.setInt(3, buyerShop.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createCustomersTableIfNotExists() {
        try (Connection conn = this.connectionToDB.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    """
                           CREATE TABLE buyer_shop(
                           id SERIAL PRIMARY KEY,
                           buyer_id INT,
                           shop_id INT
                           );
                            """
            );
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
