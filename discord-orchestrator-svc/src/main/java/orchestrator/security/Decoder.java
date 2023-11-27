package orchestrator.security;

import orchestrator.security.jwt.excpetion.TokenException;
import org.springframework.security.core.userdetails.UserDetails;

public interface Decoder<T extends UserDetails> {

    T decode(String token) throws TokenException;
}
