package org.dtoexampleapp.repository;

import org.dtoexampleapp.entity.Purchase;
import org.dtoexampleapp.repository.utils.ConnectionToDBImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class PurchaseDaoTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );
    PurchaseDao purchaseDao;

    @BeforeAll
    static void beforeAll() {
        postgres.start();
    }

    @AfterAll
    static void afterAll() {
        postgres.stop();
    }

    @BeforeEach
    void setUp() {
        ConnectionToDBImpl connectionToDB = new ConnectionToDBImpl(
                postgres.getJdbcUrl(),
                postgres.getUsername(),
                postgres.getPassword()
        );
        purchaseDao = new PurchaseDao(connectionToDB);
    }

    @Test
    void findAll() {
        purchaseDao.add(new Purchase(1, "Milk",1));
        purchaseDao.add(new Purchase(2, "Bread",1));
        List<Purchase> purchases = purchaseDao.findAll();
        assertEquals(2, purchases.size());
    }

    @Test
    void deleteById() {
        purchaseDao.add(new Purchase(1, "Milk",1));
        purchaseDao.deleteById(1);
        List<Purchase> purchases = purchaseDao.findAll();
        assertEquals(0, purchases.size());
    }

    @Test
    void add() {
        purchaseDao.add(new Purchase(1, "Milk",1));
        purchaseDao.add(new Purchase(2, "Bread",1));
        List<Purchase> purchases = purchaseDao.findAll();
        assertEquals(2, purchases.size());
    }

    @Test
    void update() {
        purchaseDao.add(new Purchase(1, "Milk",1));
        purchaseDao.update(new Purchase(1, "Bread",1));
        List<Purchase> purchases = purchaseDao.findAll();
        Purchase purchase = purchases.get(0);
        assertEquals("Bread", purchase.getName());
        assertEquals(1, purchase.getBuyerId());
    }
}