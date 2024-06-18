package org.dtoexampleapp.repository;

import org.dtoexampleapp.entity.Buyer;
import org.dtoexampleapp.repository.utils.ConnectionToDBImpl;
import org.junit.jupiter.api.*;
import org.testcontainers.containers.PostgreSQLContainer;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.List;

class BuyerDaoTest {
    static PostgreSQLContainer<?> postgres = new PostgreSQLContainer<>(
            "postgres:16-alpine"
    );
    BuyerDao buyerDao;

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
        buyerDao = new BuyerDao(connectionToDB);
    }

    @Test
    void findAll() {
        buyerDao.add(new Buyer(1, "Max", 1000));
        buyerDao.add(new Buyer(1, "Max", 1000));
        List<Buyer> buyers = buyerDao.findAll();
        assertEquals(2, buyers.size());
    }

    @Test
    void deleteById() {
        buyerDao.add(new Buyer(1, "Max", 1000));
        buyerDao.deleteById(1);
        List<Buyer> buyers = buyerDao.findAll();
        assertEquals(0, buyers.size());
    }

    @Test
    void add() {
        buyerDao.add(new Buyer(1, "Max", 1000));
        buyerDao.add(new Buyer(1, "Max", 1000));
        List<Buyer> buyers = buyerDao.findAll();
        assertEquals(2, buyers.size());
    }

    @Test
    void update() {
        buyerDao.add(new Buyer(1, "Max", 1000));
        buyerDao.update(new Buyer(1,"Lena", 900));
        List<Buyer> buyers = buyerDao.findAll();
        Buyer buyer = buyers.get(0);
        assertEquals("Lena", buyer.getName());
        assertEquals(900, buyer.getBalance());
    }
}