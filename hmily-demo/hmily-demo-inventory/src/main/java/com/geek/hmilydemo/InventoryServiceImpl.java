package com.geek.hmilydemo;

import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class InventoryServiceImpl implements InventoryService {

    @Override
    @Hmily(confirmMethod = "confirm", cancelMethod = "cancel")
    public Boolean decrease(InventoryDTO inventoryDTO) {
        log.info("执行inventory服务try接口，" + inventoryDTO);
        return null;
    }

    public Boolean confirm(InventoryDTO inventoryDTO) {
        log.info("执行inventory服务confirm接口，" + inventoryDTO);
        return null;
    }

    public Boolean cancel(InventoryDTO inventoryDTO) {
        log.info("执行inventory服务cancel接口，" + inventoryDTO);
        return null;
    }
}
