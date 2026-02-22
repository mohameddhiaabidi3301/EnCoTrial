package tn.enicar.eniconnect.dto;

import lombok.*;

/**
 * Réponse JWT après login/register réussi.
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class JwtResponse {

    private String token;
    private String type = "Bearer";
    private Long id;
    private String firstName;
    private String lastName;
    private String email;
    private String role;
    private String avatarUrl;
}
