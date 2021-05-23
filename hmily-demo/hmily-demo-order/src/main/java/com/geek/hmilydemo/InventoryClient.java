
package com.geek.hmilydemo;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "inventory")
public interface InventoryClient {

    /**
     * 库存扣减.
     *
     * @param inventoryDTO 实体对象
     * @return true 成功
     */
    @RequestMapping("/inventory/decrease")
    @Hmily
    Boolean decrease(@RequestBody InventoryDTO inventoryDTO);
}
