import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerManagementSystem {
    private static final String CSV_FILE_PATH = "customers.csv";
    private static final String CSV_HEADER = "id,name,city,email,phone";

    public static void main(String[] args) {
        try {
            initializeCSVFile();
            displayMenu();
        } catch (IOException e) {
            System.err.println("Error initializing the CSV file: " + e.getMessage());
        }
    }

    private static void initializeCSVFile() throws IOException {
        File file = new File(CSV_FILE_PATH);
        if (!file.exists()) {
            file.createNewFile();
            FileWriter writer = new FileWriter(file);
            writer.write(CSV_HEADER + "\n");
            writer.close();
        }
    }

    private static void displayMenu() {
        Scanner scanner = new Scanner(System.in);
        int choice;

        do {
            System.out.println("\nMenu:");
            System.out.println("1. Add new customer");
            System.out.println("2. View all customers");
            System.out.println("3. Search customers by city");
            System.out.println("4. Delete a customer (by ID)");
            System.out.println("5. Search customers by ID and edit/update details");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");

            choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    addNewCustomer();
                    break;
                case 2:
                    viewAllCustomers();
                    break;
                case 3:
                    searchCustomersByCity();
                    break;
                case 4:
                    deleteCustomerById();
                    break;
                case 5:
                    searchAndEditCustomer();
                    break;
                case 6:
                    System.out.println("Exiting the application. Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 6.");
            }
        } while (choice != 6);

        scanner.close();
    }

    private static void addNewCustomer() {
        Scanner scanner = new Scanner(System.in);

        try (FileWriter writer = new FileWriter(CSV_FILE_PATH, true)) {
            System.out.println("Enter customer details:");
            System.out.print("Name: ");
            String name = scanner.nextLine();

            System.out.print("City: ");
            String city = scanner.nextLine();

            System.out.print("Email: ");
            String email = scanner.nextLine();

            System.out.print("Phone: ");
            String phone = scanner.nextLine();

            int nextId = getNextCustomerId();
            String newCustomer = String.format("%d,%s,%s,%s,%s", nextId, name, city, email, phone);

            writer.write(newCustomer + "\n");
            System.out.println("Customer added successfully!");
        } catch (IOException e) {
            System.err.println("Error adding a new customer: " + e.getMessage());
        }
    }

    private static void viewAllCustomers() {
        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }
        } catch (IOException e) {
            System.err.println("Error viewing all customers: " + e.getMessage());
        }
    }

    private static void searchCustomersByCity() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter city to search: ");
        String searchCity = scanner.nextLine();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                if (line.toLowerCase().contains(searchCity.toLowerCase())) {
                    System.out.println(line);
                    found = true;
                }
            }

            if (!found) {
                System.out.println("No customers found in the specified city.");
            }
        } catch (IOException e) {
            System.err.println("Error searching customers by city: " + e.getMessage());
        }
    }

    private static void deleteCustomerById() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer ID to delete: ");
        int deleteId = scanner.nextInt();

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int currentId = Integer.parseInt(parts[0].trim());

                if (currentId != deleteId) {
                    lines.add(line);
                }
            }
        } catch (IOException e) {
            System.err.println("Error deleting customer by ID: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(CSV_FILE_PATH)) {
            writer.write(CSV_HEADER + "\n");
            for (String line : lines) {
                writer.write(line + "\n");
            }
            System.out.println("Customer deleted successfully!");
        } catch (IOException e) {
            System.err.println("Error updating customer file after deletion: " + e.getMessage());
        }
    }

    private static void searchAndEditCustomer() {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Enter customer ID to edit: ");
        int editId = scanner.nextInt();

        List<String> lines = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            boolean found = false;

            while ((line = reader.readLine()) != null) {
                String[] parts = line.split(",");
                int currentId = Integer.parseInt(parts[0].trim());

                if (currentId == editId) {
                    found = true;
                    System.out.println("Current details:\n" + line);

                    // Get updated details from the user
                    System.out.println("Enter new details:");
                    System.out.print("Name: ");
                    String name = scanner.nextLine();

                    System.out.print("City: ");
                    String city = scanner.nextLine();

                    System.out.print("Email: ");
                    String email = scanner.nextLine();

                    System.out.print("Phone: ");
                    String phone = scanner.nextLine();

                    String updatedDetails = String.format("%d,%s,%s,%s,%s", editId, name, city, email, phone);
                    lines.add(updatedDetails);
                    System.out.println("Customer details updated successfully!");
                } else {
                    lines.add(line);
                }
            }

            if (!found) {
                System.out.println("No customer found with the specified ID.");
            }
        } catch (IOException e) {
            System.err.println("Error searching and editing customer by ID: " + e.getMessage());
        }

        try (FileWriter writer = new FileWriter(CSV_FILE_PATH)) {
            writer.write(CSV_HEADER + "\n");
            for (String line : lines) {
                writer.write(line + "\n");
            }
        } catch (IOException e) {
            System.err.println("Error updating customer file after editing: " + e.getMessage());
        }
    }

    private static int getNextCustomerId() {
        int maxId = 0;

        try (BufferedReader reader = new BufferedReader(new FileReader(CSV_FILE_PATH))) {
            String line;
            while ((line = reader.readLine()) != null) {
                if (!line.equals(CSV_HEADER)) {
                    String[] parts = line.split(",");
                    int currentId = Integer.parseInt(parts[0].trim());
                    maxId = Math.max(maxId, currentId);
                }
            }
        } catch (IOException e) {
            System.err.println("Error getting the next customer ID: " + e.getMessage());
        }

        return maxId + 1;
    }
}
