
import java.util.ArrayList;
import java.util.List;
import java.lang.Math;

public class Rsa {

    public static String encryption(String plainText, List<Integer> publicKey) {
        Integer n = publicKey.get(1);
        Integer e = publicKey.get(0);
        String cipherText = "";
        for (char c : plainText.toCharArray()) {
            Integer asciiLetter = (int) c;
            Integer cipherLetter = (int) (Math.pow(asciiLetter, e) % n);
            cipherText += cipherLetter;
            cipherText += "-";

        }
        return cipherText;
    }

    public static String[] decryption(String cipherText, List<Integer> privateKey) {
        String[] cipherLetters = cipherText.split("-", 0);
        for (String c : cipherLetters.) {
            System.out.println(c);
        }
        return cipherLetters;
    }

    public static int getE() {
        int m = Rsa.getM();
        int publicKey = 5;
        System.out.println("Value of m is" + m);

        // GCD(e, m) = 1 1 < e <m
        for (int e = Rsa.getP(); e <= m; e++) {
            int gcd = getGcd(e, m);
            if (gcd == 1) {
                publicKey = e;
                break;
            }

        }
        return publicKey;
    }

    public static int getD() {
        double d = 7d;
        int m = Rsa.getM();
        int e = Rsa.getE();

        for (int i = 0; i <= m; i++) {
            d = (double) (1 + i * m) / e;

            if (d % 1 == 0) { // d is whole number
                break;
            }

        }
        return (int) d;

    }

    public static List<Integer> getPublicKey() {
        Integer n = Rsa.getN();
        Integer e = Rsa.getE();
        return new ArrayList<>(List.of(e, n));

    }

    public static List<Integer> getPrivateKey() {
        Integer n = Rsa.getN();
        Integer d = Rsa.getD();
        return new ArrayList<>(List.of(d, n));
    }

    private static int getP() {
        // Use some algorithm here to generate large prime number
        // instead of p = 29
        return 107;

    }

    private static int getQ() {
        // Use some algorithm here to generate large prime number
        // instead of q = 17
        return 113;
    }

    private static int getM() {
        int p = Rsa.getP();
        int q = Rsa.getQ();
        int m = (p - 1) * (q - 1);
        return m;
    }

    private static int getN() {
        return Rsa.getP() * Rsa.getQ();
    }

    static int getGcd(int a, int b) {

        int i;
        if (a < b)
            i = a;
        else
            i = b;

        for (i = i; i > 1; i--) {

            if (a % i == 0 && b % i == 0)
                return i;
        }
        return 1;
    }
}