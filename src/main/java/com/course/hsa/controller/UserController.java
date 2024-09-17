package com.course.hsa.controller;

import com.course.hsa.controller.dto.UserDto;
import com.course.hsa.domain.User;
import com.course.hsa.repository.UserRepository;
import com.course.hsa.service.TaskService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.Month;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class UserController {

    private final UserRepository userRepository;
    private final TaskService taskService;

    @GetMapping(path = "/users")
    public List<UserDto> getUsers(@RequestParam(required = false) String name) {
        return userRepository.getByName(name).stream().map(this::map).toList();
    }

    @GetMapping(path = "/users/count")
    public Long getUsersCount(@RequestParam String name) {
        return userRepository.countByName(name);
    }

    @PostMapping(path = "/users/countBetweenDates")
    public void countUsersBetweenDates(@RequestParam Long dbCallQty) {
        taskService.executeConcurrently(createTask(), dbCallQty);
    }

    private Runnable createTask() {
        var fromDate = asDate(LocalDate.of(1991, Month.JANUARY, 01));
        var toDate = asDate(LocalDate.of(1995, Month.JANUARY, 01));
        return () -> userRepository.countByBirthDateBetween(fromDate, toDate);
    }

    private UserDto map(User entity) {
        return new UserDto(entity.getName(), entity.getBirthDate());
    }

    private static Date asDate(LocalDate localDate) {
        return Date.from(localDate.atStartOfDay().atZone(ZoneId.systemDefault()).toInstant());
    }
}
