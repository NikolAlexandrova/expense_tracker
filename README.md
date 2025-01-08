# Expense Tracker with Budget Analysis

## Description
The Expense Tracker is a Java application designed to help users manage their expenses and budgets. The program demonstrates the use of multiple software design patterns to achieve modularity, scalability, and maintainability. Users can add expenses, categorize them, view their total spending, and compare it to a preset budget. Alerts are provided when the budget is exceeded.

This project was developed collaboratively to fulfill the requirements of a design pattern assignment.

---

## Design Patterns Used

### Creational Patterns
- **Singleton:** Ensures only one instance of the `BudgetManager` exists throughout the application, managing expenses and budgets.
- **Factory Method:** Dynamically creates expense categories such as "Food," "Transport," and "Entertainment" based on user input.

### Structural Patterns
- **Decorator:** Adds optional features to expenses, such as recurring expenses or tags.
- **Adapter:** Integrates external data or mock APIs for additional functionalities (e.g., exchange rates for different currencies).

### Behavioral Patterns
- **Observer:** Alerts the user when their spending approaches or exceeds the budget.
- **Command:** Implements undo/redo functionality for actions like adding or removing expenses.
- **State (Optional):** Represents budget states (Under Budget, Approaching Budget, Over Budget).

---

## Team members
| Name  | Role |
| ------------- | ------------- |
| Nicole Alexandrova  | Developer (Singleton, Observer, and BudgetManager functionality)  |
| Gabriella Khayutin  | Developer (Factory Method, Command, and user interaction handling)  |

---

## Contributions

| Team Member       | Contributions                                                                 |
|-------------------|------------------------------------------------------------------------------|
| [Nicole Alexandrova]       | - Implemented the Singleton pattern for `BudgetManager` to manage expenses and budgets globally. <br> - Implemented the Observer pattern to send budget alerts when expenses exceed or approach the budget limit. <br> - Contributed to testing and debugging the program. |
| [Gabriella Khayutin]   | - Implemented the Factory Method to dynamically create expense categories (e.g., Food, Transport, Entertainment). <br> - Implemented the Command pattern to handle undo/redo functionality for adding and removing expenses. <br> - Contributed to user interaction logic and debugging. |


## Design Patterns code + explaination

### **Creational Patterns**
1. **Singleton Pattern**
   - **Purpose:** Ensures only one instance of the `BudgetManager` exists globally.
   - **Implementation:**
     ```java
     public class BudgetManager {
         private static BudgetManager instance;
         private double budget;
         private List<Double> expenses;

         private BudgetManager() {
             expenses = new ArrayList<>();
         }

         public static BudgetManager getInstance() {
             if (instance == null) {
                 instance = new BudgetManager();
             }
             return instance;
         }

         public void setBudget(double budget) {
             this.budget = budget;
         }

         public double getRemainingBudget() {
             return budget - expenses.stream().mapToDouble(Double::doubleValue).sum();
         }

         public void addExpense(double amount) {
             expenses.add(amount);
         }
     }
     ```
   - **Explanation:** The `BudgetManager` ensures all expense and budget management is handled by a single instance, preventing duplication of budget data across the application.

2. **Factory Method**
   - **Purpose:** Dynamically creates different expense categories (e.g., Food, Transport, Shopping).
   - **Implementation:**
     ```java
     public class ExpenseCategoryFactory {
         public static ExpenseCategory createCategory(String type) {
             switch (type) {
                 case "Food":
                     return new FoodCategory();
                 case "Transport":
                     return new TransportCategory();
                 case "Shopping":
                     return new ShoppingCategory();
                 default:
                     throw new IllegalArgumentException("Unknown category type");
             }
         }
     }
     ```
   - **Explanation:** The factory method allows for flexibility and scalability when adding new categories without modifying existing code.

---

### **Structural Patterns**
1. **Decorator Pattern**
   - **Purpose:** Adds optional features to expenses (e.g., recurring expenses).
   - **Implementation:**
     ```java
     public abstract class ExpenseDecorator extends Expense {
         protected Expense decoratedExpense;

         public ExpenseDecorator(Expense decoratedExpense) {
             this.decoratedExpense = decoratedExpense;
         }

         @Override
         public double getAmount() {
             return decoratedExpense.getAmount();
         }

         @Override
         public String getDetails() {
             return decoratedExpense.getDetails();
         }
     }

     public class RecurringExpense extends ExpenseDecorator {
         public RecurringExpense(Expense decoratedExpense) {
             super(decoratedExpense);
         }

         @Override
         public String getDetails() {
             return decoratedExpense.getDetails() + " (Recurring)";
         }
     }
     ```
   - **Explanation:** The `RecurringExpense` class extends the functionality of basic expenses to indicate recurring charges without modifying the `Expense` class directly.

2. **Adapter Pattern**
   - **Purpose:** Adapts external APIs (e.g., mock APIs for currency exchange rates) to work with the application's expense system seamlessly.
   - **Implementation:**
     ```java
     // External Mock API
     public class MockCurrencyAPI {
         public double getExchangeRate(String currency) {
             // Simulates an API call for exchange rates
             switch (currency) {
                 case "USD":
                     return 1.0;
                 case "EUR":
                     return 0.85;
                 case "GBP":
                     return 0.75;
                 default:
                     return 1.0; // Default to USD
             }
         }
     }

     // Adapter Interface
     public interface CurrencyConverter {
         double convert(double amount);
     }

     // Adapter Class
     public class CurrencyAdapter implements CurrencyConverter {
         private MockCurrencyAPI mockCurrencyAPI;
         private String targetCurrency;

         public CurrencyAdapter(MockCurrencyAPI mockCurrencyAPI, String targetCurrency) {
             this.mockCurrencyAPI = mockCurrencyAPI;
             this.targetCurrency = targetCurrency;
         }

         @Override
         public double convert(double amount) {
             double exchangeRate = mockCurrencyAPI.getExchangeRate(targetCurrency);
             return amount * exchangeRate;
         }
     }

     // Example Usage
     public class CurrencyAdapterExample {
         public static void main(String[] args) {
             MockCurrencyAPI mockAPI = new MockCurrencyAPI();
             CurrencyAdapter adapter = new CurrencyAdapter(mockAPI, "EUR");

             double amountInUSD = 100;
             double amountInEUR = adapter.convert(amountInUSD);

             System.out.println("Amount in USD: $" + amountInUSD);
             System.out.println("Amount in EUR: €" + amountInEUR);
         }
     }
     ```
   - **Explanation:** The `CurrencyAdapter` bridges the gap between the application's internal logic and the `MockCurrencyAPI`. It allows the system to seamlessly convert expense amounts into different currencies without altering the core functionality of the application.


---

### **Behavioral Patterns**
1. **Observer Pattern**
   - **Purpose:** Sends alerts when expenses approach or exceed the budget.
   - **Implementation:**
     ```java
     public interface BudgetObserver {
         void update(double remainingBudget);
     }

     public class BudgetAlert implements BudgetObserver {
         @Override
         public void update(double remainingBudget) {
             if (remainingBudget < 50) {
                 System.out.println("Alert: You are approaching your budget limit!");
             }
         }
     }
     ```
   - **Explanation:** The `BudgetObserver` interface and `BudgetAlert` class ensure the system reacts dynamically to budget changes.
  
  
2. **Command Pattern**
   - **Purpose:** Implements undo/redo functionality for expense management.
   - **Implementation:**
     ```java
     public interface Command {
         void execute();
         void undo();
     }

     public class AddExpenseCommand implements Command {
         private BudgetManager manager;
         private double amount;

         public AddExpenseCommand(BudgetManager manager, double amount) {
             this.manager = manager;
             this.amount = amount;
         }

         @Override
         public void execute() {
             manager.addExpense(amount);
         }

         @Override
         public void undo() {
             manager.removeExpense(amount);
         }
     }
     ```
   - **Explanation:** The `Command` interface standardizes how undoable actions are handled, ensuring consistency.

---

### **How to Run**
1. Ensure you have JavaFX installed and configured.
2. Launch the application from the `Main.java` file.
3. Test:
   - Set a budget.
   - Add expenses with categories.
   - Use "Undo" and "Redo" to test the command pattern.
   - View alerts when the budget is exceeded.

---

