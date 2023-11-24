import java.util.Scanner;

class CaesarCipher {

    public static final String lower = "abcdefghijklmnopqrstuvwxyz";
    public static final String upper = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";

    static String encryption(String plainTxt, int key){
        String encrTxt="";
        for(int i = 0; i < plainTxt.length(); i++){
            char ch = plainTxt.charAt(i);
            if(plainTxt.charAt(i) >= 'a' && plainTxt.charAt(i) <= 'z'){
                int charPos = lower.indexOf(ch);
                int newPos = (charPos+key) % 26;
                encrTxt += (char)(lower.charAt(newPos));
            }
            else if(plainTxt.charAt(i) >= 'A' && plainTxt.charAt(i) <= 'Z'){
                 int charPos = upper.indexOf(ch);
                int newPos = (charPos+key) % 26;
                encrTxt += (char)(upper.charAt(newPos));
            }
            else {
                encrTxt += plainTxt.charAt(i);
            }
        }
        return encrTxt;
    }

     static String decryption(String encrTxt, int key){
        String decrTxt="";
        for(int i = 0; i < encrTxt.length(); i++){
            char ch = encrTxt.charAt(i);
            if(ch >= 'a' && ch <= 'z'){
                int charPos = lower.indexOf(ch);
                int newPos = (charPos-key) % 26;
                if(newPos < 0)
                    newPos = newPos + 26;
                decrTxt += (char)(lower.charAt(newPos));
            }
            else if(ch >= 'A' && ch <= 'Z'){
                int charPos = upper.indexOf(ch);
                int newPos = (charPos-key) % 26;
                if(newPos < 0)
                    newPos = newPos + 26;
                decrTxt += (char)(upper.charAt(newPos));
            }
            else {
                decrTxt += encrTxt.charAt(i);
            }
        }
        return decrTxt;
    }

    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        String plainTxt;
        int key;
        System.out.print("\nEnter plain text: ");
        plainTxt = sc.nextLine();
        System.out.print("\nEnter key: ");
        key = sc.nextInt();
       String cipher = encryption(plainTxt, key);
        System.out.println("\nCipher Text: "+cipher);
        System.out.println("\nPlaint Text: "+decryption(cipher, key));
        sc.close();
    }
}