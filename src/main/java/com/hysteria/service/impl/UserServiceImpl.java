package com.hysteria.service.impl;

import com.hysteria.entity.User;
import com.hysteria.mapper.UserMapper;
import com.hysteria.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author yaojun
 * @since 2021-01-29
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

}
