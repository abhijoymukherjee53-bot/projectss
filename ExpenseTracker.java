import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class ExpenseTracker {

    private static final Scanner scanner = new Scanner(System.in);
    private static final List<Expense> expenses = new ArrayList<>();

    private static final DateTimeFormatter DATE_FORMAT =
            DateTimeFormatter.ofPattern("dd-MM-yyyy");

    // Expense class
    static class Expense {
        private int id;
        private String category;
        private String description;
        private double amount;
        private LocalDate date;

        public Expense(int id, String category, String description,
                       double amount, LocalDate date) {
            this.id = id;
            this.category = category;
            this.description = description;
            this.amount = amount;
            this.date = date;
        }

        public int getId() {
            return id;
        }

        public String getCategory() {
            return category;
        }

        public String getDescription() {
            return description;
        }

        public double getAmount() {
            return amount;
        }

        public LocalDate getDate() {
            return date;
        }
    }

    public static void main(String[] args) {

        int choice;

        System.out.println("==========================================");
        System.out.println("          EXPENSE TRACKER APPLICATION");
        System.out.println("==========================================");

        do {
            displayMenu();

            choice = readInteger("Enter your choice: ");

            switch (choice) {

                case 1:
                    addExpense();
                    break;

                case 2:
                    viewExpenses();
                    break;

                case 3:
                    searchByCategory();
                    break;

                case 4:
                    showTotalExpenses();
                    break;

                case 5:
                    deleteExpense();
                    break;

                case 6:
                    System.out.println("\nThank you for using Expense Tracker!");
                    break;

                default:
                    System.out.println("\nInvalid choice. Please enter 1-6.");
            }

        } while (choice != 6);

        scanner.close();
    }

    // Display menu
    private static void displayMenu() {

        System.out.println("\n--------------- MENU ----------------");
        System.out.println("1. Add Expense");
        System.out.println("2. View All Expenses");
        System.out.println("3. Search by Category");
        System.out.println("4. Show Total Expenses");
        System.out.println("5. Delete Expense");
        System.out.println("6. Exit");
        System.out.println("-------------------------------------");
    }

    // Add expense
    private static void addExpense() {

        System.out.println("\n========== ADD EXPENSE ==========");

        String category = readText("Enter category: ");
        String description = readText("Enter description: ");
        double amount = readPositiveAmount("Enter amount: Rs. ");
        LocalDate date = readDate();

        int id = expenses.size() + 1;

        Expense expense = new Expense(
                id,
                category,
                description,
                amount,
                date
        );

        expenses.add(expense);

        System.out.println("\n Expenses added successfully!");
    }

    // View expenses
    private static void viewExpenses() {

        System.out.println("\n========== ALL EXPENSES ==========");

        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        System.out.printf(
                "%-5s %-15s %-20s %-12s %-12s%n",
                "ID", "Category", "Description", "Amount", "Date"
        );

        System.out.println(
                "----------------------------------------------------------------"
        );

        for (Expense expense : expenses) {

            System.out.printf(
                    "%-5d %-15s %-20s ₹%-11.2f %-12s%n",
                    expense.getId(),
                    expense.getCategory(),
                    expense.getDescription(),
                    expense.getAmount(),
                    expense.getDate().format(DATE_FORMAT)
            );
        }

        System.out.println(
                "----------------------------------------------------------------"
        );
    }

    // Search expenses by category
    private static void searchByCategory() {

        System.out.println("\n======= SEARCH BY CATEGORY =======");

        if (expenses.isEmpty()) {
            System.out.println("No expenses recorded.");
            return;
        }

        String category = readText("Enter category: ");

        boolean found = false;
        double categoryTotal = 0;

        System.out.println("\nExpenses under: " + category);

        for (Expense expense : expenses) {

            if (expense.getCategory()
                    .equalsIgnoreCase(category)) {

                System.out.println(
                        "ID: " + expense.getId()
                        + " | Description: " + expense.getDescription()
                        + " | Amount: ₹" + expense.getAmount()
                        + " | Date: "
                        + expense.getDate().format(DATE_FORMAT)
                );

                categoryTotal += expense.getAmount();
                found = true;
            }
        }

        if (!found) {
            System.out.println("No expenses found in this category.");
        } else {
            System.out.printf(
                    "Category Total: ₹%.2f%n",
                    categoryTotal
            );
        }
    }

    // Calculate total expenses
    private static void showTotalExpenses() {

        double total = 0;

        for (Expense expense : expenses) {
            total += expense.getAmount();
        }

        System.out.println("\n======= EXPENSE SUMMARY =======");

        System.out.println(
                "Number of expenses: " + expenses.size()
        );

        System.out.printf(
                "Total expenses: ₹%.2f%n",
                total
        );
    }

    // Delete expense
    private static void deleteExpense() {

        System.out.println("\n========== DELETE EXPENSE ==========");

        if (expenses.isEmpty()) {
            System.out.println("No expenses available.");
            return;
        }

        int id = readInteger("Enter Expense ID to delete: ");

        Expense expenseToDelete = null;

        for (Expense expense : expenses) {

            if (expense.getId() == id) {
                expenseToDelete = expense;
                break;
            }
        }

        if (expenseToDelete != null) {

            expenses.remove(expenseToDelete);

            System.out.println(
                    "\n✓ Expense deleted successfully!"
            );

        } else {

            System.out.println(
                    "\nExpense ID not found."
            );
        }
    }

    // Read text input
    private static String readText(String message) {

        while (true) {

            System.out.print(message);

            String input = scanner.nextLine().trim();

            if (!input.isEmpty()) {
                return input;
            }

            System.out.println(
                    "Input cannot be empty. Please try again."
            );
        }
    }

    // Read positive amount
    private static double readPositiveAmount(String message) {

        while (true) {

            System.out.print(message);

            try {

                double amount =
                        Double.parseDouble(scanner.nextLine());

                if (amount > 0) {
                    return amount;
                }

                System.out.println(
                        "Amount must be greater than zero."
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid amount."
                );
            }
        }
    }

    // Read integer
    private static int readInteger(String message) {

        while (true) {

            System.out.print(message);

            try {

                return Integer.parseInt(
                        scanner.nextLine()
                );

            } catch (NumberFormatException e) {

                System.out.println(
                        "Please enter a valid number."
                );
            }
        }
    }

    // Read date
    private static LocalDate readDate() {

        while (true) {

            System.out.print(
                    "Enter date (DD-MM-YYYY): "
            );

            String input = scanner.nextLine().trim();

            try {

                return LocalDate.parse(
                        input,
                        DATE_FORMAT
                );

            } catch (DateTimeParseException e) {

                System.out.println(
                        "Invalid date. Use DD-MM-YYYY."
                );
            }
        }
    }
}