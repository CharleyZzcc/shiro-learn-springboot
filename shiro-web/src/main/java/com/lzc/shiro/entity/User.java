package com.lzc.shiro.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author lzc
 * @date 2020/11/29 20:19
 */
@Entity
@Table
@Data
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String username;

    private String password;
}
