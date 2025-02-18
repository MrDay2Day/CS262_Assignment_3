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
import java.util.Objects;

public class CurrencyConverter extends JFrame {
    public static void main(String[] args){
        JFrame mainFrame = new JFrame("Currency Calculator");
        mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        JPanel panel_1 = new JPanel();
        panel_1.setLayout(new GridLayout(3, 2, 1, 1));

        // Row 1
        JLabel label_1 = new JLabel("Input $:");
        JTextField txtf_input = new JTextField(15);
        ((AbstractDocument) txtf_input.getDocument()).setDocumentFilter(new NumericDocumentFilter());

        // Row 2
        JLabel label_2 = new JLabel("Input $:");
        String[] options = {"US", "CAN", "Euro"};
        JComboBox<String> comboBox = new JComboBox<String>(options);

        // Row 3
        JLabel label_3 = new JLabel("JMD Amount $:");
        JTextField txtf_jmd = new JTextField(15);
        txtf_jmd.setEnabled(false);



        panel_1.add(label_1);
        panel_1.add(txtf_input);
        panel_1.add(label_2);
        panel_1.add(comboBox);
        panel_1.add(label_3);
        panel_1.add(txtf_jmd);

        int panel_1_width = 230;
        panel_1.setPreferredSize(new Dimension(panel_1_width, 70));
        panel_1.setMaximumSize(new Dimension(panel_1_width, 70));
        panel_1.setMinimumSize(new Dimension(panel_1_width, 70));

        JPanel panel_2 = new JPanel();
        panel_2.setLayout(new GridLayout(1, 2, 10, 15));

        JButton btn_convert = new JButton("Convert");
        JButton btn_clear= new JButton("Clear");
        btn_clear.setEnabled(false);
        btn_convert.setEnabled(false);
        panel_2.add(btn_convert);
        panel_2.add(btn_clear);
        int panel_2_width = 200;
        panel_2.setPreferredSize(new Dimension(panel_2_width, 25));
        panel_2.setMaximumSize(new Dimension(panel_2_width, 25));
        panel_2.setMinimumSize(new Dimension(panel_2_width, 25));


        mainFrame.setSize(new Dimension(panel_1_width + 30, 150));
        mainFrame.setLayout(new BoxLayout(mainFrame.getContentPane(), BoxLayout.Y_AXIS));

        txtf_input.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                System.out.println(e);
            }
        });

        btn_clear.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                txtf_input.setText("");
                txtf_jmd.setText("");
            }
        });

        txtf_input.getDocument().addDocumentListener(new DocumentListener() {
            @Override
            public void insertUpdate(DocumentEvent e) {
                updateCount();
            }

            @Override
            public void removeUpdate(DocumentEvent e) {
                updateCount();
            }

            @Override
            public void changedUpdate(DocumentEvent e) {
                updateCount();
            }

            private void updateCount() {
                int length = txtf_input.getText().length();
                if(length > 0){
                    btn_clear.setEnabled(true);
                    btn_convert.setEnabled(true);
                }else{
                    btn_clear.setEnabled(false);
                    btn_convert.setEnabled(false);
                }
            }
        });

        btn_convert.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String convertTo = (String) comboBox.getSelectedItem();
                double amount = Double.parseDouble(txtf_input.getText());

                switch (Objects.requireNonNull(convertTo)){
                    case "US":
                        txtf_jmd.setText(String.format("%.2f", amount * 129.02));
                        break;
                    case "CAN":
                        txtf_jmd.setText(String.format("%.2f", amount * 95.50));
                        break;
                    case "Euro":
                        txtf_jmd.setText(String.format("%.2f", amount * 164.33));
                        break;


                    default:
                        throw new IllegalStateException("Unexpected value: " + convertTo);
                }
            }
        });

        mainFrame.add(panel_1);
        mainFrame.add(panel_2);
        // mainFrame.setResizable(false);
        mainFrame.setVisible(true);
    }

    static class NumericDocumentFilter extends DocumentFilter {
        @Override
        public void insertString(FilterBypass fb, int offset, String string, AttributeSet attr) throws BadLocationException {
            if (string == null) return;
            if (string.matches("\\d*")) {  // Only allow digits
                super.insertString(fb, offset, string, attr);
            }
        }

        @Override
        public void replace(FilterBypass fb, int offset, int length, String text, AttributeSet attrs) throws BadLocationException {
            if (text.matches("\\d*")) {  // Only allow digits
                super.replace(fb, offset, length, text, attrs);
            }
        }
    }
}
