package com.geek.hmilydemo;

import org.dromara.hmily.annotation.Hmily;

public interface AccountService {

    /**
     * 扣款支付.
     *
     * @param accountDTO 参数dto
     * @return true boolean
     */
    @Hmily
    boolean payment(AccountDTO accountDTO);
}
