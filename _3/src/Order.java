import java.io.FileWriter;
import java.io.IOException;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Order {
    private List<Product> items;

    public Order(List<Product> items) {
        this.items = items;
    }

    public void generateReceipt(String fileName) {
        sortPrices();

        try (FileWriter writer = new FileWriter(fileName)) {
            writer.write("Order Receipt:\n");
            writer.write("----------------------------\n");
            double totalAmount = 0;
            Map<Product, Integer> productQuantities = new HashMap<>();

            for (Product item : items) {
                writer.write(item.getName() + ": $" + item.getPrice() + "\n");
                totalAmount += item.getPrice();
                productQuantities.put(item, productQuantities.getOrDefault(item, 0) + 1);
            }

            writer.write("----------------------------\n");
            writer.write("Total: $" + totalAmount + "\n");

            double averagePrice = calculateAveragePrice();
            writer.write("Average Price: $" + averagePrice + "\n");

            printTotalQuantities(productQuantities, writer);


            Product mostPopularProduct = findMostPopularProduct();
            saveMostPopularProductToFile("accounting.txt", mostPopularProduct);

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sortPrices() {
        Collections.sort(items, Comparator.comparingDouble(Product::getPrice));
    }

    private double calculateAveragePrice() {
        double total = 0;
        for (Product item : items) {
            total += item.getPrice();
        }
        return total / items.size();
    }

    private void printTotalQuantities(Map<Product, Integer> productQuantities, FileWriter writer) throws IOException {
        writer.write("Total Quantities:\n");
        for (Map.Entry<Product, Integer> entry : productQuantities.entrySet()) {
            writer.write(entry.getKey().getName() + ": " + entry.getValue() + "\n");
        }
    }

    public Product findMostPopularProduct() {
        Map<Product, Integer> productOccurrences = new HashMap<>();

        for (Product item : items) {
            productOccurrences.put(item, productOccurrences.getOrDefault(item, 0) + 1);
        }

        return Collections.max(productOccurrences.entrySet(), Comparator.comparingInt(Map.Entry::getValue)).getKey();
    }

    public void saveMostPopularProductToFile(String fileName, Product mostPopularProduct) {
        try (FileWriter writer = new FileWriter(fileName, true)) {
            writer.write("Most Popular Product: " + mostPopularProduct.getName() + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public List<Product> getItems() {
        return items;
    }
}
