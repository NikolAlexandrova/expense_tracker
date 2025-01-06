public class ExpenseCategoryFactory {
    public static ExpenseCategory createCategory(String type) {
        switch (type.toLowerCase()) {
            case "food":
                return new FoodCategory();
            case "transport":
                return new TransportCategory();
            case "shopping":
                return new ShoppingCategory();
            default:
                throw new IllegalArgumentException("Invalid category type: " + type);
        }
    }
}