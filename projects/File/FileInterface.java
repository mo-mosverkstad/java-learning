package File;
public interface FileInterface {
    public boolean create();
    public void write(String content);
    public String read();
    public void delete();
}
