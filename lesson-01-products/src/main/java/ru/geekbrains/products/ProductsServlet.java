package ru.geekbrains.products;

import ru.geekbrains.products.persist.Product;
import ru.geekbrains.products.persist.ProductsRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.NoSuchElementException;
import java.util.regex.Pattern;
import java.util.regex.Matcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet(urlPatterns = "/products/*")
public class ProductsServlet extends HttpServlet {
    private static final Pattern PRODUCT_ID_PATTERN = Pattern.compile("\\/(\\d+)");
    private ProductsRepository productsRepository;

    @Override
    public void init() {
        this.productsRepository = new ProductsRepository();
        ProductsUtils.fillWithRandomProducts(this.productsRepository, 10);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int status = 200;
        PrintWriter writer = resp.getWriter();
        writer.println("<!doctype html><html><body>");
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            String productsTable = ProductsUtils.getProductsTable(productsRepository,
                    req.getContextPath() + req.getServletPath());
            writer.println(productsTable);
        } else {
            status = printSingleProductInfo(writer, pathInfo);
        }
        writer.println("</body></html>");
        resp.setStatus(status);
    }

    private int printSingleProductInfo(PrintWriter writer, String pathInfo) {
        int status = 200;
        Matcher matcher = PRODUCT_ID_PATTERN.matcher(pathInfo);
        if (matcher.matches()) {
            long productId = Long.parseLong(matcher.group(1));
            Product product = productsRepository.getProductById(productId);
            if (product == null) {
                writer.println("<p><b>Product with id " + productId +
                        " was not found.</b></p>");
                status = 404;
            } else {
                writer.println(ProductsUtils.getProductTable(product));
            }
        } else {
            writer.println("<p><b>Bad id given: " + pathInfo.substring(1) + "</b></p>");
            status = 400;
        }
        return status;
    }
}
