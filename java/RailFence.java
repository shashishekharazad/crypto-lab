
import java.util.Scanner;

public class RailFence {

	private static String encryption(String plain, int key) {
		String encryptedText = "";
		int col = plain.length();
		int row = key;
		boolean check = false;
		int j = 0;
		char[][] rail = new char[row][col];

		for (int i = 0; i < row; i++) {
			for (int k = 0; k < col; k++)
				rail[i][k] = '*';
		}

		for (int i = 0; i < col; i++) {
			if (j == 0 || j == key - 1)
				check = !check;
			rail[j][i] = plain.charAt(i);

			if (check)
				j++;
			else
				j--;
		}

		for (int i = 0; i < row; i++) {
			for (int k = 0; k < col; k++) {
				char ch = rail[i][k];
				if (ch != '*')
					encryptedText += rail[i][k];
			}
		}
		return encryptedText;
	}

	private static String decryption(String encrypted, int key) {
		String decryptedText = "";

		int col = encrypted.length();
		int row = key;
		boolean check = false;
		int j = 0;
		char[][] rail = new char[row][col];

		// to fill the array:
		for (int i = 0; i < row; i++) {
			for (int k = 0; k < col; k++)
				rail[i][k] = '*';
		}

		for (int i = 0; i < col; i++) {
			if (j == 0 || j == key - 1)
				check = !check;
			rail[j][i] = '#';

			if (check)
				j++;
			else
				j--;
		}

		int index = 0;
		for (int i = 0; i < row; i++) {
			for (int k = 0; k < col; k++) {
				char ch = rail[i][k];
				if (ch == '#' && index < col) {
					rail[i][k] = encrypted.charAt(index++);
				}
			}
		}

		j = 0;
		check = false;
		for (int i = 0; i < col; i++) {
			if (j == 0 || j == key - 1)
				check = !check;
			decryptedText += rail[j][i];

			if (check)
				j++;
			else
				j--;
		}

		return decryptedText;
	}

	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("\nEnter the plain text message : ");
		String plainTxt = sc.nextLine();

		System.out.print("Enter the key : ");
		int key = sc.nextInt();

		String cipherTxt = encryption(plainTxt, key);
		System.out.println("\nEncrypted message: " + cipherTxt);

		String deCipherTxt = decryption(cipherTxt, key);
		System.out.println("\nDecrypted message: " + deCipherTxt+"\n");

	}

}