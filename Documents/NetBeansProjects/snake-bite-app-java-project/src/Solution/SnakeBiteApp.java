package Solution;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.Scanner;

public class SnakeBiteApp 
{
    // Declare GUI components
    private final JFrame frame;
    private final JComboBox<String> snakeComboBox;
    private JTextArea medicalInfoArea;
    private final JButton processButton;
    private final JButton clearButton;

    // Constructor to set up the GUI
    public SnakeBiteApp() 
    {
        // Initialize the main frame of the application
        frame = new JFrame("Snake Information");
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(500, 400);  // Set size of the window
        frame.setLayout(new BorderLayout(20, 20)); // Better structured layout with padding
        frame.getContentPane().setBackground(new Color(250, 250, 250)); // Apple-like light background

        // Initialize components with modern Apple-like styles
        snakeComboBox = new JComboBox<>();
        snakeComboBox.setFont(new Font("Helvetica", Font.PLAIN, 18));
        snakeComboBox.setPreferredSize(new Dimension(220, 40));
        snakeComboBox.setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10));

        medicalInfoArea = new JTextArea(10, 30);
        medicalInfoArea.setFont(new Font("Helvetica", Font.PLAIN, 16));
        medicalInfoArea.setLineWrap(true);
        medicalInfoArea.setWrapStyleWord(true);
        medicalInfoArea.setBorder(BorderFactory.createLineBorder(new Color(220, 220, 220), 2));
        medicalInfoArea.setBackground(new Color(240, 240, 240)); // Subtle background for text area

        processButton = createStyledButton("Process", new Color(52, 199, 89)); // Apple-like green button
        clearButton = createStyledButton("Clear", new Color(255, 69, 58));    // Red clear button, similar to iOS

        // Create a top panel for the combo box
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 10));
        topPanel.setBackground(new Color(255, 255, 255));
        topPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding
        JLabel label = new JLabel("Select Snake:");
        label.setFont(new Font("Helvetica", Font.PLAIN, 18));
        topPanel.add(label);
        topPanel.add(snakeComboBox);

        // Create a center panel for the medical info
        JPanel centerPanel = new JPanel(new BorderLayout());
        centerPanel.setBackground(new Color(255, 255, 255));
        centerPanel.setBorder(new EmptyBorder(10, 10, 10, 10));
        JScrollPane scrollPane = new JScrollPane(medicalInfoArea);
        centerPanel.add(scrollPane, BorderLayout.CENTER);

        // Create a bottom panel for the buttons
        JPanel bottomPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 20, 10));
        bottomPanel.setBackground(new Color(255, 255, 255));
        bottomPanel.setBorder(new EmptyBorder(10, 10, 10, 10)); // Padding
        bottomPanel.add(processButton);
        bottomPanel.add(clearButton);

        // Add panels to the frame
        frame.add(topPanel, BorderLayout.NORTH);
        frame.add(centerPanel, BorderLayout.CENTER);
        frame.add(bottomPanel, BorderLayout.SOUTH);

        // Load snake types from the file into the combo box
        loadSnakeTypes();

        // Define the action for the Process button (on click, display medical info)
        processButton.addActionListener((ActionEvent e) -> 
        {
            displayMedicalInfo(); // Show medical info when Process is clicked
        });

        // Define the action for the Clear button (on click, clear the text area)
        clearButton.addActionListener((ActionEvent e) -> 
        {
            medicalInfoArea.setText(""); // Clear the text area when Clear is clicked
        });

        // Display the frame
        frame.setVisible(true);
    }

    // Method to create styled buttons similar to Apple's design
    private JButton createStyledButton(String text, Color backgroundColor) 
    {
        JButton button = new JButton(text);
        button.setPreferredSize(new Dimension(140, 50)); // Larger button size like Apple's design
        button.setFont(new Font("Helvetica", Font.BOLD, 16));
        button.setFocusPainted(false); // No border when button is focused
        button.setBackground(backgroundColor);
        button.setForeground(Color.WHITE); // White text on colored buttons
        button.setBorder(BorderFactory.createEmptyBorder(10, 20, 10, 20)); // Rounded, no sharp edges
        
        // Rounded corners (using UIManager for native-like look)
        button.setBorder(BorderFactory.createLineBorder(new Color(200, 200, 200), 2));
        
        // Adding hover effect to mimic modern Apple button interaction
        button.addMouseListener(new java.awt.event.MouseAdapter() 
        {
            @Override
            public void mouseEntered(java.awt.event.MouseEvent evt) 
            {
                button.setBackground(backgroundColor.darker());
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent evt) 
            {
                button.setBackground(backgroundColor);
            }
        });

        return button;
    }

    // Method to load snake types from the file into the combo box
    private void loadSnakeTypes() 
    {
        try 
        {
            // Open the snakes.txt file for reading
            Scanner scanner = new Scanner(new File("snakes.txt"));

            // Read each line, extract snake types and add them to the combo box
            while (scanner.hasNextLine()) 
            {
                String line = scanner.nextLine();
                String[] data = line.split(","); // Split each line by commas

                // Add the snake type (first value) to the combo box
                snakeComboBox.addItem(data[0]);
            }

            scanner.close(); // Close the file reader
        } 
        catch (FileNotFoundException e) 
        {
            // Show an error dialog if the file isn't found
            JOptionPane.showMessageDialog(frame, "Error loading snake data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Method to display medical information for the selected snake
    private void displayMedicalInfo() 
    {
        // Get the snake selected by the user in the combo box
        String selectedSnake = (String) snakeComboBox.getSelectedItem();
        
        try 
        {
            // Open the snakes.txt file for reading
            Scanner scanner = new Scanner(new File("snakes.txt"));

            // Read through the file to find the matching snake
            while (scanner.hasNextLine()) 
            {
                String line = scanner.nextLine();
                String[] data = line.split(","); // Split the line by commas

                // If the snake in the file matches the selected one, display its info
                if (data[0].equals(selectedSnake)) 
                {
                    medicalInfoArea.setText("Fatality Ranking: " + data[1] + "\n"
                                           + "Medical Assistance Required: " + data[2]);
                    break;
                }
            }

            scanner.close(); // Close the file reader
        } 
        catch (FileNotFoundException e) 
        {
            // Show an error dialog if the file isn't found
            JOptionPane.showMessageDialog(frame, "Error loading snake data", "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Main method to start the application
    public static void main(String[] args) 
    {
        SnakeBiteApp snakeBiteApp = new SnakeBiteApp(); // Create an instance of the app
    }
}
