
package com.geek.hmilydemo;

import org.dromara.hmily.annotation.Hmily;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(value = "account")
public interface AccountClient {

    /**
     * Account项目接口 1
     */
    @RequestMapping("/account/payment")
    @Hmily
    Boolean payment(@RequestBody AccountDTO accountDO);

}
