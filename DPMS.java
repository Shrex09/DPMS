import javax.swing.*;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;
import java.util.HashMap;
import java.util.Map;

// User class
class User 
{
    private String username;
    private String password;
    private List<Double> weightLog; //private variable

    public User(String username, String password) 
    {
        this.username = username; 
        this.password = password;
        this.weightLog = new ArrayList<>();
    }

    public String getUsername() 
    {
        return username;
    }

    public boolean checkPassword(String password) 
    {
        return this.password.equals(password);
    }

    public void logWeight(double weight) 
    {
        weightLog.add(weight);
    }

    public List<Double> getWeightLog() 
    {
        return weightLog;
    }
}

// FoodItem class
class FoodItem 
{
    private String name;
    private int calories;

    public FoodItem(String name, int calories) 
    {
        this.name = name;
        this.calories = calories;
    }

    public String getName() 
    {
        return name;
    }

    @Override //helps to catch errors at compile time
    public String toString() 
    {
        return name + " (" + calories + " calories)";
    }
}

// DietPlan class
class DietPlan 
{
    private String name;
    private List<FoodItem> foodItems;

    public DietPlan(String name) 
    {
        this.name = name;
        this.foodItems = new ArrayList<>();
    }

    public void addFoodItem(FoodItem foodItem) 
    {
        foodItems.add(foodItem);
    }

    @Override
    public String toString() 
    {
        StringBuilder sb = new StringBuilder();
        sb.append("Diet Plan: ").append(name).append("\n");
        for (FoodItem item : foodItems) {
            sb.append(item.toString()).append("\n");
        }
        return sb.toString();
    }
}

// Main class with GUI
public class DPMS 
{
    private List<User> users;
    private List<FoodItem> foodItems;
    private List<DietPlan> dietPlans;
    private User currentUser;

    private JFrame frame;
    private JPanel mainPanel; //buttons,textfields
    private CardLayout cardLayout; //This variable represents a layout manager that allows you to switch between different panels

    public DPMS() 
    {
        users = new ArrayList<>();
        foodItems = new ArrayList<>();
        dietPlans = new ArrayList<>();

        initializeFoodItems();
        initializeDietPlans();

        // Initialize the GUI
        frame = new JFrame("Diet Plan Management System");
        cardLayout = new CardLayout();
        mainPanel = new JPanel(cardLayout);

        createRegisterPanel();
        createLoginPanel();
        createMainPanel();
        createAddFoodToDietPlanPanel();
        createViewDietPlansPanel();
        createBMIPanel();
        createProgressTrackerPanel();

        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); //what should happen when user closes the application window
        frame.add(mainPanel);
        frame.setSize(400, 300);
        frame.setVisible(true);
    }

    private void initializeFoodItems() 
    {
        foodItems.add(new FoodItem("Apple", 95));
        foodItems.add(new FoodItem("Banana", 105));
        foodItems.add(new FoodItem("Chicken Breast", 165));
        foodItems.add(new FoodItem("Broccoli", 55));
    }

    private void initializeDietPlans() 
    {
        DietPlan underweightPlan = new DietPlan("Underweight Plan");
        underweightPlan.addFoodItem(new FoodItem("Banana", 105));
        underweightPlan.addFoodItem(new FoodItem("Chicken Breast", 165));
        underweightPlan.addFoodItem(new FoodItem("Peanut Butter", 180));
        underweightPlan.addFoodItem(new FoodItem("Whole Milk", 150));

        DietPlan normalPlan = new DietPlan("Normal Weight Plan");
        normalPlan.addFoodItem(new FoodItem("Apple", 95));
        normalPlan.addFoodItem(new FoodItem("Grilled Chicken", 180));
        normalPlan.addFoodItem(new FoodItem("Brown Rice", 220));
        normalPlan.addFoodItem(new FoodItem("Salad", 150));

        DietPlan overweightPlan = new DietPlan("Overweight Plan");
        overweightPlan.addFoodItem(new FoodItem("Oatmeal", 150));
        overweightPlan.addFoodItem(new FoodItem("Steamed Broccoli", 55));
        overweightPlan.addFoodItem(new FoodItem("Grilled Fish", 120));
        overweightPlan.addFoodItem(new FoodItem("Green Tea", 0));

        dietPlans.add(underweightPlan);
        dietPlans.add(normalPlan);
        dietPlans.add(overweightPlan);
    }

    private void createRegisterPanel() 
    {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton registerButton = new JButton("Register");
        JButton backButton = new JButton("Back");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(registerButton);
        panel.add(backButton);

        registerButton.addActionListener(e -> 
        {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            registerUser(username, password);
            cardLayout.show(mainPanel, "Login");
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        mainPanel.add(panel, "Register");
    }

    private void createLoginPanel() 
    {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField usernameField = new JTextField();
        JPasswordField passwordField = new JPasswordField();
        JButton loginButton = new JButton("Login");
        JButton registerButton = new JButton("Register");

        panel.add(new JLabel("Username:"));
        panel.add(usernameField);
        panel.add(new JLabel("Password:"));
        panel.add(passwordField);
        panel.add(loginButton);
        panel.add(registerButton);

        loginButton.addActionListener(e -> 
        {
            String username = usernameField.getText();
            String password = new String(passwordField.getPassword());
            if (loginUser(username, password)) {
                cardLayout.show(mainPanel, "Main");
            }
        });

        registerButton.addActionListener(e -> cardLayout.show(mainPanel, "Register"));

        mainPanel.add(panel, "Login");
    }

    private void createMainPanel() 
    {
        JPanel panel = new JPanel(new GridLayout(5, 1));
        JButton addFoodToDietPlanButton = new JButton("Add Food to Diet Plan");
        JButton viewDietPlansButton = new JButton("View Diet Plans");
        JButton bmiButton = new JButton("Calculate BMI");
        JButton progressTrackerButton = new JButton("Progress Tracker");
        JButton logoutButton = new JButton("Logout");

        panel.add(addFoodToDietPlanButton);
        panel.add(viewDietPlansButton);
        panel.add(bmiButton);
        panel.add(progressTrackerButton);
        panel.add(logoutButton);

        addFoodToDietPlanButton.addActionListener(e -> cardLayout.show(mainPanel, "AddFoodToDietPlan"));
        viewDietPlansButton.addActionListener(e -> cardLayout.show(mainPanel, "ViewDietPlans"));
        bmiButton.addActionListener(e -> cardLayout.show(mainPanel, "BMI"));
        progressTrackerButton.addActionListener(e -> cardLayout.show(mainPanel, "ProgressTracker"));
        logoutButton.addActionListener(e -> cardLayout.show(mainPanel, "Login"));

        mainPanel.add(panel, "Main");
    }

    private void createAddFoodToDietPlanPanel() 
    {
        JPanel panel = new JPanel(new GridLayout(3, 2));
        JTextField planNameField = new JTextField();
        JTextField foodNameField = new JTextField();
        JButton addButton = new JButton("Add");
        JButton backButton = new JButton("Back");

        panel.add(new JLabel("Diet Plan Name:"));
        panel.add(planNameField);
        panel.add(new JLabel("Food Name:"));
        panel.add(foodNameField);
        panel.add(addButton);
        panel.add(backButton);

        addButton.addActionListener(e -> {
            String planName = planNameField.getText();
            String foodName = foodNameField.getText();
            addFoodItemToDietPlan(planName, foodName);
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main"));

        mainPanel.add(panel, "AddFoodToDietPlan");
    }

    private void createViewDietPlansPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        JTextArea textArea = new JTextArea();
        JButton backButton = new JButton("Back");

        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);
        panel.add(backButton, BorderLayout.SOUTH);

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main"));

        mainPanel.add(panel, "ViewDietPlans");

        panel.addComponentListener(new java.awt.event.ComponentAdapter() 
        {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) {
                textArea.setText("");
                for (DietPlan plan : dietPlans) {
                    textArea.append(plan.toString() + "\n");
                }
            }
        });
    }

    private void createBMIPanel() 
    {
        JPanel panel = new JPanel(new GridLayout(4, 2));
        JTextField heightField = new JTextField();
        JTextField weightField = new JTextField();
        JButton calculateButton = new JButton("Calculate BMI");
        JButton backButton = new JButton("Back");
        JLabel resultLabel = new JLabel("BMI: ");

        panel.add(new JLabel("Height (m):"));
        panel.add(heightField);
        panel.add(new JLabel("Weight (kg):"));
        panel.add(weightField);
        panel.add(calculateButton);
        panel.add(resultLabel);
        panel.add(backButton);

        calculateButton.addActionListener(e -> 
        {
            double height = Double.parseDouble(heightField.getText());
            double weight = Double.parseDouble(weightField.getText());
            double bmi = calculateBMI(height, weight);
            resultLabel.setText("BMI: " + String.format("%.2f", bmi) + " - " + getBMICategory(bmi));
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main"));

        mainPanel.add(panel, "BMI");
    }

    private void createProgressTrackerPanel() 
    {
        JPanel panel = new JPanel(new BorderLayout());
        JTextField weightField = new JTextField();
        JButton logButton = new JButton("Log Weight");
        JTextArea textArea = new JTextArea();
        JButton backButton = new JButton("Back");

        JPanel inputPanel = new JPanel(new GridLayout(2, 2));
        inputPanel.add(new JLabel("Weight (kg):"));
        inputPanel.add(weightField);
        inputPanel.add(logButton);
        inputPanel.add(backButton);

        panel.add(inputPanel, BorderLayout.NORTH);
        panel.add(new JScrollPane(textArea), BorderLayout.CENTER);

        logButton.addActionListener(e -> 
        {
            double weight = Double.parseDouble(weightField.getText());
            currentUser.logWeight(weight);
            updateProgressTextArea(textArea);
        });

        backButton.addActionListener(e -> cardLayout.show(mainPanel, "Main"));

        mainPanel.add(panel, "ProgressTracker");

        panel.addComponentListener(new java.awt.event.ComponentAdapter() 
        {
            @Override
            public void componentShown(java.awt.event.ComponentEvent evt) 
            {
                updateProgressTextArea(textArea);
            }
        });
    }

    private void updateProgressTextArea(JTextArea textArea) 
    {
        textArea.setText("");
        List<Double> weightLog = currentUser.getWeightLog();
        for (int i = 0; i < weightLog.size(); i++) {
            textArea.append("Entry " + (i + 1) + ": " + weightLog.get(i) + " kg\n");
        }
    }

    public double calculateBMI(double height, double weight) 
    {
        return weight / (height * height);
    }

    public String getBMICategory(double bmi) 
    
    {
        if (bmi < 18.5) {
            return "Underweight";
        } else if (bmi >= 18.5 && bmi < 24.9) 
        {
            return "Normal weight";
        } else if (bmi >= 25 && bmi < 29.9) 
        {
            return "Overweight";
        } else 
        {
            return "Obesity";
        }
    }

    public void registerUser(String username, String password) 
    {
        users.add(new User(username, password));
        JOptionPane.showMessageDialog(frame, "User registered successfully.");
    }

    public boolean loginUser(String username, String password) 
    {
        for (User user : users) {
            if (user.getUsername().equals(username) && user.checkPassword(password)) 
            {
                currentUser = user;
                JOptionPane.showMessageDialog(frame, "Login successful.");
                return true;
            }
        }
        JOptionPane.showMessageDialog(frame, "Invalid username or password.");
        return false;
    }

    public void addFoodItemToDietPlan(String planName, String foodItemName) 
    {
        DietPlan plan = findDietPlan(planName);
        FoodItem item = findFoodItem(foodItemName);

        if (plan != null && item != null) 
        {
            plan.addFoodItem(item);
            JOptionPane.showMessageDialog(frame, "Food item added to diet plan successfully.");
        } else 
        {
            JOptionPane.showMessageDialog(frame, "Diet plan or food item not found.");
        }
    }

    private DietPlan findDietPlan(String name) 
    {
        for (DietPlan plan : dietPlans) 
        {
            if (plan.toString().contains(name)) 
            {
                return plan;
            }
        }
        return null;
    }

    private FoodItem findFoodItem(String name) 
    {
        for (FoodItem item : foodItems) 
        {
            if (item.getName().equals(name)) 
            {
                return item;
            }
        }
        return null;
    }

    public static void main(String[] args) 
    {
        SwingUtilities.invokeLater(DPMS::new);
    }
}
