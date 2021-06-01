package net.gaven.springdemo.iocmyself.withoutset;

/**
 * @author: lee
 * @create: 2021/5/27 8:11 下午
 **/
public class UserController {
    @Autowired
    private UserService userService;

    public UserService getUserService() {
        return userService;
    }

//    public void setUserService(UserService userService) {
//        this.userService = userService;
//    }
}
