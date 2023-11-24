import java.util.Scanner;

public class RSACode {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);
        long p = 0, q = 0, E = 2, D = 0;
        System.out.print("Enter a prime number p: ");
        p = sc.nextLong();
        while (!isPrime(p)) {
            System.out.print(p + " is not a prime number, Enter prime number: ");
            p = sc.nextLong();
        }

        System.out.print("Enter a prime number q: ");
        q = sc.nextLong();
        while (!isPrime(q) || (p == q)) {
            System.out.print(q + " is not a prime number or p == q, Enter another number: ");
            q = sc.nextLong();
        }

        long n = p * q;
        long phi = (p - 1) * (q - 1);

        System.out.print("Enter a public key e such that it is co prime of phi n and 1 < e < phi n: ");
        E = sc.nextLong();
        while (!areCoprime(E, phi)) {
            System.out.print("Please select correct public key: ");
            E = sc.nextLong();
        }

        if (multiplicativeInverse(phi, E) != -1) {
            D = multiplicativeInverse(phi, E);
        } else {
            System.out.println("Private key can not be generated");
        }
        sc.nextLine();

        System.out.println("Public Key: (" + E + " " + n + ")");
        System.out.println("Private Key: (" + D + " " + n + ")");

        System.out.print("Enter plain text: ");
        String inpuString = sc.nextLine();

        byte[] plainText = inpuString.getBytes();

        System.out.print("\nPlain Text in ASCII: ");
        for (int i = 0; i < plainText.length; i++) {
            System.out.print(plainText[i] + " ");
        }

        long[] cipherText = new long[plainText.length];
        long[] decipheredText = new long[plainText.length];

        for (int i = 0; i < plainText.length; i++) {
            cipherText[i] = power(plainText[i], E, n);
        }

        System.out.print("\nEncrypted Text in ASCII:\t");
        for (int i = 0; i < cipherText.length; i++) {
            System.out.print(cipherText[i] + " ");
        }

        for (int i = 0; i < cipherText.length; i++) {
            decipheredText[i] = power(cipherText[i], D, n);
        }

        System.out.print("\nDecrypted Text in ASCII:\t");
        for (int i = 0; i < decipheredText.length; i++) {
            System.out.print(decipheredText[i] +" ");
        }
        System.out.print("\nDecrypted Text:\t");
        for (int i = 0; i < decipheredText.length; i++) {
            System.out.print((char) decipheredText[i]);
        }

        System.out.println("\n");
        sc.close();
    }

    public static boolean isPrime(long n) {
        if (n <= 1) {
            return false;
        }
        for (long i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    static long multiplicativeInverse(long a, long b) {
        long min = Math.min(a, b);
        long max = Math.max(a, b);
        a = max;
        b = min;
        long t1 = 0, t2 = 1;
        long t = 0, q = 0, r = 1;
        while (r != 0) {
            q = a / b;
            r = a % b;
            t = t1 - (t2 * q);
            a = b;
            b = r;
            t1 = t2;
            t2 = t;
        }
        if (t1 < 0) {
            t1 += t;
        }
        return t1;
    }

    static boolean areCoprime(long a, long b) {
        return gcd(a, b) == 1;
    }

    static long gcd(long a, long b) {
        if (a == 0)
            return b;
        return gcd(b % a, a);
    }

    static long power(long base, long exponent, long modulo) {
        long result = 1;
        base = base % modulo;

        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulo;
            }

            exponent = exponent >> 1;
            base = (base * base) % modulo;
        }

        return result;
    }
}
