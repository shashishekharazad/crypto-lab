import java.util.BitSet;
import java.util.Scanner;

public class DESCipher {

    static final int[] initialPermutation = { 58, 50, 42, 34, 26, 18, 10, 2, 60, 52, 44, 36,
            28, 20, 12, 4, 62, 54, 46, 38, 30, 22, 14, 6, 64, 56, 48, 40, 32,
            24, 16, 8, 57, 49, 41, 33, 25, 17, 9, 1, 59, 51, 43, 35, 27, 19,
            11, 3, 61, 53, 45, 37, 29, 21, 13, 5, 63, 55, 47, 39, 31, 23, 15, 7 };

    static final int[] finalPermutationTable = { 40, 8, 48, 16, 56, 24, 64, 32,
            39, 7, 47, 15, 55, 23, 63, 31,
            38, 6, 46, 14, 54, 22, 62, 30,
            37, 5, 45, 13, 53, 21, 61, 29,
            36, 4, 44, 12, 52, 20, 60, 28,
            35, 3, 43, 11, 51, 19, 59, 27,
            34, 2, 42, 10, 50, 18, 58, 26,
            33, 1, 41, 9, 49, 17, 57, 25 };

    static final int[][][] sBoxTable = {
            {
                    { 14, 4, 13, 1, 2, 15, 11, 8, 3, 10, 6, 12, 5, 9, 0, 7 },
                    { 0, 15, 7, 4, 14, 2, 13, 1, 10, 6, 12, 11, 9, 5, 3, 8 },
                    { 4, 1, 14, 8, 13, 6, 2, 11, 15, 12, 9, 7, 3, 10, 5, 0 },
                    { 15, 12, 8, 2, 4, 9, 1, 7, 5, 11, 3, 14, 10, 0, 6, 13 }
            },
            {
                    { 15, 1, 8, 14, 6, 11, 3, 4, 9, 7, 2, 13, 12, 0, 5, 10 }, // S2
                    { 3, 13, 4, 7, 15, 2, 8, 14, 12, 0, 1, 10, 6, 9, 11, 5 },
                    { 0, 14, 7, 11, 10, 4, 13, 1, 5, 8, 12, 6, 9, 3, 2, 15 },
                    { 13, 8, 10, 1, 3, 15, 4, 2, 11, 6, 7, 12, 0, 5, 14, 9 }
            },

            {
                    { 10, 0, 9, 14, 6, 3, 15, 5, 1, 13, 12, 7, 11, 4, 2, 8 }, // S3
                    { 13, 7, 0, 9, 3, 4, 6, 10, 2, 8, 5, 14, 12, 11, 15, 1 },
                    { 13, 6, 4, 9, 8, 15, 3, 0, 11, 1, 2, 12, 5, 10, 14, 7 },
                    { 1, 10, 13, 0, 6, 9, 8, 7, 4, 15, 14, 3, 11, 5, 2, 12 }
            },
            {
                    { 7, 13, 14, 3, 0, 6, 9, 10, 1, 2, 8, 5, 11, 12, 4, 15 }, // S4
                    { 13, 8, 11, 5, 6, 15, 0, 3, 4, 7, 2, 12, 1, 10, 14, 9 },
                    { 10, 6, 9, 0, 12, 11, 7, 13, 15, 1, 3, 14, 5, 2, 8, 4 },
                    { 3, 15, 0, 6, 10, 1, 13, 8, 9, 4, 5, 11, 12, 7, 2, 14 }
            },
            {
                    { 2, 12, 4, 1, 7, 10, 11, 6, 8, 5, 3, 15, 13, 0, 14, 9 }, // S5
                    { 14, 11, 2, 12, 4, 7, 13, 1, 5, 0, 15, 10, 3, 9, 8, 6 },
                    { 4, 2, 1, 11, 10, 13, 7, 8, 15, 9, 12, 5, 6, 3, 0, 14 },
                    { 11, 8, 12, 7, 1, 14, 2, 13, 6, 15, 0, 9, 10, 4, 5, 3 }
            },
            {
                    { 12, 1, 10, 15, 9, 2, 6, 8, 0, 13, 3, 4, 14, 7, 5, 11 }, // S6
                    { 10, 15, 4, 2, 7, 12, 9, 5, 6, 1, 13, 14, 0, 11, 3, 8 },
                    { 9, 14, 15, 5, 2, 8, 12, 3, 7, 0, 4, 10, 1, 13, 11, 6 },
                    { 4, 3, 2, 12, 9, 5, 15, 10, 11, 14, 1, 7, 6, 0, 8, 13 }
            },
            {
                    { 4, 11, 2, 14, 15, 0, 8, 13, 3, 12, 9, 7, 5, 10, 6, 1 }, // S7
                    { 13, 0, 11, 7, 4, 9, 1, 10, 14, 3, 5, 12, 2, 15, 8, 6 },
                    { 1, 4, 11, 13, 12, 3, 7, 14, 10, 15, 6, 8, 0, 5, 9, 2 },
                    { 6, 11, 13, 8, 1, 4, 10, 7, 9, 5, 0, 15, 14, 2, 3, 12 }
            },
            {
                    { 13, 2, 8, 4, 6, 15, 11, 1, 10, 9, 3, 14, 5, 0, 12, 7 }, // S8
                    { 1, 15, 13, 8, 10, 3, 7, 4, 12, 5, 6, 11, 0, 14, 9, 2 },
                    { 7, 11, 4, 1, 9, 12, 14, 2, 0, 6, 10, 13, 15, 3, 5, 8 },
                    { 2, 1, 14, 7, 4, 10, 8, 13, 15, 12, 9, 0, 3, 5, 6, 11 }
            } };

    static final int[] PC1 = {
            57, 49, 41, 33, 25, 17, 9,
            1, 58, 50, 42, 34, 26, 18,
            10, 2, 59, 51, 43, 35, 27,
            19, 11, 3, 60, 52, 44, 36,
            63, 55, 47, 39, 31, 23, 15,
            7, 62, 54, 46, 38, 30, 22,
            14, 6, 61, 53, 45, 37, 29,
            21, 13, 5, 28, 20, 12, 4 };

    static final int[] PC2 = {
            14, 17, 11, 24, 1, 5,
            3, 28, 15, 6, 21, 10,
            23, 19, 12, 4, 26, 8,
            16, 7, 27, 20, 13, 2,
            41, 52, 31, 37, 47, 55,
            30, 40, 51, 45, 33, 48,
            44, 49, 39, 56, 34, 53,
            46, 42, 50, 36, 29, 32 };

    static final int[] expantionTable = {
            32, 1, 2, 3, 4, 5,
            4, 5, 6, 7, 8, 9,
            8, 9, 10, 11, 12, 13,
            12, 13, 14, 15, 16, 17,
            16, 17, 18, 19, 20, 21,
            20, 21, 22, 23, 24, 25,
            24, 25, 26, 27, 28, 29,
            28, 29, 30, 31, 32, 1
    };

    static final int straightPbox[] = {
            16, 7, 20, 21,
            29, 12, 28, 17,
            1, 15, 23, 26,
            5, 18, 31, 10,
            2, 8, 24, 14,
            32, 27, 3, 9,
            19, 13, 30, 6,
            22, 11, 4, 25
    };

    static final int[] leftShift = { 1, 1, 2, 2, 2, 2, 2, 2, 1, 2, 2, 2, 2, 2, 2, 1 };

    static String[] roundKey = new String[16];

    private static String[] keyGeneration(String binaryKey) {
        String[] roundKey = new String[16];
        binaryKey = permute(binaryKey, PC1);
        String leftHalf = binaryKey.substring(0, 28);
        String rightHalf = binaryKey.substring(28);
        for (int i = 0; i < 16; i++) {

            leftHalf = circularLeftShift(leftHalf, i);
            rightHalf = circularLeftShift(rightHalf, i);

            String combinedHalf = leftHalf + rightHalf;
            roundKey[i] = permute(combinedHalf, PC2);
        }

        return roundKey;
    }

    private static String circularLeftShift(String input, int shift) {
        int length = input.length();
        shift = shift % length;
        return input.substring(shift) + input.substring(0, shift);
    }

    private static String permute(String input, int[] permutationTable) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < permutationTable.length; i++) {
            result.append(input.charAt(permutationTable[i] - 1));
        }
        return result.toString();
    }

    // Conversiont of binary to a BitSet string
    private static BitSet binaryToBitSet(String binaryKey) {
        BitSet bitSet = new BitSet(binaryKey.length());
        for (int i = 0; i < binaryKey.length(); i++) {
            if (binaryKey.charAt(i) == '1') {
                bitSet.set(i);
            }
        }
        return bitSet;
    }

    // Conversiont of BitSet to a binary string
    private static String bitSetToBinary(BitSet bitSet) {
        StringBuilder binaryString = new StringBuilder();
        for (int i = 0; i < bitSet.length(); i++) {
            binaryString.append(bitSet.get(i) ? '1' : '0');
        }
        return binaryString.toString();
    }

    private static String initialPermutation(String plainTxt) {
        BitSet messageBits = binaryToBitSet(plainTxt);
        int ipSize = initialPermutation.length;
        BitSet permutedBits = new BitSet(ipSize);

        // Perform the permutation
        for (int i = 0; i < ipSize; i++) {
            // Get the index of the bit from the permutation table
            int permutedIndex = initialPermutation[i];

            // Set the bit at the permuted index in the result BitSet
            if (messageBits.get(permutedIndex)) {
                permutedBits.set(i);
            }
        }
        return bitSetToBinary(permutedBits);
    }

    private static String finalPermutation(String inputText) {
        BitSet inputBits = binaryToBitSet(inputText);
        int permutedSize = finalPermutationTable.length;
        BitSet permutedText = new BitSet(permutedSize);

        for (int i = 0; i < permutedSize; i++) {
            int permutedIndex = finalPermutationTable[i];
            if (inputBits.get(permutedIndex)) {
                permutedText.set(i);
            }
        }
        return bitSetToBinary(permutedText);
    }

    /// Expantion permutation of right half plain text
    private static String expandRightHalf(String rightHalf) {
        BitSet rightPT = binaryToBitSet(rightHalf);
        int blockSize = 48;
        BitSet expandedrightPT = new BitSet(blockSize);

        for (int i = 0; i < blockSize; i++) {
            int expandedIndex = expantionTable[i];
            if (rightPT.get(expandedIndex)) {
                expandedrightPT.set(i);
            }
        }

        return bitSetToBinary(expandedrightPT);
    }

    private static String substitutionBox(String expandedString) {

        String substibutedString = "";
        int k = 0;
        for(int i = 0; i < expandedString.length()/6; i++){
            String str = expandedString.substring(0, 6);
            String rowS = str.substring(0, 1) + str.substring(5, 6);
            String colS = str.substring(1, 5);
            int row = Integer.parseInt(rowS,2);
            int col = Integer.parseInt(colS,2);

            int sBoxValue = sBoxTable[i][row][col];
            String binary = Integer.toBinaryString(sBoxValue);

            while (binary.length() < 4) {
                binary = "0" + binary;
            }
            substibutedString += binary;
            k += 6;
        }
        return substibutedString;
    }

    private static String straightPermutation(String substituted) {

        System.out.println("S P"+substituted);
        char[] substitutedTxt = substituted.toCharArray();
        char[] permutedBits = substituted.toCharArray();
        String permuted = "";
        int straightPboxLen = straightPbox.length;
        
        for (int i = 0; i < straightPboxLen; i++) {
          permutedBits[straightPbox[i]-1] = substitutedTxt[i];
        }

        for(char ch : permutedBits)
            permuted += ch;

        return permuted;
    }

    private static String xorOperation(String rightPT, String key) {
        int length = rightPT.length();
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < length; i++) {
            char bit1 = rightPT.charAt(i);
            char bit2 = key.charAt(i);

            // XOR the bits and append the result
            char resultBit = (bit1 == bit2) ? '0' : '1';
            result.append(resultBit);
        }

        return result.toString();
    }

    /// Encryption
    public static String encryption(String binaryMessage, String[] binaryKey) {
        String cipherText = "";
        String plainText = initialPermutation(binaryMessage);
        String rightPT = plainText.substring(0, 32);
        String leftPT = plainText.substring(32);

        String temp ="";
        // Perform 16 rounds of DES encryption on 64 bit plain text
        for (int round = 0; round < 16; round++) {

            /// Expansion
            String expandedPt = expandRightHalf(rightPT);

            /// Function (Key, RPT)
            String xoredRes = xorOperation(expandedPt, binaryKey[round]);

            /// S-Box Substitute
            String substituteText = substitutionBox(xoredRes);

            /// Stright P Box permutation
            String straightPermuted = straightPermutation(substituteText);
            
            /// LeftPT XOR RightPT
            temp = xorOperation(leftPT, straightPermuted);

            /// Swapping => RPT <=>LPT
            if (round != 15) {
                leftPT = rightPT;
                rightPT = temp;
            }
        }

        // Perform the final permutation (IP^-1)
        cipherText = finalPermutation(leftPT.concat(rightPT));
        return cipherText;
    }

    /// Decryption
    public void decryption() {

    }

    private static String stringToBinary(String input) {
        byte[] bytes = input.getBytes();
        StringBuilder binaryString = new StringBuilder();

        for (byte b : bytes) {
            String binary = Integer.toBinaryString(b & 0xFF);
            binaryString.append(String.format("%8s", binary).replace(' ', '0'));
        }

        return binaryString.toString();

    }

    private static String binaryToString(String binaryString) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < binaryString.length(); i += 8) {
            String binaryByte = binaryString.substring(i, i + 8);
            int decimalValue = Integer.parseInt(binaryByte, 2);
            result.append((char) decimalValue);
        }

        return result.toString();
    }

    public static void main(String[] args) {
        String cipherTxt = "";
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter 8 byte (64-bit) key: ");
        String key = sc.nextLine();
        System.out.print("Enter plaint text: ");
        String plainTxt = sc.nextLine();

        String binaryText = stringToBinary(plainTxt);

        /// Add fillers to make the plain text multiple of 64
        if (binaryText.length() % 64 != 0) {
            int n = binaryText.length() % 64;
            for (int i = 0; i < (64 - n); i++) {
                binaryText += '0';
            }
        }

        String binaryKey = stringToBinary(key);

        roundKey = keyGeneration(binaryKey);

        int k = 0;
        for (int i = 0; i < binaryText.length() / 64; i++) {
            cipherTxt += encryption(binaryText.substring(k, (k + 64)), roundKey);
            k += 64;
        }

        System.out.println("64 bit Binary Key: " + binaryKey);
        System.out.println("Cipher Text in binary: " + cipherTxt);
        System.out.println("Cipher Text in plain text: " + binaryToString(cipherTxt));

        sc.close();
    }
}
