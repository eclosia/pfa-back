package ma.eniad.pfaback.model;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {

    private String token;

    public AuthResponse(String token) {
        this.token = token;
    }
}
