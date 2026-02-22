package tn.enicar.eniconnect.dto;

import lombok.*;

/**
 * Réponse générique pour les API (succès/erreur).
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class MessageResponse {

    private String message;
    private boolean success;

    public MessageResponse(String message) {
        this.message = message;
        this.success = true;
    }
}
