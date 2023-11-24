import java.util.Scanner;

public class PlayFair {

    private String[][] playFairTable;
    private int length = 0;

    private String[][] cipherMatrix(String key) {
        String[][] playfairTable = new String[5][5];
        String keyString = key + "ABCDEFGHIKLMNOPQRSTUVWXYZ";

        // fill string array with empty string
        for (int i = 0; i < 5; i++)
            for (int j = 0; j < 5; j++)
                playfairTable[i][j] = "";

        for (int k = 0; k < keyString.length(); k++) {
            boolean repeat = false;
            boolean used = false;
            for (int i = 0; i < 5; i++) {
                for (int j = 0; j < 5; j++) {
                    if (playfairTable[i][j].equals("" + keyString.charAt(k))) {
                        repeat = true;
                    } else if (playfairTable[i][j].equals("") && !repeat && !used) {
                        playfairTable[i][j] = "" + keyString.charAt(k);
                        used = true;
                    }
                }
            }
        }
        return playfairTable;
    }

    private String encrypt(String plainTxt) {
        String output = "";
        length = (int) plainTxt.length() / 2 + plainTxt.length() % 2;

        // insert x between double-letter digraphs & redefines "length"
        for (int i = 0; i < length - 1; i++) {
            if (plainTxt.charAt(2 * i) == plainTxt.charAt(2 * i + 1)) {
                plainTxt = new StringBuffer(plainTxt).insert(2 * i + 1, 'X').toString();
                length = (int) plainTxt.length() / 2 + plainTxt.length() % 2;
            }
        }

        // insert an X to the last digraph (char pair), if necessary
        String[] digraph = new String[length];
        for (int j = 0; j < length; j++) {
            if (j == (length - 1) && plainTxt.length() / 2 == (length - 1))
                plainTxt = plainTxt + "X";
            digraph[j] = plainTxt.charAt(2 * j) + "" + plainTxt.charAt(2 * j + 1);
        }
        // Encrypt the message
        String[] encDigraphs = new String[length];
        //encDigraphs = encodeDigraph(digraph);
        for (int k = 0; k < length; k++)
            output = output + encDigraphs[k];
        
        return output;
    }

    private String encodeDigraph(String diagraph){
        String output = "";

        return output;
    }

    public static void main(String[] arhs) {
        Scanner sc = new Scanner(System.in);
        System.out.print("\nEnter Plain Text: ");
        String plainTxt = sc.nextLine();
        System.out.print("\nEnter the Key: ");
        String key = sc.nextLine();

        PlayFair pf = new PlayFair();

        pf.playFairTable = pf.cipherMatrix(key);

        for (int i = 0; i < 5; i++) {
            for (int j = 0; j < 5; j++) {
                System.out.print(pf.playFairTable[i][j] + " ");
            }
            System.out.println();
        }

        String output = pf.encrypt(plainTxt);

    }
}
