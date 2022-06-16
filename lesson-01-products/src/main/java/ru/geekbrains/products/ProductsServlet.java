package ru.geekbrains.products;

import ru.geekbrains.products.persist.Product;
import ru.geekbrains.products.persist.ProductsRepository;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.NoSuchElementException;

@WebServlet(urlPatterns = "/products/*")
public class ProductsServlet extends HttpServlet {
    private ProductsRepository productsRepository;

    @Override
    public void init() {
        this.productsRepository = new ProductsRepository();
        ProductsUtils.fillWithRandomProducts(this.productsRepository, 10);
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String pathInfo = req.getPathInfo();
        resp.getWriter().println("<!doctype html><html><body>");
        if (pathInfo == null || pathInfo.equals("/")) {
            String productsTable = ProductsUtils.getProductsTable(productsRepository);
            resp.getWriter().println(productsTable);
        } else {
            try {
                int productId = Integer.parseInt(pathInfo.substring(1));
                Product product = productsRepository.getProductById(productId);
                if (product == null) {
                    resp.getWriter().println("<p><b>Product with id " + productId + " was not found.</b></p>");
                    resp.setStatus(404);
                } else {
                    resp.getWriter().println(ProductsUtils.getProductTable(product));
                }
            } catch (NumberFormatException e) {
                e.printStackTrace();
                resp.getWriter().println("<p><b>Wrong id given: " + pathInfo.substring(1) + "</b></p>");
                resp.setStatus(400);
            }
        }
        resp.getWriter().println("</body></html>");
        resp.setContentType("text/html; charset=utf-8");
    }
}
