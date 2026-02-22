package tn.enicar.eniconnect.dto;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

/**
 * DTO pour la connexion — correspond au formulaire Angular login.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class LoginRequest {

    @NotBlank(message = "L'email est obligatoire")
    @Email
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    private String password;
}
