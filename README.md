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
## Contributions

| Team Member       | Contributions                                                                 |
|-------------------|------------------------------------------------------------------------------|
| [Nicole Alexandrova]       | - Implemented the Singleton pattern for `BudgetManager` to manage expenses and budgets globally. <br> - Implemented the Observer pattern to send budget alerts when expenses exceed or approach the budget limit. <br> - Contributed to testing and debugging the program. |
| [Gabriella Khayutin]   | - Implemented the Factory Method to dynamically create expense categories (e.g., Food, Transport, Entertainment). <br> - Implemented the Command pattern to handle undo/redo functionality for adding and removing expenses. <br> - Contributed to user interaction logic and debugging. |



