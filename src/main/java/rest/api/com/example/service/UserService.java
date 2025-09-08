package rest.api.com.example.service;

import rest.api.com.example.dto.UserRequest;
import rest.api.com.example.dto.UserResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponse createUser(UserRequest request);
    UserResponse getUser(Long id);
    Page<UserResponse> getAllUsers(String search, Pageable pageable);
    UserResponse updateUser(Long id, UserRequest request);
    void deleteUser(Long id);
}