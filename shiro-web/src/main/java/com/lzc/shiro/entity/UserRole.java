package com.lzc.shiro.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author lzc
 * @date 2020/11/29 20:46
 */
@Entity
@Table
@Data
public class UserRole {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private Integer userId;

    private Integer roleId;

}
