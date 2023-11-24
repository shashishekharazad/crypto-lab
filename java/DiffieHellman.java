import java.util.Scanner;
public class DiffieHellman {
    public static void main(String args[]) {
        Scanner sc = new Scanner(System.in);
        System.out.println("Enter two large prime number: ");
        System.out.print("n: ");
        int n = sc.nextInt();
        while (!isPrime(n)) {
            System.out.print(n + " is not a prime number, Enter prime number: ");
            n = sc.nextInt();
        }
        System.out.print("g: ");
        int g = sc.nextInt();
        while (!isPrime(g)) {
            System.out.print(g + " is not a prime number, Enter prime number: ");
            g = sc.nextInt();
        }
        System.out.print("Alice, Enter a random number x: ");
        int x = sc.nextInt();
        System.out.print("Bob, Enter a random number y: ");
        int y = sc.nextInt();
        long A = powerMod(g, x, n);
        long B = powerMod(g, y, n);
        long K1 = powerMod(B, x, n);
        long K2 = powerMod(A, y, n);
        System.out.println("Alice and Bob's shared public key is");
        System.out.println("Alice's calculated key K1: " + K1);
        System.out.println("Bob's calculated key K2: " + K2);
        sc.close();
    }
    public static boolean isPrime(int n) {
        if (n <= 1) {
            return false;
        }
        for (int i = 2; i <= Math.sqrt(n); i++) {
            if (n % i == 0) {
                return false;
            }
        }
        return true;
    }

    public static long powerMod(long base, long exponent, long modulus) {
        long result = 1;
        while (exponent > 0) {
            if (exponent % 2 == 1) {
                result = (result * base) % modulus;
            }
            base = (base * base) % modulus;
            exponent /= 2;
        }
        return result;
    }
}
