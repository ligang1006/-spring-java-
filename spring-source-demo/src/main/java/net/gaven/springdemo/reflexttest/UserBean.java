package net.gaven.springdemo.reflexttest;

/**
 * @author: lee
 * @create: 2021/9/17 10:59 上午
 **/
public class UserBean {
    private Integer id;
    private int age;
    private String name;
    private String address;

    public UserBean(){
        System.out.println("实例化");
    }

    public Integer getId() {
        return id;
    }
    public void setId(Integer id) {
        this.id = id;
    }
    public int getAge() {
        return age;
    }
    public void setAge(int age) {
        this.age = age;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getAddress() {
        return address;
    }
    public void setAddress(String address) {
        this.address = address;
    }



}
