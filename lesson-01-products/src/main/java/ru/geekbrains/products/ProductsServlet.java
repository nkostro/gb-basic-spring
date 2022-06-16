package ru.geekbrains.products;

import ru.geekbrains.products.persist.Product;
import ru.geekbrains.products.persist.ProductsRepository;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
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
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException, ServletException {
        int status = 200;
        String pathInfo = req.getPathInfo();
        if (pathInfo == null || pathInfo.equals("/")) {
            req.setAttribute("products", productsRepository.getAllProducts());
            getServletContext().getRequestDispatcher("/products.jsp").forward(req, resp);
        } else {
            PrintWriter writer = resp.getWriter();
            Matcher matcher = PRODUCT_ID_PATTERN.matcher(pathInfo);
            if (matcher.matches()) {
                long productId = Long.parseLong(matcher.group(1));
                Product product = productsRepository.getProductById(productId);
                if (product == null) {
                    status = 404;
                    writer.println("Product with id " + productId + " was now found");
                } else {
                    req.setAttribute("product", product);
                    getServletContext().getRequestDispatcher("/product_form.jsp").forward(req, resp);
                }
            } else {
                status = 400;
                writer.println("Bad id given: " + pathInfo.substring(1));
            }
        }
        resp.setStatus(status);
    }

}
