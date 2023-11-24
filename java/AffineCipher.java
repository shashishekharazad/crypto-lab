import java.util.Scanner;

class AffineCipher {
  static int k1_inverse(int n) {
    int a, b, q, r, t1, t2, t;
    a = 26;
    b = n;
    t1 = 0;
    t2 = 1;
    System.out.println("\nq  a  b  r  t1  t2  t");
    while (b != 0) {
      q = a / b;
      r = a % b;
      t = t1 - q * t2;
      System.out.println(q + "  " + a + "  " + b + "  " + r + "  " + t1 + "  " + t2 + "  " + t);
      a = b;
      b = r;
      t1 = t2;
      t2 = t;
    }
    if (t1 < 0)
      return t1 + 26;
    return t1;
  }

  static String encryptPt(char[] pt, int k1, int k2) {
    String cipher = "";
    for (int i = 0; i < pt.length; i++) {
      if (pt[i] != ' ') {
        cipher = cipher + (char) (((k1 * (pt[i] - 'A') + k2) % 26) + 'A');
      } else
        cipher += pt[i];

    }
    return cipher;
  }

  static String decryptCt(char[] cText, int k1_inv, int k2) {
    String pt = "";
    for (int i = 0; i < cText.length; i++) {
      if (cText[i] != ' ') {
        pt = pt + (char) (((k1_inv * ((cText[i] + 'A' - k2)) % 26)) + 'A');
      } else
        pt += cText[i];
    }
    return pt;
  }

  static boolean checkCoPrime(int n) {
    if (n > 0 || n < 26) {
      if (n % 2 != 0 && 26 % n != 0)
        return true;
    }
    return false;
  }

  public static void main(String args[]) {
    Scanner sc = new Scanner(System.in);
    String pt;
    String ct;
    String decTxt;
    int k1;
    int k2;
    int k1_inv = 0;
    System.out.print("\nEnter plain text: ");
    pt = sc.nextLine();
    System.out.print("\nEnter key k1: ");
    k1 = sc.nextInt();
    while (!checkCoPrime(k1)) {
      System.out.print("\nInvalid key, enter valid key: ");
      k1 = sc.nextInt();
    }
    System.out.print("\nEnter key k2: ");
    k2 = sc.nextInt();
    k1_inv = k1_inverse(k1);
    System.out.println("\nInverse of " + k1 + " = " + k1_inv);
    ct = encryptPt(pt.toCharArray(), k1, k2);
    System.out.println("\nCipher Text: " + ct);
    decTxt = decryptCt(ct.toCharArray(), k1_inv, k2);
    System.out.println("\nPlaint Text: " + decTxt + "\n");
    sc.close();
  }
}