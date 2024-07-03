package com.tp.sharding.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.tp.sharding.mapper.UserInfoMapper;
import com.tp.sharding.model.UserInfo;
import com.tp.sharding.service.UserInfoService;
import org.springframework.stereotype.Service;


@Service
public class UserInfoServiceImpl extends ServiceImpl< UserInfoMapper, UserInfo > implements UserInfoService {

}
