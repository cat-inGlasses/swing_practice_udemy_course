package gui.helpers;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;
import java.net.URL;

/**
 * class provides helping tools
 */
public class Utils {

    /**
     * Method helps to extract extension from file name
     *
     * @param name file name
     * @return extension
     */
    public static String getFileExtension(String name) {
        int pointIndex = name.lastIndexOf(".");

        if (pointIndex == -1) // not fount
            return null;

        if (pointIndex == name.length() - 1) // dot is the last element
            return null;

        return name.substring(pointIndex + 1);
    }

    /**
     * Method provides icon creating
     *
     * @param path path to the image
     * @return {@link ImageIcon} object
     */
    public static ImageIcon createIcon(String path) {
        URL url = Utils.class.getResource(path);
        if (url == null) {
            System.err.println("Unable to load image: " + path);
        }

        return new ImageIcon(url);
    }

    /**
     * Method provides font object creating from given file
     *
     * @param path path to font
     * @return {@link Font} object
     */
    public static Font createFont(String path) {
        URL url = Utils.class.getResource(path);
        if (url == null) {
            System.err.println("Unable to load font: " + path);
            return null;
        }

        Font font = null;
        try {
            font = Font.createFont(Font.TRUETYPE_FONT, url.openStream());
        } catch (FontFormatException e) {
            System.err.println("Bad format in font file: " + path);
        } catch (IOException e) {
            System.err.println("Unable to read font file: " + path);
        }

        return font;
    }
}
