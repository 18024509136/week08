package com.geek.hmilydemo;


import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class OrderServiceImpl implements OrderService {

    @Autowired
    private AccountClient accountClient;

    @Autowired
    private InventoryClient inventoryClient;

    @Override
    @Hmily(confirmMethod = "confirm", cancelMethod = "cancel")
    public void makePayment(Order order) {
        log.info("执行order服务try接口，" + order);

        InventoryDTO inventoryDTO = new InventoryDTO();
        inventoryDTO.setCount(order.getCount());
        inventoryDTO.setProductId(order.getProductId());
        System.out.println("===========执行inventory服务减库存接口==========");
        inventoryClient.decrease(inventoryDTO);

        // 进入扣减资金操作
        AccountDTO accountDTO = new AccountDTO();
        accountDTO.setAmount(order.getTotalAmount());
        accountDTO.setUserId(order.getUserId());
        System.out.println("===========执行account服务支付接口==========");
        accountClient.payment(accountDTO);
    }

    public void confirm(Order order) {
        log.info("执行order服务confirm接口，" + order);
    }

    public void cancel(Order order) {
        log.info("执行order服务cancel接口，" + order);
    }

}
