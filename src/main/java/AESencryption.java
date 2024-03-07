import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESencryption {

    private static final String ALGORITHM = "AES";

    public static String encrypt(String plaintext, String secretKey, String mode) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + mode + "/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);

        byte[] iv = generateIV();
        cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));

        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        String encryptedText = Base64.getEncoder().encodeToString(encryptedBytes);

        return Base64.getEncoder().encodeToString(iv) + ":" + encryptedText;
    }

    public static String decrypt(String ciphertext, String secretKey, String mode) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + mode + "/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);

        String[] parts = ciphertext.split(":");
        byte[] iv = Base64.getDecoder().decode(parts[0]);
        byte[] encryptedBytes = Base64.getDecoder().decode(parts[1]);

        cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));

        byte[] decryptedBytes = cipher.doFinal(encryptedBytes);
        return new String(decryptedBytes);
    }

    private static byte[] generateIV() {
        byte[] iv = new byte[16];
        new java.security.SecureRandom().nextBytes(iv);
        return iv;
    }
}
