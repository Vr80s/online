package com.xczhihui.shop.service.impl;

import com.xczhihui.shop.service.HiService;
import org.springframework.stereotype.Service;

@Service
public class HiServiceImpl implements HiService {

    @Override
    public void hi() {
        System.out.println("hi");
    }

}
