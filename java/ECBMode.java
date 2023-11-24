import java.util.*;

public class ECBMode {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a message that you want to encrypt: ");
        String message = sc.nextLine();
        int req = message.length() % 8;
        if (req != 0) {
            int padd = 8 - req;
            message += "X".repeat(padd);
        }
        System.out.println("New message after padding: " + message);
        System.out.println("Enter a key of 8 character: ");
        String key = sc.nextLine();
        BlockCipherDES dfm = new BlockCipherDES();
        String encrypted = "";
        for (int i = 0; i <= message.length() - 8; i += 8) {
            encrypted += dfm.des(message.substring(i, i + 8), key, false);
        }

        System.out.println("\nEncrypted in the form of binary\n" + encrypted);
        String decrypted = "";
        for (int i = 0; i <= encrypted.length() - 64; i += 64) {
            decrypted += dfm.des(encrypted.substring(i, i + 64), key, true);
        }
        System.out.println("\nDecrypted text: \n" + decrypted);

    }
}