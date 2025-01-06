import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class Main extends Application {
    private BudgetManager manager = BudgetManager.getInstance();
    private CommandManager commandManager = new CommandManager(); // CommandManager for undo/redo functionality

    @Override
    public void start(Stage primaryStage) {
        primaryStage.setTitle("Expense Tracker");

        // Layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));

        // Set Budget
        Label budgetLabel = new Label("Set Budget:");
        TextField budgetField = new TextField();
        Button setBudgetButton = new Button("Set Budget");
        Label budgetStatusLabel = new Label();

        setBudgetButton.setOnAction(e -> {
            try {
                double budget = Double.parseDouble(budgetField.getText());
                manager.setBudget(budget);
                budgetStatusLabel.setText("Budget set to: $" + budget);
            } catch (NumberFormatException ex) {
                budgetStatusLabel.setText("Invalid budget amount!");
            }
        });

        // Add Expense
        Label expenseLabel = new Label("Add Expense:");
        TextField expenseDescriptionField = new TextField();
        expenseDescriptionField.setPromptText("Description");
        TextField expenseAmountField = new TextField();
        expenseAmountField.setPromptText("Amount");
        ComboBox<String> categoryDropdown = new ComboBox<>();
        categoryDropdown.getItems().addAll("Food", "Transport", "Shopping", "Entertainment");
        categoryDropdown.setPromptText("Select Category");
        CheckBox recurringCheckBox = new CheckBox("Recurring");
        Button addExpenseButton = new Button("Add Expense");
        Label expenseStatusLabel = new Label();

        addExpenseButton.setOnAction(e -> {
            try {
                String description = expenseDescriptionField.getText();
                String categoryName = categoryDropdown.getValue();
                double amount = Double.parseDouble(expenseAmountField.getText());

                if (categoryName == null) {
                    expenseStatusLabel.setText("Please select a category!");
                    return;
                }

                ExpenseCategory category = ExpenseCategoryFactory.createCategory(categoryName);

                Expense expense = new BasicExpense(description, amount);
                if (recurringCheckBox.isSelected()) {
                    expense = new RecurringExpense(expense);
                }

                // Execute command
                AddExpenseCommand addExpenseCommand = new AddExpenseCommand(manager, amount);
                commandManager.executeCommand(addExpenseCommand);

                expenseStatusLabel.setText("Expense added: " + description + ", $" + amount + " in category " + category.getCategoryName());
            } catch (NumberFormatException ex) {
                expenseStatusLabel.setText("Invalid expense amount!");
            }
        });

        // View Expenses
        Button viewExpensesButton = new Button("View All Expenses");
        ListView<String> expenseListView = new ListView<>();

        viewExpensesButton.setOnAction(e -> {
            expenseListView.getItems().clear();
            // Convert expenses to strings for ListView
            manager.getExpenses().forEach(expense -> expenseListView.getItems().add("$" + expense));
        });

        // View Remaining Budget
        Button viewBudgetButton = new Button("View Remaining Budget");
        Label remainingBudgetLabel = new Label();

        viewBudgetButton.setOnAction(e -> {
            remainingBudgetLabel.setText("Remaining Budget: $" + manager.getRemainingBudget());
        });

        // Undo/Redo Buttons
        Button undoButton = new Button("Undo");
        Button redoButton = new Button("Redo");
        Label undoRedoStatusLabel = new Label();

        undoButton.setOnAction(e -> {
            commandManager.undo();
            undoRedoStatusLabel.setText("Undo performed.");
        });

        redoButton.setOnAction(e -> {
            commandManager.redo();
            undoRedoStatusLabel.setText("Redo performed.");
        });

        // Add components to layout
        layout.getChildren().addAll(
                budgetLabel, budgetField, setBudgetButton, budgetStatusLabel,
                expenseLabel, expenseDescriptionField, expenseAmountField, categoryDropdown, recurringCheckBox, addExpenseButton, expenseStatusLabel,
                viewExpensesButton, expenseListView,
                viewBudgetButton, remainingBudgetLabel,
                undoButton, redoButton, undoRedoStatusLabel
        );

        // Create the scene
        Scene scene = new Scene(layout, 400, 600);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}