import java.util.List;

public class Main {
    public static void main(String[] args) {
        List<Integer> publicKey = Rsa.getPublicKey();
        List<Integer> privateKey = Rsa.getPrivateKey();

        String plainText = "Hello world";
        String cipherText = Rsa.encryption(plainText, publicKey);
        System.out.println(cipherText);
        System.out.println("Decryption is" + Rsa.decryption(cipherText, privateKey));

    }
}
