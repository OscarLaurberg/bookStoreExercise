/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package presentation;

import businessLogic.Book;
import businessLogic.CollectionToHtml;
import businessLogic.ShoppingCart;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import persistence.BookMapper;
import persistence.BookMapperInterface;

/**
 *
 * @author oscar
 */
@WebServlet(name = "ShowBooksByFullTitle", urlPatterns = {"/ShowBooksByFullTitle"})
public class ShowBooksByFullTitle extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code> methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        HttpSession session = request.getSession(true);
        ShoppingCart cart;
        synchronized (session) {
            cart = (ShoppingCart) session.getAttribute("shoppingCart");
            if (cart == null) {
                cart = new ShoppingCart();
                session.setAttribute("shoppingCart", cart);
            }

        }
        BookMapperInterface mapper = new BookMapper();
        List<Book> books = mapper.getAllBooks();
        String title = request.getParameter("bookTitle");
        Book book = mapper.getBookByFullTitle(title);
        request.setAttribute("books", books);
        String bookHtmlForm ="";
        if (book != null){
            bookHtmlForm = CollectionToHtml.bookToHtmlForm(book);
        }else {
            bookHtmlForm = "Your search didn't match any books, please try again.";
            
        } 
        try ( PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet GetBookByTitleServlet</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet GetBookByTitleServlet at " + request.getContextPath() + "</h1>");
            out.println(bookHtmlForm);
            out.println("</body>");
            out.println("</html>");
        }
    }
    


    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
