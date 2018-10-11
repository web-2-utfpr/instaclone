package servlet;

import exception.*;
import model.repository.UserRepository;
import util.Context;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ResourceBundle;

@WebServlet(
        name = "Registration",
        urlPatterns = {"/register"}
)
public class RegisterServlet extends HttpServlet {

    Context context;

    private static UserRepository userRepository;

    static {
        userRepository = new UserRepository();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        req.getRequestDispatcher("/register.jsp").forward(req, resp);

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {

        context = new Context(req, resp);

        ResourceBundle messages = ResourceBundle.getBundle("Messages");

        String username = req.getParameter("username");
        String email = req.getParameter("email");
        String password = req.getParameter("password");
        String confirm_password = req.getParameter("confirm_password");

        try {
            if (!password.equals(confirm_password)) throw new PasswordsNotMatchException();
            userRepository.registrar(username, email, password);
            req.setAttribute("msg", messages.getString("registerSuccess"));
            context.Dispatch("/login.jsp");
        } catch (UserAlreadyExistsException ex) {
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.setAttribute("error", ex.getMessage());
            context.Dispatch("/register.jsp");
        } catch (EmailAlreadyRegisteredException ex) {
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.setAttribute("error", ex.getMessage());
            context.Dispatch("/register.jsp");
        } catch (InvalidUsernameException ex) {
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.setAttribute("error", ex.getMessage());
            context.Dispatch("/register.jsp");
        } catch (InvalidEmailException ex) {
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.setAttribute("error", ex.getMessage());
            context.Dispatch("/register.jsp");
        } catch (InvalidPasswordException ex) {
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.setAttribute("error", ex.getMessage());
            context.Dispatch("/register.jsp");
        } catch (PasswordsNotMatchException ex) {
            req.setAttribute("username", username);
            req.setAttribute("email", email);
            req.setAttribute("error", ex.getMessage());
            context.Dispatch("/register.jsp");
        }

    }

}
