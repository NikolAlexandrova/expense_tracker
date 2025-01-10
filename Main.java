import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {
    private BudgetManager manager = BudgetManager.getInstance();
    private CurrencyAdapter currencyAdapter = new CurrencyAdapterImpl(new ExternalCurrencyService());
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
        ComboBox<String> currencyDropdown = new ComboBox<>();
        currencyDropdown.getItems().addAll("USD", "EUR", "GBP");
        currencyDropdown.setValue("USD");
        ComboBox<String> categoryDropdown = new ComboBox<>();
        categoryDropdown.getItems().addAll("Food", "Transport", "Shopping", "Entertainment");
        categoryDropdown.setPromptText("Select Category");
        CheckBox recurringCheckBox = new CheckBox("Recurring");
        Button addExpenseButton = new Button("Add Expense");
        Label expenseStatusLabel = new Label();

        addExpenseButton.setOnAction(e -> {
            try {
                String description = expenseDescriptionField.getText();
                double amount = Double.parseDouble(expenseAmountField.getText());
                String selectedCurrency = currencyDropdown.getValue();
                String categoryName = categoryDropdown.getValue();
                double convertedAmount = currencyAdapter.convertToBaseCurrency(amount, selectedCurrency);

                if (categoryName == null) {
                    expenseStatusLabel.setText("Please select a category!");
                    return;
                }

                ExpenseCategory category = ExpenseCategoryFactory.createCategory(categoryName);

                // Create and execute the command for adding an expense
                AddExpenseCommand addExpenseCommand = new AddExpenseCommand(
                        manager, amount, description, selectedCurrency, convertedAmount, category.getCategoryName()
                );
                commandManager.executeCommand(addExpenseCommand);

                expenseStatusLabel.setText("Expense added: " + description + " (" + selectedCurrency + " " + amount + " -> USD $" + convertedAmount + ", Category: " + category.getCategoryName() + ")");
            } catch (NumberFormatException ex) {
                expenseStatusLabel.setText("Invalid expense amount!");
            }
        });

        // View Expenses
        Label expensesLabel = new Label("Expenses:");
        ListView<String> expenseListView = new ListView<>();

        Button viewExpensesButton = new Button("View All Expenses");
        viewExpensesButton.setOnAction(e -> {
            expenseListView.getItems().clear();
            manager.getExpenses().forEach(expense -> expenseListView.getItems().add(expense.toString()));
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
            if (!commandManager.getUndoStack().isEmpty()) {
                commandManager.undo();
                undoRedoStatusLabel.setText("Undo performed.");
            } else {
                undoRedoStatusLabel.setText("No actions to undo.");
            }
        });

        redoButton.setOnAction(e -> {
            if (!commandManager.getRedoStack().isEmpty()) {
                commandManager.redo();
                undoRedoStatusLabel.setText("Redo performed.");
            } else {
                undoRedoStatusLabel.setText("No actions to redo.");
            }
        });

        // Add components to layout
        layout.getChildren().addAll(
                budgetLabel, budgetField, setBudgetButton, budgetStatusLabel,
                expenseLabel, expenseDescriptionField, expenseAmountField, currencyDropdown, categoryDropdown, recurringCheckBox, addExpenseButton, expenseStatusLabel,
                expensesLabel, expenseListView,
                viewExpensesButton, viewBudgetButton, remainingBudgetLabel,
                undoButton, redoButton, undoRedoStatusLabel
        );

        // Create the scene
        Scene scene = new Scene(layout, 400, 700);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}