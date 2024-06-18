package org.dtoexampleapp.repository;

import org.dtoexampleapp.entity.BuyerShop;
import org.dtoexampleapp.repository.utils.ConnectionToDBImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class BuyerShopDaoTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );
    BuyerShopDao buyerShopDao;

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
        buyerShopDao = new BuyerShopDao(connectionToDB);
    }

    @Test
    void findAll() {
        buyerShopDao.add(new BuyerShop(1,2,1));
        buyerShopDao.add(new BuyerShop(2,1,2));
        List<BuyerShop> buyerShopList = buyerShopDao.findAll();
        assertEquals(2, buyerShopList.size());
    }

    @Test
    void deleteById() {
        buyerShopDao.add(new BuyerShop(1,2,1));
        buyerShopDao.deleteById(1);
        List<BuyerShop> buyerShopList = buyerShopDao.findAll();
        assertEquals(0, buyerShopList.size());
    }

    @Test
    void add() {
        buyerShopDao.add(new BuyerShop(1,2,1));
        buyerShopDao.add(new BuyerShop(2,1,2));
        List<BuyerShop> buyerShopList = buyerShopDao.findAll();
        assertEquals(2, buyerShopList.size());
    }

    @Test
    void update() {
        buyerShopDao.add(new BuyerShop(1,2,1));
        buyerShopDao.update(new BuyerShop(1,1,2));
        List<BuyerShop> buyerShopList = buyerShopDao.findAll();
        BuyerShop buyerShop = buyerShopList.get(0);
        assertEquals(1, buyerShop.getBuyerId());
        assertEquals(2, buyerShop.getShopId());
    }
}