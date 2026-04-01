package com.taskflow.taskflow.service;

import com.taskflow.taskflow.common.BusinessException;
import com.taskflow.taskflow.entity.Task;
import com.taskflow.taskflow.repository.TaskRepository;
import com.taskflow.taskflow.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {

    private final TaskRepository taskRepository;
    private final UserRepository userRepository;

    // 获取当前登录用户id的工具方法
    private Long getCurrentUserId() {
        String username = SecurityContextHolder.getContext()
                .getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new BusinessException(400,"用户不存在"))
                .getId();
    }

    // 查询我的所有任务
    public List<Task> getMyTasks() {
        return taskRepository.findByUserIdAndDeletedAtIsNull(getCurrentUserId());
    }

    // 创建任务
    public Task createTask(Task task) {
        task.setUserId(getCurrentUserId()); // 从Token解析，不信任前端
        return taskRepository.save(task);
    }

    // 更新任务
    public Task updateTask(Long taskId, Task taskData) {
        Task task = taskRepository.findByIdAndUserId(taskId, getCurrentUserId())
                .orElseThrow(() -> new BusinessException(400,"任务不存在或无权限"));
        task.setTitle(taskData.getTitle());
        task.setDescription(taskData.getDescription());
        task.setStatus(taskData.getStatus());
        task.setPriority(taskData.getPriority());
        task.setDueDate(taskData.getDueDate());
        return taskRepository.save(task);
    }

    // 软删除任务
    public void deleteTask(Long taskId) {
        Task task = taskRepository.findByIdAndUserId(taskId, getCurrentUserId())
                .orElseThrow(() -> new BusinessException(400,"任务不存在或无权限"));
        task.setDeletedAt(LocalDateTime.now()); // 软删除：记录时间
        taskRepository.save(task);
    }
}