import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.List;

public class Main extends Application {
    private BudgetManager manager = BudgetManager.getInstance();

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
        CheckBox recurringCheckBox = new CheckBox("Recurring");
        Button addExpenseButton = new Button("Add Expense");
        Label expenseStatusLabel = new Label();

        addExpenseButton.setOnAction(e -> {
            try {
                String description = expenseDescriptionField.getText();
                double amount = Double.parseDouble(expenseAmountField.getText());
                Expense expense = new BasicExpense(description, amount);

                if (recurringCheckBox.isSelected()) {
                    expense = new RecurringExpense(expense);
                }

                manager.addExpense(amount); // Add to BudgetManager
                expenseStatusLabel.setText("Expense added: " + expense.getDetails() + ", $" + expense.getAmount());
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

        // Add components to layout
        layout.getChildren().addAll(
                budgetLabel, budgetField, setBudgetButton, budgetStatusLabel,
                expenseLabel, expenseDescriptionField, expenseAmountField, recurringCheckBox, addExpenseButton, expenseStatusLabel,
                viewExpensesButton, expenseListView,
                viewBudgetButton, remainingBudgetLabel
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
