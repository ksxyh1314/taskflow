package com.taskflow.taskflow.repository;
import com.taskflow.taskflow.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
// JPA 帮你自动生成增删改查的代码，你不用自己写 SQL
import org.springframework.stereotype.Repository;
import java.util.Optional;
//用来安全地处理可能为 null 的情况，避免空指针异常
@Repository//数据库操作层
public  interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByUsername(String username);
    // 自动生成：SELECT * FROM users WHERE username = ?
    Optional<User> findByEmail(String email);

    boolean existsByUsername(String username);
    // 自动生成：SELECT COUNT(*) FROM users WHERE username = ?
    boolean existsByEmail(String email);
}
