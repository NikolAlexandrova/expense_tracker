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
     import java.util.ArrayList;
      import java.util.List;

      public class BudgetManager {
       private static BudgetManager instance;
       private double budget;
       private double totalExpenses;
       private List<ExpenseEntry> expenses; // List to hold expense entries
       private List<BudgetObserver> observers; // List to hold budget observers

       // Private constructor
       private BudgetManager() {
           expenses = new ArrayList<>();
           observers = new ArrayList<>();
       }

       // Singleton instance retrieval
       public static BudgetManager getInstance() {
           if (instance == null) {
               instance = new BudgetManager();
           }
           return instance;
       }

       // Set the budget
       public void setBudget(double budget) {
           this.budget = budget;
           notifyObservers();
       }

       // Add an expense
       public void addExpense(double amount, String description, String currency, double convertedAmount, String category) {
           totalExpenses += convertedAmount;
           expenses.add(new ExpenseEntry(description, amount, currency, convertedAmount, category));
           notifyObservers();
       }


       // Remove an expense
       public boolean removeExpense(String description, double convertedAmount) {
           for (ExpenseEntry expense : expenses) {
               if (expense.getDescription().equals(description) && expense.getConvertedAmount() == convertedAmount) {
                   expenses.remove(expense);
                   totalExpenses -= convertedAmount;
                   notifyObservers();
                   return true;
               }
           }
           return false;
       }

       // Get remaining budget
       public double getRemainingBudget() {
           return budget - totalExpenses;
       }

       // Get a copy of the expense list
       public List<ExpenseEntry> getExpenses() {
           return new ArrayList<>(expenses);
       }

       // Register a budget observer
       public void registerObserver(BudgetObserver observer) {
           observers.add(observer);
       }

       // Notify all observers
       private void notifyObservers() {
           for (BudgetObserver observer : observers) {
               observer.update(totalExpenses, budget);
              }
          }
      }
     
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
     ```
   - **Explanation:** The `RecurringExpense` class extends the functionality of basic expenses to indicate recurring charges without modifying the `Expense` class directly.

2. **Adapter Pattern**
   - **Purpose:** Adapts external APIs (e.g., mock APIs for currency exchange rates) to work with the application's expense system seamlessly.
   - **Implementation:**
     ```java
     // ExternalCurrencyService
     public class ExternalCurrencyService {
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
     
     // CurrencyAdapter.java
      public interface CurrencyAdapter {
          double convertToBaseCurrency(double amount, String currencyCode);
      }


     // CurrencyAdapterImpl.java
      public class CurrencyAdapterImpl implements CurrencyAdapter {
          private ExternalCurrencyService externalService;

          public CurrencyAdapterImpl(ExternalCurrencyService service) {
              this.externalService = service;
          }

          @Override
          public double convertToBaseCurrency(double amount, String currencyCode) {
              double rate = externalService.getExchangeRate(currencyCode);
              return amount * rate;
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
         void update(double totalExpenses, double budget);
     }

     public class BudgetAlert implements BudgetObserver {
         @Override
          public void update(double totalExpenses, double budget) {
              if (totalExpenses > budget) {
                  System.out.println("Warning: You have exceeded your budget!");
              } else if (totalExpenses > 0.9 * budget) {
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
       private String description;
       private String currency;
       private double convertedAmount;
       private String category; // Added category

       public AddExpenseCommand(BudgetManager manager, double amount, String description, String currency, double convertedAmount, String category) {
           this.manager = manager;
           this.amount = amount;
           this.description = description;
           this.currency = currency;
           this.convertedAmount = convertedAmount;
           this.category = category; // Initialize category
       }

       @Override
       public void execute() {
           manager.addExpense(amount, description, currency, convertedAmount, category); // Pass category
       }

       @Override
       public void undo() {
           boolean success = manager.removeExpense(description, convertedAmount);
           if (!success) {
               System.out.println("Undo failed: Expense not found.");
           }
       }
      }
     
   - **Explanation:** The `Command` interface standardizes how undoable actions are handled, ensuring consistency.

---

# How to Use the Expense Tracker

## Prerequisites
- Ensure **JavaFX** is installed and configured in your IDE.
  - Add the required JavaFX library or SDK to your project.
  - Update your IDE's run configurations to include JavaFX VM options, e.g., `--module-path /path/to/javafx-sdk/lib --add-modules javafx.controls,javafx.fxml`.

---

## 1. Launch the Application
- Open the project in your IDE.
- Run the `Main` class to start the application.

---

## 2. Set a Budget
1. Enter your desired budget in the **"Set Budget"** field.
2. Click **"Set Budget"** to confirm.

---

## 3. Add an Expense
1. Fill out the **"Add Expense"** fields:
   - **Description**, **Amount**, **Currency**, **Category**, and (optional) **Recurring**.
2. The currency will be automatically converted to USD using real-time exchange rates (mocked in this application).
3. Click **"Add Expense"** to save it.
4. A confirmation message will display the added expense, category, and converted amount.

---

## 4. View Expenses
- Click **"View All Expenses"** to see a list of all saved expenses, including their categories, currencies, and converted amounts.

---

## 5. Check Remaining Budget
- Click **"View Remaining Budget"** to see how much budget is left after the converted expenses are deducted.

---

## 6. Undo/Redo Actions
- Click **"Undo"** to reverse the last action or **"Redo"** to reapply a reversed action.
- Confirmation messages will indicate success or failure.

---

## 7. Budget Alerts
- Warnings will display if expenses approach or exceed your budget:
  - "You have exceeded your budget!"
  - "You are approaching your budget limit!"

---
# The application working
<img width="398" alt="Screenshot 2025-01-10 at 20 12 37" src="https://github.com/user-attachments/assets/46828c14-9c0e-407b-9020-0b4144fec46e" />
<img width="395" alt="Screenshot 2025-01-10 at 20 12 58" src="https://github.com/user-attachments/assets/eb7334f6-4cbe-400d-8aad-5acd8c73b7c9" />


