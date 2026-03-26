package DesignPatterns.StructuralPatterns.Facade;

import java.io.File;

public class FacadeMain {
    public static void main(String[] args) {
        VideoConversionFacade converter = new VideoConversionFacade();
        File mp4Video = converter.convertVideo("youtubevideo.ogg", "mp4");
        System.out.println("converted to mp4 : " + mp4Video);
    }
}
