package DesignPatterns.StructuralPatterns.Proxy;

public class ProxyMain {

    public static void main(String[] args) {
        Image image = new ProxyImage("test_10mb.jpg");
        System.out.println("The first displayed image is: " + image);
        image.display();

        System.out.println("The second image is: " + image);
        image.display();

        ProxyImages proxyImages = new ProxyImages();
        proxyImages.display("my_photo.jpg");
        proxyImages.display("my_photo2.jpg");
        proxyImages.display("my_photo.jpg");
        proxyImages.display("my_photo2.jpg");

    }
}
