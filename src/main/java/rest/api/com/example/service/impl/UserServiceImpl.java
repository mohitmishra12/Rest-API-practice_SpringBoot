package rest.api.com.example.service.impl;

import rest.api.com.example.dto.UserRequest;
import rest.api.com.example.dto.UserResponse;
import rest.api.com.example.exception.ResourceNotFoundException;
import rest.api.com.example.model.User;
import rest.api.com.example.repository.UserRepository;
import rest.api.com.example.service.UserService;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
@Transactional
public class UserServiceImpl implements UserService {
    private final UserRepository repo;
    private final ModelMapper mapper;

    public UserServiceImpl(UserRepository repo, ModelMapper mapper) {
        this.repo = repo;
        this.mapper = mapper;
    }

    @Override
    public UserResponse createUser(UserRequest request) {
        if (repo.existsByEmail(request.getEmail())) {
            throw new IllegalArgumentException("Email already exists");
        }
        User user = mapper.map(request, User.class);
        User saved = repo.save(user);
        return mapper.map(saved, UserResponse.class);
    }

    @Override
    public UserResponse getUser(Long id) {
        User user = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));
        return mapper.map(user, UserResponse.class);
    }

    @Override
    public Page<UserResponse> getAllUsers(String search, Pageable pageable) {
        Page<User> page;
        if (search == null || search.isBlank()) {
            page = repo.findAll(pageable);
        } else {
            page = new PageImpl<>(repo.findAll().stream()
                    .filter(u -> u.getName().toLowerCase().contains(search.toLowerCase())
                            || u.getEmail().toLowerCase().contains(search.toLowerCase()))
                    .skip(pageable.getOffset())
                    .limit(pageable.getPageSize())
                    .collect(Collectors.toList()),
                    pageable,
                    repo.count());
        }
        return page.map(u -> mapper.map(u, UserResponse.class));
    }

    @Override
    public UserResponse updateUser(Long id, UserRequest request) {
        User existing = repo.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with id: " + id));

        existing.setName(request.getName());
        existing.setEmail(request.getEmail());
        existing.setAddress(request.getAddress());
        existing.setPhone(request.getPhone());
        existing.setDob(request.getDob());

        User saved = repo.save(existing);
        return mapper.map(saved, UserResponse.class);
    }

    @Override
    public void deleteUser(Long id) {
        if (!repo.existsById(id)) {
            throw new ResourceNotFoundException("User not found with id: " + id);
        }
        repo.deleteById(id);
    }
}