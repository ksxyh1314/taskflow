package com.taskflow.taskflow.repository;

import com.taskflow.taskflow.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {

    // 查询某个用户的所有未删除任务
    // deletedAt为null表示没有被删除
    List<Task> findByUserIdAndDeletedAtIsNull(Long userId);

    // 根据id和userId查询（防止用户查别人的任务）
    java.util.Optional<Task> findByIdAndUserId(Long id, Long userId);
}