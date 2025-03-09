/**
 * The Main class serves as the entry point for launching multiple applications.
 * It initializes and runs both the Currency Converter and Text Editor applications.
 */
public class Main {

    /**
     * The main method that executes both the CurrencyConverter and TextEditor applications.
     * @param args Command-line arguments passed to the applications (not used).
     */
    public static void main(String[] args) {

        // Launch the Currency Converter application
        CurrencyConverter.main(args);

        // Launch the Text Editor application
        TextEditor.main(args);
    }
}
