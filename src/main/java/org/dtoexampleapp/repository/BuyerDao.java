package org.dtoexampleapp.repository;

import org.dtoexampleapp.entity.Buyer;
import org.dtoexampleapp.repository.utils.ConnectionToDBImpl;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BuyerDao implements CrudOperations<Buyer> {
    private final String addSql = "INSERT INTO buyers (name, balance) VALUES (?, ?)";
    private final String getSql = "SELECT * FROM buyers";
    private final String updateSql = "UPDATE buyers SET name=?, balance=? WHERE id=?";
    private final String deleteSql = "DELETE FROM buyers WHERE id=?";
    private final ConnectionToDBImpl connectionToDB;

    public BuyerDao(ConnectionToDBImpl connectionToDB) {
        this.connectionToDB = connectionToDB;
        createCustomersTableIfNotExists();
    }

    @Override
    public List findAll() {
        List<Buyer> listOfBuyers = new ArrayList<>();
        try (Connection connection = this.connectionToDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(getSql);
            ResultSet resultSet = preparedStatement.executeQuery();
            while (resultSet.next()) {
                Buyer buyer = new Buyer();
                buyer.setId(resultSet.getInt(1));
                buyer.setName(resultSet.getString(2));
                buyer.setBalance(resultSet.getInt(3));
                listOfBuyers.add(buyer);
            }
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
        return listOfBuyers;
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
    public void add(Buyer buyer) {
        try (Connection connection = this.connectionToDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(addSql);
            preparedStatement.setString(1, buyer.getName());
            preparedStatement.setInt(2, buyer.getBalance());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void update(Buyer buyer) {
        try (Connection connection = this.connectionToDB.getConnection()) {
            PreparedStatement preparedStatement = connection.prepareStatement(updateSql);
            preparedStatement.setString(1, buyer.getName());
            preparedStatement.setInt(2, buyer.getBalance());
            preparedStatement.setInt(3, buyer.getId());
            preparedStatement.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    private void createCustomersTableIfNotExists() {
        try (Connection conn = this.connectionToDB.getConnection()) {
            PreparedStatement pstmt = conn.prepareStatement(
                    """
                           CREATE TABLE buyers(
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(30),
                           balance INT
                           );
                            """
            );
            pstmt.execute();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
