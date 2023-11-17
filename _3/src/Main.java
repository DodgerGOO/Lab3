import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        List<Product> availableProducts = FileService.loadProductsFromFile("products.dat");

        if (availableProducts == null) {
            availableProducts = new ArrayList<>();
        }

        final Inventory inventory = new Inventory();
        final User user = new User("Bolodya");

        final Scanner scanner = new Scanner(System.in);

        int choice;
        do {
            System.out.println("=== Menu ===");
            System.out.println("1. Add product to inventory");
            System.out.println("2. Buy product");
            System.out.println("3. Create order and generate receipt");
            System.out.println("4. Find most popular product");
            System.out.println("5. Save information about the most popular product");
            System.out.println("6. Exit");
            System.out.print("Choose an option (1-6): ");

            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    System.out.print("Enter the product name: ");
                    final String productName = scanner.next();
                    System.out.print("Enter the product price (in $): ");
                    final double productPrice = scanner.nextDouble();
                    final Product newProduct = new Product(productName, productPrice);
                    inventory.addProduct(newProduct);
                    availableProducts.add(newProduct);
                    break;
                case 2:
                    System.out.print("Enter the product name to purchase: ");
                    final String purchasedProductName = scanner.next();
                    final Product purchasedProduct = availableProducts.stream()
                            .filter(p -> p.getName().equalsIgnoreCase(purchasedProductName))
                            .findFirst()
                            .orElse(null);
                    if (purchasedProduct != null) {
                        final int quantity = getUserInputForQuantity(scanner);
                        user.setPurchaseQuantity(purchasedProduct, quantity);
                        inventory.sellProduct(purchasedProduct, quantity);
                    } else {
                        System.out.println("This product is not available in the inventory.");
                    }
                    break;
                case 3:
                    final Order order = new Order(user.getPurchases());
                    order.generateReceipt("order_receipt.txt");
                    System.out.println("Receipt generated and saved in 'order_receipt.txt'");
                    break;
                case 4:
                    final Order orderForAnalysis = new Order(user.getPurchases());
                    final Product mostPopularProduct = orderForAnalysis.findMostPopularProduct();
                    System.out.println("Most popular product: " + mostPopularProduct.getName());
                    break;
                case 5:
                    final Order orderForAnalysisSave = new Order(user.getPurchases());
                    final Product mostPopularProductSave = orderForAnalysisSave.findMostPopularProduct();
                    orderForAnalysisSave.saveMostPopularProductToFile("accounting.txt", mostPopularProductSave);
                    System.out.println("Information about the most popular product saved in 'accounting.txt'");
                    break;
                case 6:
                    System.out.println("Goodbye!");
                    break;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        } while (choice != 6);

        FileService.saveProductsToFile(availableProducts, "products.dat");

        scanner.close();
    }

    private static int getUserInputForQuantity(final Scanner scanner) {
        int quantity;
        do {
            System.out.print("Enter the quantity: ");
            while (!scanner.hasNextInt()) {
                System.out.println("Please enter a number.");
                scanner.next();
            }
            quantity = scanner.nextInt();
            if (quantity <= 0) {
                System.out.println("Quantity must be greater than 0.");
            }
        } while (quantity <= 0);
        return quantity;
    }
}
