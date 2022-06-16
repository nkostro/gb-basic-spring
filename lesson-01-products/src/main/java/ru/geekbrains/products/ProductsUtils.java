package ru.geekbrains.products;

import ru.geekbrains.products.persist.Product;
import ru.geekbrains.products.persist.ProductsRepository;

import java.math.BigDecimal;
import java.util.Random;

public class ProductsUtils {
    private static final String[] titles = {
            "Apples", "Meat", "Milk", "Cabbage", "Water", "Eggs", "Cereal", "Cheese", "Butter", "Gum",
            "Candy", "Cola", "Pepsi", "Ice-cream", "Bread", "Grapes", "Pears", "Crisps", "Corn", "Peaches"
    };

    public static void fillWithRandomProducts(ProductsRepository repository, int numOfRandomProducts) {
        Random rand = new Random();
        for (int i = 0; i < numOfRandomProducts; i++) {
            String title = titles[rand.nextInt(titles.length)];
            BigDecimal cost = BigDecimal.valueOf(rand.nextInt(10000) / 100.0);
            repository.addProduct(title, cost);
        }
    }

    public static String getProductsTable(ProductsRepository repository) {
        StringBuilder sb = new StringBuilder("<table><tr><th>Id</th><th>Title</th><th>Cost</th></tr>");
        for (Product product : repository.getAllProducts()) {
            sb.append(
                    String.format("<tr><td>%d</td><td>%s</td><td>%s</td></tr>",
                    product.getId(), product.getTitle(), product.getCost()));
        }
        sb.append("</table>");
        return sb.toString();
    }

    public static String getProductTable(Product product) {
        return "<table><tr><th>Id</th><th>Title</th><th>Cost</th></tr>" +
                String.format("<tr><td>%d</td><td>%s</td><td>%s</td></tr></table>",
                        product.getId(), product.getTitle(), product.getCost());
    }
}
