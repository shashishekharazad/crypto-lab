import java.util.Scanner;

public class RSA {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        int n = 0, p = 0, q = 0, E = 2, D = 0;
        System.out.print("Enter a prime number p: ");
        p = sc.nextInt();
        while (!isPrime(p)) {
            System.out.print(p + " is not a prime number, Enter prime number: ");
            p = sc.nextInt();
        }

        System.out.print("Enter a prime number q: ");
        q = sc.nextInt();
        while (!isPrime(q)) {
            System.out.print(q + " is not a prime number or p = q, Enter another number: ");
            q = sc.nextInt();
        }
        if(p == q){
            System.out.print("P and Q are equal");
            return;
        }

        n = p * q;
        int phi = (p - 1) * (q - 1);

        System.out.print("Do you want to provide public key(y/n): ");
        char ch = sc.next().charAt(0);
        //Public key input
        if (ch == 'y' || ch == 'Y') {
            System.out.print("Enter public key E i.e. it is not the factor of (p-1) and (q-1): ");
            E = sc.nextInt();
            while (E % (p - 1) == 0 || E % (q - 1) == 0) {
                System.out.print(E + " is factor or (p-1) or (q-1), Enter another: ");
                E = sc.nextInt();
            }
        } else {
            // or E can be generated randomly
            phi = (p - 1) * (q - 1);
            while (E < phi) {
                if (gcd(E, phi) == 1)
                    break;
                else
                    E++;
            }
        }

        for (int i = 1; i < phi; i++) {
            if (((E * i) % phi) == 1) {
                D = i;
            }
        }

        System.out.print("Enter plain text: ");
        int plainText = sc.nextInt();

        

        int cipherText = (int)Math.pow(plainText, E) % n; 
        System.out.println("Cipher Text: " + cipherText);

        int decryptedText = (int)Math.pow(cipherText, D) % n; 
        System.out.println("Deciphered Text: " + decryptedText);

        sc.close();
    }

    static boolean isPrime(int n) {
        if (n == 1)
            return false;
        if (n == 2 || n == 3) {
            return true;
        }
        for (int i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) {
                return false;
            }
        }
        return true;
    }

    static int gcd(int a, int b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    static int multiplicativeInverse(int phi, int e) {
        int a, b, q, r, t1, t2, t;
        a = phi;
        b = e;
        t1 = 0;
        t2 = 1;
        while (b != 0) {
          q = a / b;
          r = a % b;
          t = t1 - q * t2;
          a = b;
          b = r;
          t1 = t2;
          t2 = t;
        }
        if (t1 < 0)
          return t1 + phi;
        return t1;
      }
}
