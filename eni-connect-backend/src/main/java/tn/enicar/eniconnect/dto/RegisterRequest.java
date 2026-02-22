package tn.enicar.eniconnect.dto;

import lombok.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

/**
 * DTO pour l'inscription — correspond au formulaire Angular register.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class RegisterRequest {

    @NotBlank(message = "Le prénom est obligatoire")
    @Size(max = 50)
    private String firstName;

    @NotBlank(message = "Le nom est obligatoire")
    @Size(max = 50)
    private String lastName;

    @NotBlank(message = "L'email est obligatoire")
    @Email(message = "Format email invalide")
    private String email;

    @NotBlank(message = "Le mot de passe est obligatoire")
    @Size(min = 6, message = "Le mot de passe doit contenir au moins 6 caractères")
    private String password;

    private String role; // "student", "teacher", "alumni", etc.

    private String filiere;

    private String promotion;
}
