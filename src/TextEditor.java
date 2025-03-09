import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

/**
 * A simple text editor application using Java Swing.
 * Provides basic functionality to create, open, and save text files.
 */
public class TextEditor extends JFrame {

    /**
     * Main entry point of the Text Editor application.
     * @param args Command-line arguments (not used).
     */
    public static void main(String[] args) {
        try {
            // Set the Windows look and feel for UI consistency
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        // Create the main frame
        JFrame frame = new JFrame("Text Editor");

        // Create a menu bar
        JMenuBar menuBar = new JMenuBar();

        // Create the menu buttons
        JMenu fileMenu = new JMenu("File");
        JMenuItem newMenuItem = new JMenuItem("New");
        JMenuItem openMenuItem = new JMenuItem("Open");
        JMenuItem saveMenuItem = new JMenuItem("Save");
        JMenuItem quitMenuItem = new JMenuItem("Quit");

        // Add an action listener to quit the application
        quitMenuItem.addActionListener(e -> System.exit(0));

        // Add menu items to the file menu
        fileMenu.add(newMenuItem);
        fileMenu.add(openMenuItem);
        fileMenu.add(saveMenuItem);
        fileMenu.add(quitMenuItem);

        // Add the file menu to the menu bar
        menuBar.add(fileMenu);

        // Set the menu bar on the frame
        frame.setJMenuBar(menuBar);

        // Create a text area for editing text
        JTextArea textArea = new JTextArea(5, 20);
        textArea.setText("This is a simple text editor written in Java.");
        textArea.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));
        frame.add(textArea);

        // Add action listener for the "New" menu item
        newMenuItem.addActionListener(e -> textArea.setText(""));

        // Add action listener for the "Open" menu item
        openMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Open Text File");

                int result = fileChooser.showOpenDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();
                    readFile(selectedFile);
                }
            }

            /**
             * Reads the content of a selected file and displays it in the text area.
             * @param file The file to be read.
             */
            private void readFile(File file) {
                try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                    textArea.setText(""); // Clear previous text
                    String line;
                    while ((line = reader.readLine()) != null) {
                        textArea.append(line + "\n");
                    }
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(frame, "Error reading file!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Add action listener for the "Save" menu item
        saveMenuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JFileChooser fileChooser = new JFileChooser();
                fileChooser.setDialogTitle("Save Text File");

                int result = fileChooser.showSaveDialog(frame);
                if (result == JFileChooser.APPROVE_OPTION) {
                    File selectedFile = fileChooser.getSelectedFile();

                    // Ensure the file has a .txt extension
                    if (!selectedFile.getName().toLowerCase().endsWith(".txt")) {
                        selectedFile = new File(selectedFile.getAbsolutePath() + ".txt");
                    }

                    writeFile(selectedFile);
                }
            }

            /**
             * Writes the content of the text area to a selected file.
             * @param file The file to be written to.
             */
            private void writeFile(File file) {
                try (BufferedWriter writer = new BufferedWriter(new FileWriter(file))) {
                    writer.write(textArea.getText());
                    JOptionPane.showMessageDialog(frame, "File saved successfully!", "Success",
                            JOptionPane.INFORMATION_MESSAGE);
                } catch (IOException e) {
                    JOptionPane.showMessageDialog(frame, "Error saving file!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        // Set frame properties
        frame.setSize(500, 300);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
