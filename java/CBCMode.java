import java.util.*;

public class CBCMode {
    public static void main(String args[]) {
        String iv = "00000000000000000000000000000000000000000000000000000000";
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter a plain text, you want to encrypt: ");
        String message = sc.nextLine();
        int req = message.length() % 8;
        if (req != 0) {
            int padd = 8 - req;
            message += "X".repeat(padd);
        }
        System.out.println("Plain text after padding: " + message);
        System.out.println("Enter a key of 8 byte: ");
        String key = sc.nextLine();
        BlockCipherDES dfm = new BlockCipherDES();
        String str = "";
        String encrypted = "";
        int j = 0;
        for (int i = 0; i <= message.length() - 8; i += 8) {
            if (i == 0) {
                str = dfm.xorStrings(iv.substring(0, 8), message.substring(i, i + 8), false);
            } else {
                str = dfm.xorStrings(encrypted.substring(i, i + 8), message.substring(i, i + 8), false);
            }
            encrypted = dfm.des(str, key, false);
        }

        System.out.println("\nEncrypted in the form of binary\n" + encrypted);
        String decrypted = "";
        j = 0;
        for (int i = 0; i <= encrypted.length() - 64; i += 64) {
            if (i == 0) {
                str = dfm.xorStrings(iv, encrypted.substring(i, i + 64), true);
            } else {
                str = dfm.xorStrings(decrypted.substring(j, j + 64), encrypted.substring(i, i + 64), false);
            }
            decrypted += dfm.des(decrypted.substring(i, i + 64), key, true);
            j++;
        }
        System.out.println("\nDecrypted text: \n" + decrypted);
    }
}