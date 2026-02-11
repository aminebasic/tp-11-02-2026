package ma.tp.web;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import ma.tp.dao.ContactDAO;
import ma.tp.model.Contact;

@WebServlet("/contacts")
public class ContactController extends HttpServlet {
    private ContactDAO contactDAO;
    
    @Override
    public void init() {
        contactDAO = new ContactDAO();
    }
    
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            
            if (action == null) action = "list";
            
            switch (action) {
                case "list" :
		 listContacts(request, response);
			break;
                case "new" :
		 showNewForm(request, response);
			break; 
                case "edit" :
		 showEditForm(request, response);
			break;
                case "delete" :
		 deleteContact(request, response);
			break;
                case "search" :
		 searchContacts(request, response);
			break;
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        try {
            String action = request.getParameter("action");
            
            if ("save".equals(action)) {
                saveContact(request, response);
            } else if ("update".equals(action)) {
                updateContact(request, response);
            }
        } catch (SQLException ex) {
            throw new ServletException(ex);
        }
    }
    
    private void listContacts(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        request.setAttribute("contacts", contactDAO.findAll());
        request.getRequestDispatcher("/WEB-INF/views/liste.jsp").forward(request, response);
    }
    
    private void searchContacts(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        request.setAttribute("contacts", contactDAO.searchByName(request.getParameter("q")));
        request.setAttribute("searchTerm", request.getParameter("q"));
        request.getRequestDispatcher("/WEB-INF/views/liste.jsp").forward(request, response);
    }
    
    private void showNewForm(HttpServletRequest request, HttpServletResponse response) 
            throws ServletException, IOException {
        request.getRequestDispatcher("/WEB-INF/views/form.jsp").forward(request, response);
    }
    
    private void showEditForm(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, ServletException, IOException {
        int id = Integer.parseInt(request.getParameter("id"));
        request.setAttribute("contact", contactDAO.findById(id));
        request.setAttribute("editMode", true);
        request.getRequestDispatcher("/WEB-INF/views/form.jsp").forward(request, response);
    }
    
    private void saveContact(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        contactDAO.save(new Contact(
            request.getParameter("nom"),
            request.getParameter("prenom"),
            request.getParameter("tel"),
            request.getParameter("email")
        ));
        response.sendRedirect("contacts");
    }
    
    private void updateContact(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        contactDAO.update(new Contact(
            Integer.parseInt(request.getParameter("id")),
            request.getParameter("nom"),
            request.getParameter("prenom"),
            request.getParameter("tel"),
            request.getParameter("email")
        ));
        response.sendRedirect("contacts");
    }
    
    private void deleteContact(HttpServletRequest request, HttpServletResponse response) 
            throws SQLException, IOException {
        contactDAO.delete(Integer.parseInt(request.getParameter("id")));
        response.sendRedirect("contacts");
    }
}