package com.lzc.shiro.entity;

import lombok.Data;

import javax.persistence.*;

/**
 * @author lzc
 * @date 2020/11/29 20:50
 */
@Entity
@Table
@Data
public class Permission {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String permission;
}
