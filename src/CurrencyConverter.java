import javax.swing.*;
import javax.swing.event.DocumentEvent;
import javax.swing.event.DocumentListener;
import javax.swing.text.AbstractDocument;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.DocumentFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * A simple Currency Converter application using Java Swing.
 * Converts input amount in USD, CAD, or Euro to JMD (Jamaican Dollars).
 */
public class CurrencyConverter extends JFrame {

    /**
     * The main method to run the Currency Converter application.
     * @param args Command line arguments (not used)
     */
    public static void main(String[] args){
        try {
            UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        } catch (Exception e) {
            e.printStackTrace();
        }

        JFrame mainFrame = new JFrame("Currency Calculator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new GridLayout(3, 2, 5, 5));
        panel_1.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        // Row 1 - Input Amount
        JLabel label_1 = new JLabel("Input $:");
        JTextField txtf_input = new JTextField(15);
        ((AbstractDocument) txtf_input.getDocument()).setDocumentFilter(new NumericDocumentFilter());

        // Row 2 - Currency Selection
        JLabel label_2 = new JLabel("Input $:");
        String[] options = {"US", "CAN", "Euro"};
        JComboBox<String> comboBox = new JComboBox<>(options);

        // Row 3 - Output Converted Amount
        JLabel label_3 = new JLabel("JMD Amount $:");
        JTextField txtf_jmd = new JTextField(15);
        txtf_jmd.setEnabled(false);

        // Adding components to panel
        panel_1.add(label_1);
        panel_1.add(txtf_input);
        panel_1.add(label_2);
        panel_1.add(comboBox);
        panel_1.add(label_3);
        panel_1.add(txtf_jmd);

        // Setting panel dimensions
        panel_1.setPreferredSize(new Dimension(230, 100));

        // Panel 2 - Buttons
        JPanel panel_2 = new JPanel();
        panel_2.setLayout(new GridLayout(1, 2, 10, 15));

        JButton btn_convert = new JButton("Convert");
        JButton btn_clear= new JButton("Clear");
        btn_clear.setEnabled(false);
        btn_convert.setEnabled(false);
        panel_2.add(btn_convert);
        panel_2.add(btn_clear);
        panel_2.setPreferredSize(new Dimension(240, 30));
        panel_2.setBorder(BorderFactory.createEmptyBorder(0, 5, 5, 5));

        // Main frame settings
        mainFrame.setSize(new Dimension(260, 170));
        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

        // Action listener for clearing input
        btn_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtf_input.setText("");
                txtf_jmd.setText("");
            }
        });

        // Document listener to enable/disable buttons based on input
        txtf_input.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateButtons();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateButtons();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateButtons();
            }

            /**
             * Enables the convert and clear buttons if input is present.
             */
            private void updateButtons() {
                boolean hasText = txtf_input.getText().length() > 0;
                btn_clear.setEnabled(hasText);
                btn_convert.setEnabled(hasText);
            }
        });

        // Action listener for currency conversion
        btn_convert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String convertTo = (String) comboBox.getSelectedItem();
                double amount = Double.parseDouble(txtf_input.getText());
                double convertedAmount;

                switch (convertTo) {
                    case "US":
                        convertedAmount = amount * 129.02;
                        break;
                    case "CAN":
                        convertedAmount = amount * 95.50;
                        break;
                    case "Euro":
                        convertedAmount = amount * 164.33;
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + convertTo);
                }
                txtf_jmd.setText(String.format("%.2f", convertedAmount));
            }
        });

        // Adding panels to main frame
        mainFrame.add(panel_1);
        mainFrame.add(panel_2);
        mainFrame.setVisible(true);
    }

    /**
     * A custom DocumentFilter to allow only numeric input.
     */
    static class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string != null && string.matches("\\d*")) { // Only allow digits
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text.matches("\\d*")) { // Only allow digits
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
