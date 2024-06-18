package org.dtoexampleapp.service;

import org.dtoexampleapp.dto.BuyerDto;
import org.dtoexampleapp.dto.ShopDto;
import org.dtoexampleapp.entity.Buyer;
import org.dtoexampleapp.entity.BuyerShop;
import org.dtoexampleapp.entity.Shop;
import org.dtoexampleapp.mappers.BuyerMapper;
import org.dtoexampleapp.mappers.ShopMapper;
import org.dtoexampleapp.repository.BuyerDao;
import org.dtoexampleapp.repository.BuyerShopDao;
import org.dtoexampleapp.repository.ShopDao;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ShopServiceImplTest {
    @InjectMocks
    private ShopServiceImpl shopService;
    @Mock
    private BuyerDao buyerDao;
    @Mock
    private BuyerShopDao buyerShopDao;
    @Mock
    private ShopDao shopDao;

    @Test
    void findAll() {
        List<Buyer> buyers = new ArrayList<>();
        buyers.add(new Buyer(1, "Max", 1000));

        List<Shop> shops = new ArrayList<>();
        shops.add(new Shop(1,"Santa", 111));

        List<BuyerShop> buyerShopList = new ArrayList<>();
        buyerShopList.add(new BuyerShop(1, 1, 1));

        ShopDto shopDtoForBuyer = ShopMapper.INSTANCE.toDto(shops.get(0));
        List<ShopDto> shopDtoList = new ArrayList<>();
        shopDtoList.add(shopDtoForBuyer);

        List<BuyerDto> BuyersDto = new ArrayList<>();
        BuyersDto.add(BuyerMapper.INSTANCE.toDto(buyers.get(0)));

        ShopDto expectedShopDto = new ShopDto(1, "Santa", 111, BuyersDto);
        List<ShopDto> expectedShopDtoList = new ArrayList<>();
        expectedShopDtoList.add(expectedShopDto);

        when(buyerDao.findAll()).thenReturn(buyers);
        when(shopDao.findAll()).thenReturn(shops);
        when(buyerShopDao.findAll()).thenReturn(buyerShopList);

        List<ShopDto> shopDtos = shopService.findAll();

        Assertions.assertEquals(expectedShopDtoList, shopDtos);
    }

    @Test
    void deleteById() {
        int id = 1;
        Mockito.doNothing().when(shopService).deleteById(id);
        shopService.deleteById(id);
        verify(shopService).deleteById(id);
    }

    @Test
    void add() {
        ShopDto shopDto = new ShopDto();
        shopService.add(shopDto);
        verify(shopService).add(shopDto);
    }

    @Test
    void update() {
        ShopDto shopDto = new ShopDto();
        shopService.update(shopDto);
        verify(shopService).update(shopDto);
    }
}