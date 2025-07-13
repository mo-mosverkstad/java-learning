package SimpleCypher;

public class SimpleCypher {
    public static void main(String[] args) {
        CaesarCypher mappingCypher = new CaesarCypher(3);

        System.out.println(mappingCypher.encrypt("hello world"));
        System.out.println(mappingCypher.decrypt("khoor zruog"));
    }

}
