package gui.helpers;

import javax.swing.filechooser.FileFilter;
import java.io.File;

/**
 * Class creates own filter for acceptation file extension
 */
public class PersonFileFilter extends FileFilter {

    /**
     * {@inheritDoc}
     *
     * @param f
     * @return
     */
    @Override
    public boolean accept(File f) {

        // allows to open directory
        if (f.isDirectory())
            return true;

        String extension = Utils.getFileExtension(f.getName());

        if (extension == null)
            return false;

        return extension.equals("per");
    }

    /**
     * {@inheritDoc}
     *
     * @return
     */
    @Override
    public String getDescription() {
        return "Person database files (*.per)";
    }
}
