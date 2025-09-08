package rest.api.com.example.dto;

import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserRequest {
    @NotBlank(message = "name is required")
    private String name;

    @NotBlank(message = "email is required")
    @Email(message = "email must be valid")
    private String email;

    private String address;

    @Pattern(regexp = "^[0-9+\\-() ]{7,20}$", message = "phone invalid")
    private String phone;

    private String dob;
}