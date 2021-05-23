package com.geek.hmilydemo;

import lombok.extern.slf4j.Slf4j;
import org.dromara.hmily.annotation.Hmily;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class AccountServiceImpl implements AccountService {

    @Override
    @Hmily(confirmMethod = "confirm", cancelMethod = "cancel")
    public boolean payment(AccountDTO accountDTO) {
        log.info("执行account服务try接口，" + accountDTO);
        int i = 1 / 0;
        return true;
    }

    public boolean confirm(final AccountDTO accountDTO) {
        log.info("执行account服务confirm接口，" + accountDTO);
        return true;
    }


    public boolean cancel(final AccountDTO accountDTO) {
        log.info("执行account服务cancel接口，" + accountDTO);
        return true;
    }


}
