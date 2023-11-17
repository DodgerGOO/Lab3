import java.util.ArrayList;
import java.util.List;

public class User {
    private String name;
    private List<Product> purchases;

    public User(String name) {
        this.name = name;
        this.purchases = new ArrayList<>();
    }

    public String getName() {
        return name;
    }

    public List<Product> getPurchases() {
        return purchases;
    }

    public void setPurchaseQuantity(Product product, int quantity) {
        for (int i = 0; i < quantity; i++) {
            purchases.add(product);
        }
    }


    public void addPurchase(Product product) {
        purchases.add(product);
    }
}
