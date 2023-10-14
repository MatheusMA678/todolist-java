package com.matheus.todolist.task;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/tasks")
public class TaskController {

    @Autowired
    private ITaskRepository taskRepository;

    @PostMapping()
    private ResponseEntity create(@RequestBody TaskModel taskModel, HttpServletRequest request) {

        if (taskModel.getTitle().length() > 50) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("O título deve ter no máximo 50 caracteres.");
        }

        var userId = request.getAttribute("userId");
        taskModel.setUserId((UUID) userId);

        var createdTask = this.taskRepository.save(taskModel);

        return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
    }

}
