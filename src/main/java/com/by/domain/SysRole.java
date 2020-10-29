package com.by.domain;

import java.util.HashSet;
import java.util.Set;

/**
 * @author wangby
 * @version 0.0.1
 * @date 2020-10-29 15:34
 */
public class SysRole {
    private Long role_id;
    private String role_name;
    private String role_memo;

    //如何设置多对多的关系，即表示一个角色被多个用户所选择？
    //放置的是用户的集合
    private Set<SysUser> users = new HashSet<SysUser>();

    public Long getRole_id() {
        return role_id;
    }
    public void setRole_id(Long role_id) {
        this.role_id = role_id;
    }
    public String getRole_name() {
        return role_name;
    }
    public void setRole_name(String role_name) {
        this.role_name = role_name;
    }
    public String getRole_memo() {
        return role_memo;
    }
    public void setRole_memo(String role_memo) {
        this.role_memo = role_memo;
    }
    public Set<SysUser> getUsers() {
        return users;
    }
    public void setUsers(Set<SysUser> users) {
        this.users = users;
    }
}
