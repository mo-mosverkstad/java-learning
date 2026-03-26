package OodPrinciples.LiskovSubstitutionPrinciple;

public class MatrixArgument extends MatrixConfig {
    public MatrixArgument(String[] args) {
        for (int i = 0; i < args.length; i = i + 1) {
            String[] entries = args[i].split("=");
            configMap.put(entries[0], entries[1]);
        }
    }

}
