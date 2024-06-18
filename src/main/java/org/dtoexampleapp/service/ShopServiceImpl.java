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
import org.dtoexampleapp.repository.utils.ConnectionToDBImpl;
import org.dtoexampleapp.repository.utils.LocalConnectionInf;

import java.util.ArrayList;
import java.util.List;

public class ShopServiceImpl implements ServiceOperation<ShopDto> {
    private final BuyerDao buyerDao;
    private final BuyerShopDao buyerShopDao;
    private final ShopDao shopDao;
    private final LocalConnectionInf localConnectionInf = new LocalConnectionInf();

    public ShopServiceImpl() {
        buyerDao = new BuyerDao(new ConnectionToDBImpl(localConnectionInf.url, localConnectionInf.username, localConnectionInf.password));
        buyerShopDao = new BuyerShopDao(new ConnectionToDBImpl(localConnectionInf.url, localConnectionInf.username, localConnectionInf.password));
        shopDao = new ShopDao(new ConnectionToDBImpl(localConnectionInf.url, localConnectionInf.username, localConnectionInf.password));
    }

    public ShopServiceImpl(BuyerDao buyerDao, BuyerShopDao buyerShopDao, ShopDao shopDao) {
        this.buyerDao = buyerDao;
        this.buyerShopDao = buyerShopDao;
        this.shopDao = shopDao;
    }

    @Override
    public List<ShopDto> findAll() {
        List<Buyer> buyers = buyerDao.findAll();
        List<Shop> shops = shopDao.findAll();
        List<BuyerShop> buyerShopList = buyerShopDao.findAll();
        List<ShopDto> shopDtos = new ArrayList<>();
        List<BuyerDto> buyerDtoList = new ArrayList<>();
        ShopDto shopDto;
        for (Shop shop : shops) {
            for (BuyerShop buyerShop : buyerShopList)
                if (shop.getId() == buyerShop.getShopId()) {
                    int buyerId = buyerShop.getBuyerId();
                    for (Buyer buyer : buyers) {
                        if (buyer.getId() == buyerId) {
                            buyerDtoList.add(BuyerMapper.INSTANCE.toDto(buyer));
                        }
                    }
                }
            shopDto = ShopMapper.INSTANCE.toDto(shop);
            shopDto.setBuyers(buyerDtoList);
            shopDtos.add(shopDto);
        }
        return shopDtos;
    }

    @Override
    public void deleteById(int id) {
        shopDao.deleteById(id);
    }

    @Override
    public void add(ShopDto shopDto) {
        shopDao.add(ShopMapper.INSTANCE.toEntity(shopDto));
    }

    @Override
    public void update(ShopDto shopDto) {
        shopDao.update(ShopMapper.INSTANCE.toEntity(shopDto));
    }
}
