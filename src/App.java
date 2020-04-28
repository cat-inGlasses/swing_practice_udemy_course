import gui.MainFrame;

import javax.swing.*;

public class App {
    public static void main(String[] args) {

        // running application in another thread
        SwingUtilities.invokeLater(MainFrame::new);
    }
}
