package asseco.praksa.OnlineBank.auth;

import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.security.crypto.encrypt.TextEncryptor;
import org.springframework.stereotype.Service;

@Service
public class EncryptionUtil {
    @Value("${app.secretKey}")
    private String password;

    @Value("${app.salt}")
    private String salt;
    private static TextEncryptor encryptor;

    @PostConstruct
    private void init() {
        this.encryptor = Encryptors.text(password, salt);
    }

    public static String encrypt(String data) {
        return encryptor.encrypt(data);
    }

    public static String decrypt(String encryptedData) {
        return encryptor.decrypt(encryptedData);
    }
}
