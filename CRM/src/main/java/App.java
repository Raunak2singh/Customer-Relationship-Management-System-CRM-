import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.cfg.Configuration;
import dao.*;
import entities.*;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

public class App {

    private static final Scanner scanner = new Scanner(System.in);

    public static void main(String[] args) {
        try {
            // Create a Hibernate Configuration object and load configuration from hibernate.cfg.xml
            Configuration config = new Configuration().configure("/resource/hibernate.cfg.xml");
            // Create a SessionFactory using the configuration
            SessionFactory sfg = config.buildSessionFactory();

            // Create DAO instances for all entities
            CustomerDAO customerDAO = new CustomerDAOImpl(sfg);
            OpportunityDAO opportunityDAO = new OpportunityDAOImpl(sfg);
            TaskDAO taskDAO = new TaskDAOImpl(sfg);
            ProductDAO productDAO = new ProductDAOImpl(sfg);

            while (true) {
                displayMenu();
                int choice = readChoice();

                switch (choice) {
                    case 1:
                        viewCustomers(customerDAO);
                        break;
                    case 2:
                        viewOpportunities(opportunityDAO);
                        break;
                    case 3:
                        viewTasks(taskDAO);
                        break;
                    case 4:
                        viewProducts(productDAO, opportunityDAO);
                        break;
                    case 5:
                        addCustomer(customerDAO);
                        break;
                    case 6:
                        addOpportunity(opportunityDAO, customerDAO);
                        break;
                    case 7:
                        addProductToOpportunity(opportunityDAO, productDAO);
                        break;
                    case 10:
                        addTask(taskDAO, customerDAO, opportunityDAO);
                        break;
                    case 11:
                        addProduct(productDAO);
                        break;
                    case 12:
                        updateCustomer(customerDAO);
                        break;
                    case 13:
                        updateOpportunity(opportunityDAO, customerDAO);
                        break;
                    case 14:
                        updateTask(taskDAO, customerDAO, opportunityDAO);
                        break;
                    case 15:
                        deleteCustomer(customerDAO);
                        break;
                    case 16:
                        deleteOpportunity(opportunityDAO);
                        break;
                    case 17:
                        deleteTask(taskDAO);
                        break;
                    case 18:
                        viewCustomersOpportunities(customerDAO,opportunityDAO);
                        break;
                    case 19:
                        viewOpportunityTasks(opportunityDAO);
                        break;
                    case 0:
                        // Exit the program
                        System.out.println("Exiting...");
                        sfg.close(); // Close the SessionFactory
                        return;
                    default:
                        System.out.println("Invalid choice. Please try again.");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void displayMenu() {
        System.out.println("=== CRM System Menu ===");
        System.out.println("1. View Customers");
        System.out.println("2. View Opportunities");
        System.out.println("3. View Tasks");
        System.out.println("4. View Products");
        System.out.println("6. Add Customer");
        System.out.println("8. Add Opportunity");
        System.out.println("9. Add Product to Opportunity");
        System.out.println("10. Add Task");
        System.out.println("11. Add Product");
        System.out.println("12. Update Customer");
        System.out.println("13. Update Opportunity");
        System.out.println("14. Update Task");
        System.out.println("15. Delete Customer");
        System.out.println("16. Delete Opportunity");
        System.out.println("17. Delete Task");
        System.out.println("18. View Customer's Opportunities");
        System.out.println("19. View Opportunity's Tasks");
        System.out.println("0. Exit");
        System.out.println("========================");
        System.out.print("Enter your choice: ");
    }

    private static int readChoice() {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Consume invalid input
            return -1;
        }
    }

    private static void viewCustomers(CustomerDAO customerDAO) {
        List<Customer> customers = customerDAO.getAll();
        if (customers != null && !customers.isEmpty()) {
            System.out.println("=== Customers ===");
            for (Customer customer : customers) {
                System.out.println(customer.getId() + ". " + customer.getName());
            }
        } else {
            System.out.println("No customers found.");
        }
    }

    private static void viewOpportunities(OpportunityDAO opportunityDAO) {
        // Implement viewOpportunities functionality here
        List<Opportunity> opportunities = opportunityDAO.getAll();

    if (opportunities != null && !opportunities.isEmpty()) {
        System.out.println("=== Opportunities ===");
        for (Opportunity opportunity : opportunities) {
            System.out.println("ID: " + opportunity.getId());
            System.out.println("Name: " + opportunity.getName());
            System.out.println("Customer: " + opportunity.getCustomer().getName()); // Assuming there's a getName() method in Customer
            // Display other relevant information about the opportunity
            System.out.println();
        }
    } else {
        System.out.println("No opportunities found.");
    }
    }

    private static void viewTasks(TaskDAO taskDAO) {
        List<Task> tasks = taskDAO.getAll();
    
        if (tasks != null && !tasks.isEmpty()) {
            System.out.println("=== Tasks ===");
            for (Task task : tasks) {
                System.out.println("ID: " + task.getId());
                System.out.println("Name: " + task.getName());
                System.out.println("Customer: " + task.getCustomer().getName());
                System.out.println("Opportunity: " + task.getOpportunity().getName());
                // Display other relevant information about the task
                System.out.println();
            }
        } else {
            System.out.println("No tasks found.");
        }
    }

    private static void viewProducts(ProductDAO productDAO,OpportunityDAO opportunityDAO) {
        List<Product> products = productDAO.getAll();

    if (products != null && !products.isEmpty()) {
        System.out.println("=== Products ===");
        for (Product product : products) {
            System.out.println("ID: " + product.getId());
            System.out.println("Name: " + product.getName());
            System.out.println("Opportunities: ");
            List<Opportunity> opportunities = opportunityDAO.getByProduct(product);
            if (opportunities != null && !opportunities.isEmpty()) {
                for (Opportunity opportunity : opportunities) {
                    System.out.println("- Opportunity: " + opportunity.getName());
                }
            } else {
                System.out.println("- No opportunities found for this product.");
            }
            System.out.println(); // Add a blank line for readability
        }
    } else {
        System.out.println("No products found.");
     }
    }



    private static void addCustomer(CustomerDAO customerDAO) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter customer name:");
        String name = scanner.nextLine();
    
        // Create a new Customer object
        Customer newCustomer = new Customer();
        newCustomer.setName(name);
    
        // Save the new customer using the CustomerDAO
        customerDAO.save(newCustomer);
    
        System.out.println("Customer added successfully.");
    }

    private static void addOpportunity(OpportunityDAO opportunityDAO, CustomerDAO customerDAO) {
    Scanner scanner = new Scanner(System.in);

    System.out.println("Enter customer ID for the opportunity:");
    long customerId = scanner.nextLong();
    scanner.nextLine(); // Consume newline character

    // Get the customer from the database using the CustomerDAO
    Customer customer = customerDAO.getById(customerId);
    if (customer == null) {
        System.out.println("Customer not found.");
        return;
    }

    System.out.println("Enter opportunity name:");
    String opportunityName = scanner.nextLine();

    // Create a new Opportunity object
    Opportunity newOpportunity = new Opportunity();
    newOpportunity.setCustomer(customer);
    newOpportunity.setName(opportunityName);

    // Save the new opportunity using the OpportunityDAO
    opportunityDAO.save(newOpportunity);

    System.out.println("Opportunity added successfully.");
    }

    private static void addTask(TaskDAO taskDAO, CustomerDAO customerDAO, OpportunityDAO opportunityDAO) {
        Scanner scanner = new Scanner(System.in);

        // Get customer ID
        System.out.println("Enter customer ID for the task:");
        int customerId = (int) scanner.nextLong();
        scanner.nextLine(); // Consume newline character
    
        // Get opportunity ID
        System.out.println("Enter opportunity ID for the task:");
        int opportunityId = (int) scanner.nextLong();
        scanner.nextLine(); // Consume newline character
    
        // Retrieve customer and opportunity objects
        Customer customer = customerDAO.getById(customerId);
        Opportunity opportunity = opportunityDAO.getById(opportunityId);
    
        if (customer == null || opportunity == null) {
            System.out.println("Customer or opportunity not found.");
            return;
        }
    
        // Create a new Task object
        Task newTask = new Task();
        System.out.println("Enter task name:");
        String taskName = scanner.nextLine();
        newTask.setName(taskName);
        newTask.setCustomer(customer);
        newTask.setOpportunity(opportunity);
    
        // Save the new task using the TaskDAO
        taskDAO.save(newTask);
    
        System.out.println("Task added successfully.");
    
    }


    private static void addProduct(ProductDAO productDAO) {
        Scanner scanner = new Scanner(System.in);
    
        System.out.println("Enter product name:");
        String name = scanner.nextLine();
    
        // Create a new Product object
        Product newProduct = new Product();
        newProduct.setName(name);
    
        // Save the new product using the ProductDAO
        productDAO.save(newProduct);
    
        System.out.println("Product added successfully.");
    }

    private static void updateCustomer(CustomerDAO customerDAO) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the ID of the customer to update
        System.out.println("Enter the ID of the customer to update:");
        long customerId = scanner.nextLong();
        scanner.nextLine(); // Consume newline character
    
        // Retrieve the customer object from the database
        Customer customer = customerDAO.getById(customerId);
    
        if (customer != null) {
            // Prompt the user to enter the updated details
            System.out.println("Enter the updated name:");
            String updatedName = scanner.nextLine();
    
            // Update the customer object with the new details
            customer.setName(updatedName);
    
            // Save the updated customer object
            customerDAO.update(customer);
    
            System.out.println("Customer updated successfully.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void updateOpportunity(OpportunityDAO opportunityDAO, CustomerDAO customerDAO) {
        Scanner scanner = new Scanner(System.in);

    // Prompt the user to enter the ID of the opportunity to update
    System.out.println("Enter the ID of the opportunity to update:");
    long opportunityId = scanner.nextLong();
    scanner.nextLine(); // Consume newline character

    // Convert the long ID to an int
    int opportunityIdInt = Math.toIntExact(opportunityId);

    // Use the OpportunityDAO to retrieve the opportunity by its ID
    Opportunity opportunity = opportunityDAO.getById(opportunityIdInt);

    if (opportunity != null) {
        // Display the current details of the opportunity
        System.out.println("Current opportunity details:");
        System.out.println("Name: " + opportunity.getName());
        // Display additional details such as customer if needed

        // Prompt the user to enter the updated opportunity details
        System.out.println("Enter the new name for the opportunity:");
        String newName = scanner.nextLine();
        opportunity.setName(newName);

        // Prompt the user to update the associated customer if needed
        System.out.println("Do you want to update the associated customer? (Y/N)");
        String updateCustomerChoice = scanner.nextLine();
        if (updateCustomerChoice.equalsIgnoreCase("Y")) {
            // Prompt the user to enter the ID of the new customer
            System.out.println("Enter the ID of the new customer:");
            long customerId = scanner.nextLong();
            scanner.nextLine(); // Consume newline character

            // Use the CustomerDAO to retrieve the customer by its ID
            Customer customer = customerDAO.getById(customerId);
            if (customer != null) {
                // Update the opportunity's associated customer
                opportunity.setCustomer(customer);
            } else {
                System.out.println("Customer not found. Opportunity's customer remains unchanged.");
            }
        }

        // Update the opportunity using the OpportunityDAO
        opportunityDAO.update(opportunity);
        System.out.println("Opportunity updated successfully.");
    } else {
        System.out.println("Opportunity not found.");
     }
    }

    private static void updateTask(TaskDAO taskDAO, CustomerDAO customerDAO, OpportunityDAO opportunityDAO) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the ID of the task to update
        System.out.println("Enter the ID of the task to update:");
        long taskId = scanner.nextLong();
        scanner.nextLine(); // Consume newline character
    
        // Convert the long ID to an int
        int taskIdInt = Math.toIntExact(taskId);
    
        // Use the TaskDAO to retrieve the task by its ID
        Task task = taskDAO.getById(taskIdInt);
    
        if (task != null) {
            // Display the current details of the task
            System.out.println("Current task details:");
            System.out.println("Name: " + task.getName());
            // Display additional details such as customer and opportunity if needed
    
            // Prompt the user to enter the updated task details
            System.out.println("Enter the new name for the task:");
            String newName = scanner.nextLine();
            task.setName(newName);
    
            // Update the task using the TaskDAO
            taskDAO.update(task);
            System.out.println("Task updated successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void deleteCustomer(CustomerDAO customerDAO) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the ID of the customer to delete
        System.out.println("Enter the ID of the customer to delete:");
        long customerId = scanner.nextLong();
        scanner.nextLine(); // Consume newline character
    
        // Retrieve the customer object from the database
        Customer customer = customerDAO.getById(customerId);
    
        if (customer != null) {
            // Delete the customer object
            customerDAO.delete(customer);
            System.out.println("Customer deleted successfully.");
        } else {
            System.out.println("Customer not found.");
        }
    }

    private static void deleteOpportunity(OpportunityDAO opportunityDAO) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the ID of the opportunity to delete
        System.out.println("Enter the ID of the opportunity to delete:");
        long opportunityId = scanner.nextLong();
        scanner.nextLine(); // Consume newline character
    
        // Convert the long ID to an int
        int opportunityIdInt = Math.toIntExact(opportunityId);
    
        // Use the OpportunityDAO to retrieve the opportunity by its ID
        Opportunity opportunity = opportunityDAO.getById(opportunityIdInt);
    
        if (opportunity != null) {
            // If the opportunity exists, delete it using the OpportunityDAO
            opportunityDAO.delete(opportunity);
            System.out.println("Opportunity deleted successfully.");
        } else {
            System.out.println("Opportunity not found.");
        }
    }

    private static void deleteTask(TaskDAO taskDAO) {
        Scanner scanner = new Scanner(System.in);

        // Prompt the user to enter the ID of the task to delete
        System.out.println("Enter the ID of the task to delete:");
        long taskId = scanner.nextLong();
        scanner.nextLine(); // Consume newline character
    
        // Convert the long ID to an int
        int taskIdInt = Math.toIntExact(taskId);
    
        // Use the TaskDAO to retrieve the task by its ID
        Task task = taskDAO.getById(taskIdInt);
    
        if (task != null) {
            // If the task exists, delete it using the TaskDAO
            taskDAO.delete(task);
            System.out.println("Task deleted successfully.");
        } else {
            System.out.println("Task not found.");
        }
    }

    private static void viewCustomersOpportunities(CustomerDAO customerDAO, OpportunityDAO opportunityDAO) {
        List<Customer> customers = customerDAO.getAll();
        
        if (customers != null && !customers.isEmpty()) {
            System.out.println("=== Customers and their Opportunities ===");
            for (Customer customer : customers) {
                System.out.println("Customer: " + customer.getName());
                List<Opportunity> opportunities = opportunityDAO.getByCustomer(customer);;
                if (opportunities != null && !opportunities.isEmpty()) {
                    for (Opportunity opportunity : opportunities) {
                        System.out.println("- Opportunity: " + opportunity.getName());
                    }
                } else {
                    System.out.println("- No opportunities found for this customer.");
                }
            }
        } else {
            System.out.println("No customers found.");
        }
    }

    private static void viewOpportunityTasks(OpportunityDAO opportunityDAO) {
        Scanner scanner = new Scanner(System.in);
    
        // Get the opportunity ID from the user
        System.out.println("Enter opportunity ID:");
        int opportunityId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
    
        // Retrieve the opportunity object by its ID
        Opportunity opportunity = opportunityDAO.getById(opportunityId);
    
        if (opportunity != null) {
            List<Task> tasks = opportunity.getTasks();
    
            if (tasks != null && !tasks.isEmpty()) {
                System.out.println("=== Tasks for Opportunity " + opportunity.getName() + " ===");
                for (Task task : tasks) {
                    System.out.println("ID: " + task.getId());
                    System.out.println("Name: " + task.getName());
                    System.out.println("Customer: " + task.getCustomer().getName());
                    // Display other relevant information about the task
                    System.out.println();
                }
            } else {
                System.out.println("No tasks found for this opportunity.");
            }
        } else {
            System.out.println("Opportunity not found.");
        }
    }
    

    private static void addProductToOpportunity(OpportunityDAO opportunityDAO, ProductDAO productDAO) {
        Scanner scanner = new Scanner(System.in);
    
        // Get the opportunity ID
        System.out.println("Enter opportunity ID:");
        int opportunityId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
    
        // Get the product ID
        System.out.println("Enter product ID:");
        int productId = scanner.nextInt();
        scanner.nextLine(); // Consume newline character
    
        // Retrieve the opportunity and product objects
        Opportunity opportunity = opportunityDAO.getById(opportunityId);
        Product product = productDAO.getById(productId);
    
        if (opportunity == null || product == null) {
            System.out.println("Opportunity or product not found.");
            return;
        }
    
        // Add the product to the opportunity
        opportunityDAO.addOpportunityForProduct(opportunity, product);
        
        System.out.println("Product added to the opportunity successfully.");
    }
    
}
