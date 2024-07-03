package com.tp.sharding;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.tp.sharding.model.UserInfo;
import com.tp.sharding.service.UserInfoService;
import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

@RunWith(SpringRunner.class)
@SpringBootTest(classes = ShardingApplication.class)
@Slf4j
class ShardingjdbcApplicationTests {
    @Autowired
    private UserInfoService userInfoService;
    @Test
    void contextLoads() {
        LambdaQueryWrapper<UserInfo> queryWrapper = new LambdaQueryWrapper<>();
        queryWrapper.eq(UserInfo::getUserId, "24000011");
        List<UserInfo> userInfo = userInfoService.list(queryWrapper);
        log.info("++++++++++++++:{}",userInfo.toString());
        if(Objects.nonNull(userInfo)){
            log.info("++++++++++++++:{}",userInfo.toString());
        }else {
            log.info("++++++++++++++:{}",Boolean.FALSE);
        }
    }

}
