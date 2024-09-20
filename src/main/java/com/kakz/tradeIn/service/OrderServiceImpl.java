package com.kakz.tradeIn.service;

import com.kakz.tradeIn.domain.OrderStatus;
import com.kakz.tradeIn.domain.OrderType;
import com.kakz.tradeIn.model.*;
import com.kakz.tradeIn.repository.OrderItemRepository;
import com.kakz.tradeIn.repository.OrderRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class OrderServiceImpl implements OrderService{

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private WalletService walletService;

    @Autowired
    private OrderItemRepository orderItemRepository;

    @Autowired
    private AssetService assetService;


    @Override
    public Order createOrder(User user, OrderItem orderItem, OrderType orderType) {

        double price = orderItem.getCoin().getCurrentPrice()*orderItem.getQuantity();

        Order order = new Order();
        order.setOrderType(orderType);
        order.setOrderItem(orderItem);
        order.setPrice(BigDecimal.valueOf(price));
        order.setUser(user);
        order.setTimestamp(LocalDateTime.now());
        order.setStatus(OrderStatus.PENDING);

        return orderRepository.save(order);
    }

    @Override
    public Order getOrderById(Long id) throws Exception {
        return  orderRepository
                .findById(id)
                .orElseThrow(
                        ()-> new Exception("Order not found"));
    }

    @Override
    public List<Order> getAllOrdersofUser(Long userId, OrderType orderType, String assetSymbol) {

        return orderRepository.findByUserId(userId);
    }

    public OrderItem createOrderItem(Coin coin,double quantity,
                                      double buyPrice,double sellPrice){
        OrderItem orderItem = new OrderItem();
        orderItem.setQuantity(quantity);
        orderItem.setBuyPrice(buyPrice);
        orderItem.setSellPrice(sellPrice);
        orderItem.setCoin(coin);
        return orderItemRepository.save(orderItem);
    }

    @Transactional
    public Order buyAsset(Coin coin, double quantity,User user) throws Exception {
        if(quantity< 0){
            throw new Exception("Quantity must be more than zero");
        }
        double price = coin.getCurrentPrice();
        OrderItem orderItem = createOrderItem(coin, quantity, price,0);
        Order order = createOrder(user,orderItem,OrderType.BUY);
        orderItem.setOrder(order);

        walletService.payOrderPayment(order, user);
        order.setStatus(OrderStatus.SUCCESS);
        order.setOrderType(OrderType.BUY);
        Order saveOrder = orderRepository.save(order);
//        Create Asset
        Asset oldAsset = assetService.findAssetByUserIdAndCoinId(order.getUser().getId(),
                order.getOrderItem().getCoin().getId());
        if(oldAsset == null){
            assetService.createAsset(user,
                    orderItem.getCoin(),
                    orderItem.getQuantity());
        }
        else{
            assetService.updateAsset(oldAsset.getId(),quantity);
        }
        return saveOrder;
    }

    @Transactional
    public Order sellAsset(Coin coin, double quantity,User user) throws Exception {
        if(quantity<= 0){
            throw new Exception("Quantity must be more than zero");
        }
        double sellPrice = coin.getCurrentPrice();

        Asset assetToSell =assetService.findAssetByUserIdAndCoinId(
                user.getId(),
                coin.getId());

        if(assetToSell != null) {

            OrderItem orderItem = createOrderItem(coin,
                    quantity,
                    assetToSell.getBuyPrice(),
                    sellPrice);

        Order order = createOrder(user,
                orderItem,
                OrderType.SELL);

        orderItem.setOrder(order);

        if(assetToSell.getQuantity()>= quantity){
            order.setStatus(OrderStatus.SUCCESS);
            order.setOrderType(OrderType.SELL);
            Order savedOrder = orderRepository.save(order);
            walletService.payOrderPayment(order,user);

            Asset updatedAsset = assetService.updateAsset(
                    assetToSell.getId(),
                    -quantity);
            if(updatedAsset.getQuantity()*coin.getCurrentPrice()<=1 ){
                assetService.deleteAsset(updatedAsset.getId());
            }
            return savedOrder;
        }

//        Create Asset
        throw new Exception("Insufficient Quantity to Sell Asset");
        }
        throw new Exception("Asset Not Found");
    }

    @Override
    @Transactional
    public Order processOrder(Coin coin, double quantity,
                              OrderType orderType, User user) throws Exception {
        if(orderType == OrderType.BUY){
            return buyAsset(coin, quantity,user);
        }
        else if(orderType == OrderType.SELL){
            return sellAsset(coin, quantity,user);
        }
        else{
            throw  new Exception("Invalid Order Type");
        }
    }
}
