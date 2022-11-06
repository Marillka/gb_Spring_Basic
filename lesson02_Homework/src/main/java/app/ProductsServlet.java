package app;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "ProductsServlet", urlPatterns = "/products")
public class ProductsServlet extends HttpServlet {

    private static Logger logger = LoggerFactory.getLogger(ProductsServlet.class);

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        logger.info("ProductsServlet started");
        ArrayList<Product> products = initProductsList();

        resp.setContentType("text/html;charset=UTF-8");
        resp.getWriter().printf("<html><body>");
        for (int i = 0; i < products.size(); i++) {
            resp.getWriter().printf("<h3>" + products.get(i).getId() + " " + products.get(i).getTitle() + " " + products.get(i).getCost());
        }
        resp.getWriter().printf("</body></html>");
        resp.getWriter().close();
        logger.info("ProductsServlet finished");

    }

    public ArrayList<Product> initProductsList() {
        ArrayList<Product> products = new ArrayList<>();
        products.add(new Product(1, "orange", 500));
        products.add(new Product(2, "apple", 200));
        products.add(new Product(3, "banana", 769));
        products.add(new Product(4, "apricot ", 71221));
        products.add(new Product(5, "apricot ", 424));
        products.add(new Product(6, "pineapple ", 7712));
        products.add(new Product(7, "bergamot ", 7354352));
        products.add(new Product(8, "durian ", 7329));
        products.add(new Product(9, "grapefruit ", 169));
        products.add(new Product(10, "kiwi ", 969));
        return products;
    }
}
