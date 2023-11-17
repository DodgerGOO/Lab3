import java.util.ArrayList;
import java.util.List;

public class Inventory {
    private List<Product> products;

    public Inventory() {
        this.products = new ArrayList<>();
    }

    public void addProduct(Product product) {
        products.add(product);
    }

    public void sellProduct(Product product, int quantity) {
        if (products.contains(product)) {
            products.remove(product);

        }
    }


    public void editProduct(Product oldProduct, Product newProduct) {
        if (products.contains(oldProduct)) {
            int index = products.indexOf(oldProduct);
            products.set(index, newProduct);

        }
    }


    public List<Product> getProducts() {
        return products;
    }
}
