/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.agnisys.idsnextgen.classes;

/**
 *
 * @author Sumeet
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

import org.apache.commons.codec.binary.Base64;

public class ImageConvertor {

    public static void main(String[] args) {

        ImageConvertor tempObject = new ImageConvertor();

        // convert file to regular byte array
        byte[] codedFile = tempObject.convertFileToByteArray("your_input_file_path");

        // encoded file in Base64
        byte[] encodedFile = Base64.encodeBase64(codedFile);

        // print out the byte array
        System.out.println(Arrays.toString(encodedFile));

        // print the encoded String
        System.out.println(encodedFile);

        // decode file back to regular byte array
        byte[] decodedByteArray = Base64.decodeBase64(encodedFile);

        // save decoded byte array to a file
        boolean success = tempObject.saveFileFromByteArray("your_output_file_path", decodedByteArray);

        // print out success
        System.out.println("success : " + success);
    }

    public String encodeImage(byte[] imageByteArray) {
        return Base64.encodeBase64URLSafeString(imageByteArray);
    }

    public String getImageString(String path) {

        // convert file to regular byte array
//        byte[] codedFile = convertFileToByteArray(path);
//
//        // encoded file in Base64
//        byte[] encodedFile = Base64.encodeBase64(codedFile);
        // print out the byte array
//        String imageString = null;
//        imageString = Arrays.toString(encodedFile);
//        System.out.println("encode file=" + imageString);
//        try {
//
//            imageString = new String(Base64.encodeBase64(encodedFile), "UTF-8");
//            System.out.println("--imageString=" + imageString);
//
//        } catch (Exception e) {
//            System.err.println("Err getImageString : " + e.getMessage());
//        }
        // Reading a Image file from file system
        try {
            File file = new File(path);
            FileInputStream imageInFile = new FileInputStream(file);
            byte imageData[] = new byte[(int) file.length()];
            imageInFile.read(imageData);

            // Converting Image byte array into Base64 String
            String imageDataString = encodeImage(imageData);
            imageDataString = imageDataString.replace("-", "+").replace("_", "/");
            System.out.println("encode file=" + imageDataString);
            return imageDataString;
        } catch (Exception e) {
            System.err.println("Err getmageString : " + e.getMessage());
        }
        return null;
    }

    private static ImageConvertor imageConvertor;

    public static ImageConvertor getImageConvertor() {
        if (imageConvertor == null) {
            imageConvertor = new ImageConvertor();
        }
        return imageConvertor;
    }

    public byte[] convertFileToByteArray(String filePath) {

        Path path = Paths.get(filePath);

        byte[] codedFile = null;

        try {
            codedFile = Files.readAllBytes(path);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return codedFile;
    }

    public boolean saveFileFromByteArray(String filePath, byte[] decodedByteArray) {

        boolean success = false;

        Path path = Paths.get(filePath);

        try {
            Files.write(path, decodedByteArray);
            success = true;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return success;
    }
}
