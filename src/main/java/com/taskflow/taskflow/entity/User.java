package com.taskflow.taskflow.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.time.LocalDateTime;

@Data              // Lombok自动生成 get/set/toString 方法，不用自己写
@Entity            // 告诉JPA：这个类对应数据库的一张表
@EqualsAndHashCode(of = "id")  // 只用id来比较，id相同才是同一个对象
@Table(name = "users")  // 指定表名是 users
public class User {

    @Id                                           // 这个字段是主键
    @GeneratedValue(strategy = GenerationType.IDENTITY)  // 主键自增
    private Long id;

    @Column(nullable = false, unique = true, length = 50)  // 不能为空、不能重复、最长50字符
    private String username;

    @Column(nullable = false, unique = true, length = 100)
    private String email;

    @Column(nullable = false)
    private String password;  // 存的是加密后的密码，不是明文！

    @Column(name = "created_at")
    private LocalDateTime createdAt;

    @PrePersist  // 在插入数据库之前自动执行这个方法
    public void prePersist() {
        this.createdAt = LocalDateTime.now();  // 自动设置创建时间
    }
}
