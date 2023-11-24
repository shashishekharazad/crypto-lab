import java.math.BigInteger;

public class test {

    public static void main(String[] args) {
        // Given values
        BigInteger e1 = new BigInteger("6968"); // Replace with actual value
        BigInteger e2 = new BigInteger("2038"); // Replace with actual value
        BigInteger m = new BigInteger("5000");  // Replace with actual value
        BigInteger S1 = new BigInteger("18"); // Replace with actual value
        BigInteger S2 = new BigInteger("95"); // Replace with actual value
        BigInteger p = new BigInteger("8081");  // Replace with actual value
        BigInteger q = new BigInteger("101");   // Replace with actual value

        // Calculate e1^(m * S1) mod p
        BigInteger part1 = e1.modPow(m.multiply(S1), p);

        // Calculate S2^-1 mod q
        BigInteger S2Inverse = S2.modInverse(q);

        // Calculate e2^(S1 * S2^-1) mod p
        BigInteger part2 = e2.modPow(S1.multiply(S2Inverse), p);

        // Calculate the final result: (e1^(m * S1) * e2^(S1 * S2^-1)) mod p mod q
        BigInteger result = part1.multiply(part2).mod(p).mod(q);

        System.out.println("Result: " + result);
    }
}
