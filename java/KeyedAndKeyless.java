import java.util.Scanner;

public class KeyedAndKeyless {
	public static String selectedKey;
	public static char sortedKey[];
	public static int sortedKeyPos[];

	public KeyedAndKeyless(String key) {
		selectedKey = key;
		sortedKeyPos = new int[selectedKey.length()];
		sortedKey = selectedKey.toCharArray();
	}

	public static void doProcessOnKey() {
		int min, i, j;
		char orginalKey[] = selectedKey.toCharArray();
		char temp;

		for (i = 0; i < selectedKey.length(); i++) {
			min = i;
			for (j = i; j < selectedKey.length(); j++) {
				if (sortedKey[min] > sortedKey[j]) {
					min = j;
				}
			}
			if (min != i) {
				temp = sortedKey[i];
				sortedKey[i] = sortedKey[min];
				sortedKey[min] = temp;
			}
		}

		for (i = 0; i < selectedKey.length(); i++) {
			for (j = 0; j < selectedKey.length(); j++) {
				if (orginalKey[i] == sortedKey[j])
					sortedKeyPos[i] = j;
			}
		}
	}

	public static String doEncryption(String plainText) {
		int i, j;
		doProcessOnKey();

		int row = plainText.length() / selectedKey.length();
		int extrabit = plainText.length() % selectedKey.length();
		int exrow = (extrabit == 0) ? 0 : 1;
		int coltemp = -1;
		int totalLength = (row + exrow) * selectedKey.length();
		char pmat[][] = new char[(row + exrow)][(selectedKey.length())];
		char encry[] = new char[totalLength];
		row = 0;
		for (i = 0; i < totalLength; i++) {
			coltemp++;
			if (i < plainText.length()) {
				if (coltemp == (selectedKey.length())) {
					row++;
					coltemp = 0;
				}
				pmat[row][coltemp] = plainText.charAt(i);
			} else {
				pmat[row][coltemp] = '*';
			}
		}
		int len = -1, k;
		for (i = 0; i < selectedKey.length(); i++) {
			for (k = 0; k < selectedKey.length(); k++) {
				if (i == sortedKeyPos[k]) {
					break;
				}
			}
			for (j = 0; j <= row; j++) {
				len++;
				encry[len] = pmat[j][k];
			}
		}
		String p1 = new String(encry);
		return (new String(p1));
	}

	public static String doDecryption(String s) {
		int i, j, k;
		char encry[] = s.toCharArray();
		doProcessOnKey();

		int row = s.length() / selectedKey.length();
		char pmat[][] = new char[row][(selectedKey.length())];
		int tempcnt = -1;
		for (i = 0; i < selectedKey.length(); i++) {
			for (k = 0; k < selectedKey.length(); k++) {
				if (i == sortedKeyPos[k]) {
					break;
				}
			}
			for (j = 0; j < row; j++) {
				tempcnt++;
				pmat[j][k] = encry[tempcnt];
			}
		}

		char p1[] = new char[row * selectedKey.length()];
		k = 0;
		for (i = 0; i < row; i++) {
			for (j = 0; j < selectedKey.length(); j++) {
				if (pmat[i][j] != '*') {
					p1[k++] = pmat[i][j];
				}
			}
		}
		p1[k++] = '\0';
		return (new String(p1));
	}

	@SuppressWarnings("static-access")
	public static void main(String[] args) {
		Scanner sc = new Scanner(System.in);
		System.out.print("Enter key for keyed encryption: ");
		String key = sc.nextLine();
		KeyedAndKeyless keyedAndKeyless = new KeyedAndKeyless(key);
		System.out.print("Enter plaint text: ");
		String plainTxt = sc.nextLine();
		String cipherTxt = keyedAndKeyless.doEncryption(plainTxt);
		System.out.print("Encrypted message: ");
		for (char ch : cipherTxt.toCharArray()) {
			if (ch != '*')
				System.out.print(ch);
		}
		String deCipheredTxt = doDecryption(cipherTxt);
		System.out.println("\nDecrypted Message is: " + deCipheredTxt + "\n");
		sc.close();
	}
}