package pl.deska.springbootsecurityimplementation.service.encoder;

import org.springframework.security.crypto.password.PasswordEncoder;

public class CaeserPasswordEncoder implements PasswordEncoder {
    @Override
    public String encode(CharSequence rawPassword) {
        int shift = 10;
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < rawPassword.length(); i++) {
            if (Character.isUpperCase(rawPassword.charAt(i))) {
                char ch = (char) (((int) rawPassword.charAt(i) +
                        shift - 65) % 26 + 65);
                result.append(ch);
            } else {
                char ch = (char) (((int) rawPassword.charAt(i) +
                        shift - 97) % 26 + 97);
                result.append(ch);
            }
        }
        return result.toString();
    }

    @Override
    public boolean matches(CharSequence rawPassword, String encodedPassword) {
        boolean result = false;
        if(encode(rawPassword).equals(encodedPassword))
            result = true;
        return result;
    }
}
