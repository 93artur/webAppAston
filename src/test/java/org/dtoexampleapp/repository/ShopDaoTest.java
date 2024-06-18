package org.dtoexampleapp.repository;

import org.dtoexampleapp.entity.Shop;
import org.dtoexampleapp.repository.utils.ConnectionToDBImpl;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.testcontainers.containers.PostgreSQLContainer;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class ShopDaoTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );
    ShopDao shopDao;

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
        shopDao = new ShopDao(connectionToDB);
    }

    @Test
    void findAll() {
        shopDao.add(new Shop(1,"Veras", 175));
        shopDao.add(new Shop(2,"Santa", 332));
        List<Shop> shops = shopDao.findAll();
        assertEquals(2, shops.size());
    }

    @Test
    void deleteById() {
        shopDao.add(new Shop(1,"Veras", 175));
        shopDao.deleteById(1);
        List<Shop> shops = shopDao.findAll();
        assertEquals(0, shops.size());
    }

    @Test
    void add() {
        shopDao.add(new Shop(1,"Veras", 175));
        List<Shop> shops = shopDao.findAll();
        assertEquals(1, shops.size());
    }

    @Test
    void update() {
        shopDao.add(new Shop(1,"Veras", 175));
        shopDao.update(new Shop(1,"Santa", 332));
        List<Shop> shops = shopDao.findAll();
        Shop shop = shops.get(0);
        assertEquals("Santa", shop.getName());
        assertEquals(332, shop.getContactNumber());
    }
}