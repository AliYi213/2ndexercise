import java.util.Scanner;

public class EncryptApp {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.print("Select operation (1 for encryption, 2 for decryption): ");
        int operation = scanner.nextInt();
        scanner.nextLine();

        try {
            String result;
            if (operation == 1) {

                System.out.print("Enter plaintext: ");
                String plaintext = scanner.nextLine();
                System.out.print("Enter secret key: ");
                String secretKey = padKey(scanner.nextLine());
                System.out.print("Enter block cipher mode (ECB, CBC, CFB): ");
                String mode = scanner.nextLine();
                result = performEncryption(plaintext, secretKey, mode);
            } else if (operation == 2) {

                System.out.print("Do you want to read ciphertext from a file (Y) or enter ciphertext manually (N)? (Y/N): ");
                String readFromFileChoice = scanner.nextLine();

                if (readFromFileChoice.equalsIgnoreCase("Y")) {

                    System.out.print("Enter filename with ciphertext: ");
                    String filename = scanner.nextLine();
                    System.out.print("Enter secret key: ");
                    String secretKey = padKey(scanner.nextLine());
                    System.out.print("Enter block cipher mode (ECB, CBC, CFB): ");
                    String mode = scanner.nextLine();
                    String ciphertext = Operations.readFromFile(filename);
                    result = performDecryption(ciphertext, secretKey, mode);
                } else if (readFromFileChoice.equalsIgnoreCase("N")) {

                    System.out.print("Enter ciphertext to decrypt: ");
                    String ciphertext = scanner.nextLine();
                    System.out.print("Enter secret key: ");
                    String secretKey = padKey(scanner.nextLine());
                    System.out.print("Enter block cipher mode (ECB, CBC, CFB): ");
                    String mode = scanner.nextLine();
                    result = performDecryption(ciphertext, secretKey, mode);
                } else {
                    System.err.println("Invalid choice for reading from file or entering ciphertext manually.");
                    return;
                }
            } else {
                System.err.println("Invalid operation selected");
                return;
            }

            System.out.println("Result: " + result);

        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        } finally {
            scanner.close();
        }
    }

    private static String performEncryption(String plaintext, String secretKey, String mode) throws Exception {
        String ciphertext = AESencryption.encrypt(plaintext, secretKey, mode);
        System.out.println("Encrypted text: " + ciphertext);

        System.out.print("Enter filename to save encrypted text: ");
        String filename = new Scanner(System.in).nextLine();
        Operations.writeToFile(filename, ciphertext);

        System.out.println("Reading encrypted text from file: ");
        String readCiphertext = Operations.readFromFile(filename);
        System.out.println("Encrypted text from file: " + readCiphertext);

        return readCiphertext;
    }

    private static String performDecryption(String ciphertext, String secretKey, String mode) throws Exception {
        if (ciphertext == null || secretKey == null || mode == null) {
            throw new IllegalArgumentException("Decryption parameters are missing.");
        }

        String decryptedText = AESencryption.decrypt(ciphertext, secretKey, mode);

        return decryptedText;
    }

    private static String padKey(String key) {

        int length = 16;
        if (key.length() < length) {
            while (key.length() < length) {
                key += "0";
            }
        } else if (key.length() > length) {
            key = key.substring(0, length);
        }
        return key;
    }
}