/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import model.domain.Product;
import model.domain.ProductCategory;

@WebServlet(name = "productMenuServlet", urlPatterns = {"/productMenuServlet"})
public class productMenuServlet extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {

            response.setContentType("text/html;charset=UTF-8");
            RequestDispatcher requestDispatcher;
            HttpSession session = request.getSession();

            String productCategory = "";
            String searchValue = "";

            // Controller
            ProductController prodController = new ProductController();
            ProductCategoryController prodCategoryController = new ProductCategoryController();

            // Retreive all Product and Product Category from database
            ArrayList<Product> productList = prodController.getAllProduct();
            ArrayList<ProductCategory> productCategoryList = prodCategoryController.getAllProductCategory();

            // Set session Attribute
            session.setAttribute("productList", productList);
            session.setAttribute("productCategoryList", productCategoryList);

            // If Parameter "searchProductByCategory" exists 
            if (request.getParameter("searchProductByCategory") != null) {

                productCategory = request.getParameter("searchProductByCategory");
                ArrayList<Product> newProductList = new ArrayList<>();

                // Loop Every Product
                for (Product product : productList) {

                    // Compare Category Name in the product object with the Parameter pass in from Menu.jsp (Product Category)
                    if (product.getProductCategory().getCategoryName().equalsIgnoreCase(productCategory)) {

                        newProductList.add(product);
                    }

                }

                session.setAttribute("productList", newProductList);
            }

            // If Parameter "search-value" exists 
            if (request.getParameter("search-value") != null) {

                searchValue = request.getParameter("search-value");
                ArrayList<Product> newProductList = new ArrayList<>();

                // Loop Every Product
                for (Product product : productList) {

                    // Compare Category Name in the product object with the Parameter pass in from Menu.jsp (Product Category)
                    if (product.getProductName().equalsIgnoreCase(searchValue)) {

                        newProductList.add(product);

                    } else if (product.getProductID().equalsIgnoreCase(searchValue)) {

                        newProductList.add(product);

                    }

                }

                session.setAttribute("productList", newProductList);
            }

            request.getRequestDispatcher("Menu.jsp").forward(request, response);

//            response.sendRedirect("Menu.jsp");
        } catch (SQLException ex) {
            Logger.getLogger(productMenuServlet.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

}
