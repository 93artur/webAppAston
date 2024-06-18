package org.dtoexampleapp.service;

import org.dtoexampleapp.dto.BuyerDto;
import org.dtoexampleapp.dto.PurchaseDto;
import org.dtoexampleapp.dto.ShopDto;
import org.dtoexampleapp.entity.Buyer;
import org.dtoexampleapp.entity.BuyerShop;
import org.dtoexampleapp.entity.Purchase;
import org.dtoexampleapp.entity.Shop;
import org.dtoexampleapp.mappers.PurchaseMapper;
import org.dtoexampleapp.mappers.ShopMapper;
import org.dtoexampleapp.repository.BuyerDao;
import org.dtoexampleapp.repository.BuyerShopDao;
import org.dtoexampleapp.repository.PurchaseDao;
import org.dtoexampleapp.repository.ShopDao;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class BuyerServiceImplTest {
    @InjectMocks
    private BuyerServiceImpl buyerService;
    @Mock
    private BuyerDao buyerDao;
    @Mock
    private BuyerShopDao buyerShopDao;
    @Mock
    private ShopDao shopDao;
    @Mock
    private PurchaseDao purchaseDao;

    @Test
    void findAll() {
        List<Buyer> buyers = new ArrayList<>();
        buyers.add(new Buyer(1, "Max", 1000));

        List<Shop> shops = new ArrayList<>();
        shops.add(new Shop(1,"Santa", 111));

        List<Purchase> purchases = new ArrayList<>();
        purchases.add(new Purchase(1, "Milk", 1));

        List<BuyerShop> buyerShopList = new ArrayList<>();
        buyerShopList.add(new BuyerShop(1, 1, 1));

        ShopDto shopDto = ShopMapper.INSTANCE.toDto(shops.get(0));
        List<ShopDto> shopDtoList = new ArrayList<>();
        shopDtoList.add(shopDto);

        PurchaseDto purchaseDto = PurchaseMapper.INSTANCE.toDto(purchases.get(0));
        List<PurchaseDto> purchaseDtoList = new ArrayList<>();
        purchaseDtoList.add(purchaseDto);

        List<BuyerDto> expectedBuyersDto = new ArrayList<>();
        BuyerDto buyerDto = new BuyerDto(1, "Max", 1000, shopDtoList, purchaseDtoList);
        expectedBuyersDto.add(buyerDto);

        when(buyerDao.findAll()).thenReturn(buyers);
        when(shopDao.findAll()).thenReturn(shops);
        when(purchaseDao.findAll()).thenReturn(purchases);
        when(buyerShopDao.findAll()).thenReturn(buyerShopList);

        List <BuyerDto> buyerDtos = buyerService.findAll();

        assertEquals(expectedBuyersDto, buyerDtos);
    }

    @Test
    void deleteByIdTest() {
        int id = 1;
        Mockito.doNothing().when(buyerService).deleteById(id);
        buyerService.deleteById(id);
        verify(buyerService).deleteById(id);
    }

    @Test
    void add() {
        BuyerDto buyerDto = new BuyerDto();
        buyerService.add(buyerDto);
        verify(buyerService).add(buyerDto);
    }

    @Test
    void update() {
        BuyerDto buyerDto = new BuyerDto();
        buyerService.update(buyerDto);
        verify(buyerService).update(buyerDto);
    }
}