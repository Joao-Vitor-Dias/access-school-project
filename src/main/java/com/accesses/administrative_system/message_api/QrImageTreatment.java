package com.accesses.administrative_system.message_api;

import org.springframework.stereotype.Component;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

@Component
public class QrImageTreatment {

    public BufferedImage getImage(String imagePath) throws IOException {
        File imageFile = new File(imagePath);

        if (!imageFile.exists()) {
            throw new IOException("Image file not found: " + imagePath);
        }

        return ImageIO.read(imageFile);
    }

    public byte[] getImageAsBytes(String imagePath) throws IOException {
        File imageFile = new File(imagePath);
        return Files.readAllBytes(imageFile.toPath());
    }

}
