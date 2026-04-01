package com.taskflow.taskflow.controller;

import com.taskflow.taskflow.common.ApiResponse;
import com.taskflow.taskflow.entity.Task;
import com.taskflow.taskflow.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/tasks")
@RequiredArgsConstructor
public class TaskController {

    private final TaskService taskService;

    @GetMapping
    public ApiResponse<List<Task>> getMyTasks() {
        return ApiResponse.success(taskService.getMyTasks());
    }

    @PostMapping
    public ApiResponse<Task> createTask(@RequestBody Task task) {
        return ApiResponse.success(taskService.createTask(task));
    }

    @PutMapping("/{id}")
    public ApiResponse<Task> updateTask(@PathVariable Long id,
                                        @RequestBody Task task) {
        return ApiResponse.success(taskService.updateTask(id, task));
    }

    @DeleteMapping("/{id}")
    public ApiResponse<Void> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ApiResponse.success(null);
    }
}