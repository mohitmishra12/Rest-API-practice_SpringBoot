package rest.api.com.example.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import rest.api.com.example.model.User;

public interface UserRepository extends JpaRepository<User, Integer> {
}
