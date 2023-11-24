import java.util.Scanner;

public class SHA1Hash {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String message = "";
        StringBuilder binaryMess = new StringBuilder();
        int padLen = 0;
        String mLen="";
        System.out.println("Enter the message: ");
        message = sc.nextLine();
        binaryMess = strToBinary(message);
        if ((binaryMess.length() + 64) % 512 == 0) {
            padLen = 512;
        } else {
            padLen = 512 - ((binaryMess.length() + 64) % 512);
        }
        System.out.println(binaryMess);
        binaryMess.append("1");
        for (int i = 0; i < padLen - 1; i++) {
            binaryMess.append("0");
        }
        mLen = Integer.toBinaryString(message.length());

        System.out.println(binaryMess);


        sc.close();
    }

    private static StringBuilder strToBinary(String s) {
        byte[] bytes = s.getBytes();
        StringBuilder binary = new StringBuilder();
        for (byte b : bytes) {
            int val = b;
            for (int i = 0; i < 8; i++) {
                binary.append((val & 128) == 0 ? 0 : 1);
                val <<= 1;
            }
        }
        return binary;
    }
}
