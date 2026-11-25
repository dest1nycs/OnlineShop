import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Main {

    private static final List<Product> catalog = new ArrayList<>();
    private static final List<Order> orderHistory = new ArrayList<>();

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        Category electronics = new Category(1, "Електроніка");
        Category smartphones = new Category(2, "Смартфони");
        Category accessories = new Category(3, "Аксесуари");
        Category clothing = new Category(4, "Одяг");

        catalog.add(new Product(1, "Ноутбук", 19999.99, "Високопродуктивний ноутбук для роботи та ігор", electronics));
        catalog.add(new Product(2, "Смартфон", 12999.50, "Смартфон з великим екраном та високою автономністю", smartphones));
        catalog.add(new Product(3, "Навушники", 2499.00, "Бездротові навушники з шумозаглушенням", accessories));
        catalog.add(new Product(4, "Куртка", 3200.00, "Тепла зимова куртка", clothing));
        catalog.add(new Product(5, "Чохол", 399.00, "Захисний чохол для смартфона", accessories));

        Cart cart = new Cart();

        while (true) {
            printMenu();
            int choice = readInt(scanner, "Ваш вибір: ");

            switch (choice) {
                case 1:
                    printProducts(catalog);
                    break;
                case 2:
                    addToCart(scanner, cart);
                    break;
                case 3:
                    System.out.println(cart);
                    break;
                case 4:
                    removeFromCart(scanner, cart);
                    break;
                case 5:
                    makeOrder(cart);
                    break;
                case 6:
                    showHistory();
                    break;
                case 7:
                    searchProducts(scanner);
                    break;
                case 0:
                    System.out.println("Дякуємо, що використовували наш магазин!");
                    return;
                default:
                    System.out.println("Невідома опція. Спробуйте ще раз.");
            }
        }
    }

    private static void printMenu() {
        System.out.println("\n===== ІНТЕРНЕТ-МАГАЗИН =====");
        System.out.println("1 - Переглянути список товарів");
        System.out.println("2 - Додати товар до кошика");
        System.out.println("3 - Переглянути кошик");
        System.out.println("4 - Видалити товар з кошика");
        System.out.println("5 - Зробити замовлення");
        System.out.println("6 - Історія замовлень");
        System.out.println("7 - Пошук товарів");
        System.out.println("0 - Вийти");
    }

    // Безпечне читання числа: не падає, якщо введено текст
    private static int readInt(Scanner scanner, String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("Будь ласка, введіть ціле число.");
            }
        }
    }

    private static void printProducts(List<Product> products) {
        if (products.isEmpty()) {
            System.out.println("Товарів не знайдено.");
            return;
        }
        for (Product p : products) {
            System.out.println(p);
        }
    }

    private static Product findById(int id) {
        for (Product p : catalog) {
            if (p.getId() == id) {
                return p;
            }
        }
        return null;
    }

    private static void addToCart(Scanner scanner, Cart cart) {
        int id = readInt(scanner, "Введіть ID товару для додавання до кошика: ");
        Product product = findById(id);
        if (product == null) {
            System.out.println("Товар з таким ID не знайдено.");
        } else {
            cart.addProduct(product);
            System.out.println("Додано до кошика: " + product.getName());
        }
    }

    private static void removeFromCart(Scanner scanner, Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Кошик порожній.");
            return;
        }
        System.out.println(cart);
        int id = readInt(scanner, "Введіть ID товару для видалення: ");
        if (cart.removeProductById(id)) {
            System.out.println("Товар видалено з кошика.");
        } else {
            System.out.println("Такого товару немає в кошику.");
        }
    }

    private static void makeOrder(Cart cart) {
        if (cart.isEmpty()) {
            System.out.println("Кошик порожній. Додайте товари перед оформленням замовлення.");
            return;
        }
        Order order = new Order(cart);
        orderHistory.add(order);
        System.out.println("Замовлення оформлено:");
        System.out.println(order);
        cart.clear();
    }

    private static void showHistory() {
        if (orderHistory.isEmpty()) {
            System.out.println("Історія замовлень порожня.");
            return;
        }
        for (Order order : orderHistory) {
            System.out.println(order);
            System.out.println("-----------------------------");
        }
    }

    // Пошук за назвою товару або назвою категорії (без урахування регістру)
    private static void searchProducts(Scanner scanner) {
        System.out.print("Введіть назву товару або категорії: ");
        String query = scanner.nextLine().trim().toLowerCase();
        List<Product> found = new ArrayList<>();
        for (Product p : catalog) {
            if (p.getName().toLowerCase().contains(query)
                    || p.getCategory().getName().toLowerCase().contains(query)) {
                found.add(p);
            }
        }
        printProducts(found);
    }
}
