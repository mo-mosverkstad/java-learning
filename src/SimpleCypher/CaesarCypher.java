package SimpleCypher;

public class CaesarCypher extends MappingCypher {
    public CaesarCypher(int key, char offsetBase, int amount){
        super(new char[amount], new char[amount], offsetBase);
        for (int i = 0; i < amount; i++){
            int encryptedNumber = (i + key) % amount;
            encryptMap[i] = (char) (offsetBase + encryptedNumber);
            decryptMap[encryptedNumber] = (char) (offsetBase + i);
        }
    }

    public CaesarCypher(int key){
        this(3, 'a', 26);
    }
}
