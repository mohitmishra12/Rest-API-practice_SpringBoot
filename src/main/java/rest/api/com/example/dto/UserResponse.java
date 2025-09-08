package rest.api.com.example.dto;

import lombok.*;
import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String name;
    private String email;
    private String address;
    private String phone;
    private String dob;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
}