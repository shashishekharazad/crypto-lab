import java.util.Scanner;

public class RSAAlgorithm {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter first prime number P: ");
        long p = sc.nextInt();
        while (!isPrime(p)) {
            System.out.print(p + " is not a prime number, Enter prime number: ");
            p = sc.nextLong();
        }
        System.out.print("Enter second prime number q: ");
        long q = sc.nextInt();
        while (!isPrime(q) || (p == q)) {
            System.out.print(q + " is not a prime number, Enter prime number: ");
            q = sc.nextLong();
        }
        long n = p * q;
        long phi_n = (p - 1) * (q - 1);

        System.out.print("Enter a public key e such that it is co prime of phi n and 1 < e < phi_n: ");
        long e = sc.nextInt();
        if (!areCoprime(e, phi_n)) {
            System.out.print("Please select correct public key: ");
            return;
        }
        long d = mult_inverse(e, phi_n);
        System.out.println("Public Key: (" + n + " " + e+")");
        System.out.println("Public Key: (" + n + " " + d+")");

        System.out.print("Enter a message to encrypt");
        // to eat blank space
        sc.nextLine();
        byte message[] = sc.nextLine().getBytes();
        long encrypted[] = new long[message.length];
        long decrypted[] = new long[message.length];

        for (int i = 0; i < message.length; i++) {
            long ct = encrypt(message[i], e, n);
            encrypted[i] = ct;
        }
        System.out.print("Encrypted text is :");
        for (long en : encrypted) {
            System.out.print(en + " ");
        }
        for (int i = 0; i < message.length; i++) {
            long ptcheck = decrypt(encrypted[i], d, n);
            decrypted[i] = ptcheck;
        }

        System.out.print("\nDecrypted text is :");
        for (long dc : decrypted) {
            System.out.print((char) dc);
        }
    }

    private static long decrypt(long ct, long d, long n) {
        return modPow(ct, d, n);
    }

    private static long encrypt(long pt, long e, long n) {
        return modPow(pt, e, n);
    }

    static boolean areCoprime(long a, long b) {
        return gcd(a, b) == 1;
    }

    static long gcd(long a, long b) {
        while (b != 0) {
            long temp = b;
            b = a % b;
            a = temp;
        }
        return a;
    }

    public static long mult_inverse(long a, long b) {
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

    static long modPow(long base, long exponent, long modulo) {
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
}