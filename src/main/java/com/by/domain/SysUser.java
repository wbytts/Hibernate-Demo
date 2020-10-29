package com.by.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wangby
 * @version 0.0.1
 * @date 2020-10-29 15:34
 */
public class SysUser {

    private Long user_id;
    private String user_code;
    private String user_name;
    private String user_password;
    private String user_state;

    //如何设置多对多的关系，即表示一个用户可以选择多个角色？
    //放置的是角色的集合
    private Set<SysRole> roles = new HashSet<SysRole>();

    public Long getUser_id() {
        return user_id;
    }
    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }
    public String getUser_code() {
        return user_code;
    }
    public void setUser_code(String user_code) {
        this.user_code = user_code;
    }
    public String getUser_name() {
        return user_name;
    }
    public void setUser_name(String user_name) {
        this.user_name = user_name;
    }
    public String getUser_password() {
        return user_password;
    }
    public void setUser_password(String user_password) {
        this.user_password = user_password;
    }
    public String getUser_state() {
        return user_state;
    }
    public void setUser_state(String user_state) {
        this.user_state = user_state;
    }
    public Set<SysRole> getRoles() {
        return roles;
    }
    public void setRoles(Set<SysRole> roles) {
        this.roles = roles;
    }

}
