package org.dtoexampleapp.service;

import lombok.RequiredArgsConstructor;
import org.dtoexampleapp.dto.BuyerDto;
import org.dtoexampleapp.dto.PurchaseDto;
import org.dtoexampleapp.dto.ShopDto;
import org.dtoexampleapp.entity.Buyer;
import org.dtoexampleapp.entity.BuyerShop;
import org.dtoexampleapp.entity.Purchase;
import org.dtoexampleapp.entity.Shop;
import org.dtoexampleapp.mappers.BuyerMapper;
import org.dtoexampleapp.mappers.PurchaseMapper;
import org.dtoexampleapp.mappers.ShopMapper;
import org.dtoexampleapp.repository.BuyerDao;
import org.dtoexampleapp.repository.BuyerShopDao;
import org.dtoexampleapp.repository.PurchaseDao;
import org.dtoexampleapp.repository.ShopDao;
import org.dtoexampleapp.repository.utils.ConnectionToDBImpl;
import org.dtoexampleapp.repository.utils.LocalConnectionInf;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
public class BuyerServiceImpl implements ServiceOperation<BuyerDto> {
    private final BuyerDao buyerDao;
    private final BuyerShopDao buyerShopDao;
    private final ShopDao shopDao;
    private final PurchaseDao purchaseDao;
    private final LocalConnectionInf localConnectionInf = new LocalConnectionInf();

    public BuyerServiceImpl(){
        buyerDao = new BuyerDao(new ConnectionToDBImpl(localConnectionInf.url, localConnectionInf.username, localConnectionInf.password));
        buyerShopDao = new BuyerShopDao(new ConnectionToDBImpl(localConnectionInf.url, localConnectionInf.username, localConnectionInf.password));
        shopDao = new ShopDao(new ConnectionToDBImpl(localConnectionInf.url, localConnectionInf.username, localConnectionInf.password));
        purchaseDao = new PurchaseDao(new ConnectionToDBImpl(localConnectionInf.url, localConnectionInf.username, localConnectionInf.password));
    }

    @Override
    public List<BuyerDto> findAll() {
        List<Buyer> buyers = buyerDao.findAll();
        List<Shop> shops = shopDao.findAll();
        List<Purchase> purchases = purchaseDao.findAll();
        List<BuyerShop> buyerShopList = buyerShopDao.findAll();
        List<BuyerDto> buyerDtoList = new ArrayList<>();
        List<ShopDto> shopDtoBuyerList = new ArrayList<>();
        List<PurchaseDto> purchaseDtoList = new ArrayList<>();
        BuyerDto buyerDto;
        for (Buyer buyer : buyers) {
            for (BuyerShop buyerShop : buyerShopList) {
                if (buyer.getId() == buyerShop.getBuyerId()) {
                    int shopId = buyerShop.getShopId();
                    for(Shop shop : shops){
                        if(shop.getId() == shopId){
                            shopDtoBuyerList.add(ShopMapper.INSTANCE.toDto(shop));
                        }
                    }
                }
            }
            for (Purchase purchase : purchases){
                if (buyer.getId() == purchase.getBuyerId()){
                    purchaseDtoList.add(PurchaseMapper.INSTANCE.toDto(purchase));
                }
            }
            buyerDto = BuyerMapper.INSTANCE.toDto(buyer);
            buyerDto.setShops(shopDtoBuyerList);
            buyerDto.setPurchases(purchaseDtoList);
            buyerDtoList.add(buyerDto);
        }
        return buyerDtoList;
    }

    @Override
    public void deleteById(int id) {
        buyerDao.deleteById(id);
    }

    @Override
    public void add(BuyerDto buyerDto) {
        Buyer buyer = BuyerMapper.INSTANCE.toEntity(buyerDto);
        buyerDao.add(buyer);
    }

    @Override
    public void update(BuyerDto buyerDto) {
        buyerDao.update(BuyerMapper.INSTANCE.toEntity(buyerDto));
    }
}
