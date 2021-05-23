
package com.geek.hmilydemo;

import org.dromara.hmily.annotation.Hmily;


@SuppressWarnings("all")
public interface InventoryService {

    /**
     * 扣减库存操作.
     * 这一个tcc接口
     *
     * @param inventoryDTO 库存DTO对象
     * @return true
     */
    @Hmily
    Boolean decrease(InventoryDTO inventoryDTO);

}
