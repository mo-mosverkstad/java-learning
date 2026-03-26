package SimpleCypher;

public class MappingCypher implements CypherInterface {
    private static String format = "MappingCypher(encryptMap = [%s], decryptMap = [%s], offsetBase = '%c')";

    protected char[] encryptMap;
    protected char[] decryptMap;
    protected char offsetBase = '\u0000';

    public MappingCypher(char[] encryptMap, char[] decryptMap){
        this.encryptMap = encryptMap;
        this.decryptMap = decryptMap;
    }

    public MappingCypher(char[] encryptMap, char[] decryptMap, char offsetBase){
        this.encryptMap = encryptMap;
        this.decryptMap = decryptMap;
        this.offsetBase = offsetBase;
    }

    private String mapString(String raw, char[] map){
        int mapLength = map.length;
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < raw.length(); i++){
            char currentChar = raw.charAt(i);
            int currentCharIndex = currentChar - offsetBase;
            if (currentCharIndex > 0 && currentCharIndex < mapLength){
                builder.append(map[currentCharIndex]);
            }
            else{
                builder.append(currentChar);
            }
            
        }
        return builder.toString();
    }

    @Override
    public String encrypt(String plain){
        return mapString(plain, encryptMap);
    }

    @Override
    public String decrypt(String encrypted){
        return mapString(encrypted, decryptMap);
    }

    public String toString(){
        return String.format(format, new String(encryptMap), new String(decryptMap), offsetBase);
    }
}
