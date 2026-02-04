package auca.ac.rw;

import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String username = sanitize(request.getParameter("username"));
        String password = request.getParameter("password");
        if (password == null) {
            password = "";
        }

        response.setContentType("text/html;charset=UTF-8");

        PrintWriter out = response.getWriter();
        out.println("<!DOCTYPE html>");
        out.println("<html>");
        out.println("<head>");
        out.println("<title>Login Result</title>");
        out.println("<style>");
        out.println("body{font-family:Arial,sans-serif;background:#f4f6fb;color:#1f2430;margin:0;padding:0;}");
        out.println(".wrapper{min-height:100vh;display:flex;align-items:center;justify-content:center;}");
        out.println(".card{background:#fff;border-radius:12px;box-shadow:0 12px 30px rgba(0,0,0,0.12);padding:32px;max-width:420px;width:90%;text-align:center;}");
        out.println(".card h2{margin-top:0;font-size:24px;}");
        out.println(".msg{font-size:18px;margin:16px 0;}");
        out.println(".back{display:inline-block;margin-top:12px;color:#1f6feb;text-decoration:none;font-weight:bold;}");
        out.println(".back:hover{text-decoration:underline;}");
        out.println("</style>");
        out.println("</head>");
        out.println("<body>");
        out.println("<div class=\"wrapper\"><div class=\"card\">");
        out.println("<h2>Login Response</h2>");

        if (password.length() < 8) {
            out.println("<div class=\"msg\">Hello " + username + ", your password is weak. Try a strong one.</div>");
        } else {
            out.println("<div class=\"msg\">Welcome " + username + "</div>");
        }

        out.println("<a class=\"back\" href=\"index.html\">&larr; Back to Login</a>");
        out.println("</div></div>");
        out.println("</body>");
        out.println("</html>");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.sendRedirect(request.getContextPath() + "/index.html");
    }

    private String sanitize(String input) {
        if (input == null) {
            return "";
        }
        return input
                .replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;")
                .replace("'", "&#39;");
    }
}
