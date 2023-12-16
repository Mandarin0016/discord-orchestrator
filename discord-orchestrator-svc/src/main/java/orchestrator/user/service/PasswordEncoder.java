package orchestrator.user.service;

import orchestrator.user.exception.UserDomainException;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

@Component
public class PasswordEncoder {

    private final MessageDigest messageDigest;

    public PasswordEncoder(){
        try {
            messageDigest = MessageDigest.getInstance("SHA-512");
        } catch (NoSuchAlgorithmException e) {
            throw new UserDomainException("Unable to initialize class of type=[%s]".formatted(PasswordEncoder.class.getSimpleName()));
        }
    }

    public String getHashBase64(String plainText) {

        byte[] raw = messageDigest.digest(plainText.getBytes(StandardCharsets.UTF_8));
        return Base64.getEncoder().encodeToString(messageDigest.digest(raw));
    }
}
