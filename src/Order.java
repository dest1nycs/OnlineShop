import java.util.ArrayList;
import java.util.List;

public class Order {
    private static int counter = 0;

    private final int id;
    private final List<Product> products;
    private final double totalPrice;
    private String status;

    public Order(Cart cart) {
        this.id = ++counter;
        this.products = new ArrayList<>(cart.getProducts());
        this.totalPrice = cart.getTotalPrice();
        this.status = "Нове";
    }

    public int getId() { return id; }
    public List<Product> getProducts() { return new ArrayList<>(products); }
    public double getTotalPrice() { return totalPrice; }
    public String getStatus() { return status; }
    public void setStatus(String status) { this.status = status; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("Замовлення №" + id + ":\n");
        for (Product product : products) {
            sb.append(product).append("\n");
        }
        sb.append(String.format("Загальна вартість: %.2f грн%n", totalPrice));
        sb.append("Статус: ").append(status);
        return sb.toString();
    }
}
