package rest.api.com.example.controller;

import rest.api.com.example.dto.UserRequest;
import rest.api.com.example.dto.UserResponse;
import rest.api.com.example.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/users")
public class UserController {
    private final UserService service;

    public UserController(UserService service) {
        this.service = service;
    }

    @Operation(summary = "Create a user")
    @PostMapping
    public ResponseEntity<UserResponse> createUser(
            @RequestHeader(value = "trace-id", required = false) String traceId,
            @RequestHeader(value = "client-id", required = false) String clientId,
            @Validated @RequestBody UserRequest request) {
        UserResponse response = service.createUser(request);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Get user by id")
    @GetMapping("/{id}")
    public ResponseEntity<UserResponse> getUserById(
            @RequestHeader(value = "trace-id", required = false) String traceId,
            @RequestHeader(value = "client-id", required = false) String clientId,
            @PathVariable Long id) {
        return ResponseEntity.ok(service.getUser(id));
    }

    @Operation(summary = "Get all users - supports paging, sorting, search")
    @GetMapping
    public ResponseEntity<Page<UserResponse>> getAllUsers(
            @RequestHeader(value = "trace-id", required = false) String traceId,
            @RequestHeader(value = "client-id", required = false) String clientId,
            @RequestParam(value = "search", required = false) String search,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "id,desc") String sort) {

        String[] sortParts = sort.split(",");
        PageRequest pageRequest = PageRequest.of(page, size,
                sortParts.length == 2 && sortParts[1].equalsIgnoreCase("asc") ?
                        org.springframework.data.domain.Sort.by(sortParts[0]).ascending()
                        : org.springframework.data.domain.Sort.by(sortParts[0]).descending());

        Page<UserResponse> pageResp = service.getAllUsers(search, pageRequest);
        return ResponseEntity.ok(pageResp);
    }

    @Operation(summary = "Update user")
    @PutMapping("/{id}")
    public ResponseEntity<UserResponse> updateUser(
            @RequestHeader(value = "trace-id", required = false) String traceId,
            @RequestHeader(value = "client-id", required = false) String clientId,
            @PathVariable Long id,
            @Validated @RequestBody UserRequest request) {
        return ResponseEntity.ok(service.updateUser(id, request));
    }

    @Operation(summary = "Delete user")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(
            @RequestHeader(value = "trace-id", required = false) String traceId,
            @RequestHeader(value = "client-id", required = false) String clientId,
            @PathVariable Long id) {
        service.deleteUser(id);
        return ResponseEntity.noContent().build();
    }
}