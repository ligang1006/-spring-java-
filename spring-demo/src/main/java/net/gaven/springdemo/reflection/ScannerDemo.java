package net.gaven.springdemo.reflection;

import org.springframework.stereotype.Service;

/**
 * @author: lee
 * @create: 2021/5/31 9:41 上午
 **/
@Service
public class ScannerDemo {
    private String option;
    private String userId;

    public String getOption() {
        return option;
    }

    public void setOption(String option) {
        this.option = option;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }
}
