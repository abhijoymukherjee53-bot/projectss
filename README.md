# projects
import java.io.*;
import java.time.LocalDate;
import java.util.*;
public class Main {

    static Scanner sc = new Scanner(System.in);
    static ArrayList<Expense> expenses = new ArrayList<>();
    static String fileName;
    static double total = 0;
    
    public static void main(String[] args) {
    
        int year = LocalDate.now().getYear();
        fileName = "expenses_" + year + ".txt";
        loadExpenses();
        
        while (true) {
            System.out.println("\n========== Expense Tracker ==========");
            System.out.println("1. Add Expense");
            System.out.println("2. View Expenses");
            System.out.println("3. Total Expense");
            System.out.println("4. Exit");
            System.out.print("Choice: ");
            
            int choice = sc.nextInt();
            
            sc.nextLine();
            switch (choice) {
                case 1:
                    addExpense();
                    break;
                    
                case 2:
                    viewExpenses();
                    break;
                    
                case 3:
                    totalExpense();
                    break;
                    
                case 4:
                    System.out.println("Thankyou");
                    return;
                    
                default:
                    System.out.println("Invalid Choice.");
            }
        }
    }

    static void addExpense() {
        System.out.print("Date (DD-MM-YYYY): ");
        String date = sc.nextLine();

        System.out.print("Category: ");
        String category = sc.nextLine();

        System.out.print("Description: ");
        String description = sc.nextLine();

        System.out.print("Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();

        Expense e = new Expense(date, category, description, amount);
        expenses.add(e);
        total += amount;
        saveExpense(e);
        System.out.println("Expense Saved Successfully.");
    }

    static void viewExpenses() {
        if (expenses.isEmpty()) {
            System.out.println("No Expenses Found.");
            return;
        }
        for (Expense e : expenses) {
            System.out.println(e);
        }
    }
    static void totalExpense() {
        System.out.println("Total = ₹" + total);
    }
    static void saveExpense(Expense e) {
        try (FileWriter fw = new FileWriter(fileName, true)) {
            fw.write(e.toFileString() + "\n");
        } catch (IOException ex) {
            System.out.println("Error Saving File");
        }
    }
    static void loadExpenses() {
        try {
            File file = new File(fileName);
            if (!file.exists()) return;
            Scanner reader = new Scanner(file);
            while (reader.hasNextLine()) {
                String line = reader.nextLine();
                String[] data = line.split(",", 4);
                if (data.length == 4) {
                    Expense e = new Expense(
                            data[0],
                            data[1],
                            data[2],
                            Double.parseDouble(data[3]));
                    expenses.add(e);
                    total += e.getAmount();
                }
            }
            reader.close();
        } catch (Exception e) {
            System.out.println("Error Loading Data");
        }
    }
}

class Expense {
    private String date;
    private String category;
    private String description;
    private double amount;

    public Expense(String date, String category, String description, double amount) {
        this.date = date;
        this.category = category;
        this.description = description;
        this.amount = amount;
    }

    public double getAmount() {
        return amount;
    }

    public String getDate() {
        return date;
    }

    public String toFileString() {
        return date + "," + category + "," + description + "," + amount;
    }

    @Override
    public String toString() {
        return date + " | " + category + " | " + description + " | ₹" + amount;
    }
}
