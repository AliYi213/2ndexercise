import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.util.Base64;

public class AESencryption {

    private static final String ALGORITHM = "AES";

    public static String encrypt(String plaintext, String secretKey, String mode) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + mode + "/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);

        if (mode.equals("CBC") || mode.equals("CFB")) {
            byte[] iv = generateIV();
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, new IvParameterSpec(iv));
        } else {
            cipher.init(Cipher.ENCRYPT_MODE, keySpec);
        }

        byte[] encryptedBytes = cipher.doFinal(plaintext.getBytes());
        return Base64.getEncoder().encodeToString(encryptedBytes);
    }

    public static String decrypt(String ciphertext, String secretKey, String mode) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM + "/" + mode + "/PKCS5Padding");
        SecretKeySpec keySpec = new SecretKeySpec(secretKey.getBytes(), ALGORITHM);

        if (mode.equals("CBC") || mode.equals("CFB")) {
            byte[] iv = generateIV();
            cipher.init(Cipher.DECRYPT_MODE, keySpec, new IvParameterSpec(iv));
        } else {
            cipher.init(Cipher.DECRYPT_MODE, keySpec);
        }

        byte[] decryptedBytes = cipher.doFinal(Base64.getDecoder().decode(ciphertext));
        return new String(decryptedBytes);
    }

    private static byte[] generateIV() {

        byte[] iv = new byte[16];
        new java.security.SecureRandom().nextBytes(iv);
        return iv;
    }
}
