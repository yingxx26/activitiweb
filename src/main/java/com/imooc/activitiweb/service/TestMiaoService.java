package com.imooc.activitiweb.service;


import com.imooc.activitiweb.mapper.TestMiaoShaBeanMapper;
import com.imooc.activitiweb.mapper.UserInfoBeanMapper;
import com.imooc.activitiweb.pojo.TestMiaoShaBean;
import com.imooc.activitiweb.pojo.UserInfoBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;


@Service
public class TestMiaoService {
    private Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    TestMiaoShaBeanMapper testMiaoShaBeanMapper;

    @Transactional
    public void loadTestMiao(Long id) {
        synchronized (this) {
            TestMiaoShaBean testMiaoShaBean = testMiaoShaBeanMapper.selectById(1L);
            Integer stock = testMiaoShaBean.getStock();

            if (stock > 0) {
                stock--;
            }


            testMiaoShaBeanMapper.updateById(1L, stock);
            System.out.println("更新" + stock);
        }
    }
}
