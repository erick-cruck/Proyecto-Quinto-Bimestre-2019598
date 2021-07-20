package controller;
import java.io.IOException;
import java.io.PrintWriter;
import static java.lang.System.out;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.Persona;
import model.Usuario;
import modelDAO.PersonaDAO;
public class Controlador extends HttpServlet {
    //Variables de clase
    int codigoPersona;
    String js = "confirm('Estas seguro de eliminarlo')";
    String home = "index.html";
    String listar = "views/listarView.jsp";
    String listarUser = "views/listarUserView.jsp";
    String add = "views/addView.jsp";
    String edit = "views/editView.jsp";
    Persona nuevaPersona = new Persona();
    Usuario nuevoUsuario = new Usuario();
    PersonaDAO nuevaPersonaDao = new PersonaDAO();
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet Controlador</title>");            
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet Controlador at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
    }
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
      
      String acceso = "";
      String action = request.getParameter("accion");
      if(action.equalsIgnoreCase("home")){
          acceso = home;
      }
      if(action.equalsIgnoreCase("add")){
          acceso = add;
      }else if(action.equalsIgnoreCase("listar")){
          acceso = listar;
      }
      else if(action.equalsIgnoreCase("listarUser")){
          acceso = listarUser;
      }
      if(action.equalsIgnoreCase("agregar")){
          String DPI = request.getParameter("txtDPI");
          String NombreP = request.getParameter("txtNombres");
          Persona NuevaPersona = new Persona();
          NuevaPersona.setDPI(DPI);
          NuevaPersona.setNombrePersona(NombreP);
          PersonaDAO nuevaPersonaDAO = new PersonaDAO();
          nuevaPersonaDAO.add(NuevaPersona);
          try {
              Thread.sleep(2000);
              acceso = listar;
          } catch (InterruptedException ex) {
              Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
          }
      }else if(action.equalsIgnoreCase("editar")){
          request.setAttribute("codPer",request.getParameter("codigoPersona"));
          acceso = edit;
      }else if(action.equalsIgnoreCase("actualizar")){
          codigoPersona = Integer.parseInt(request.getParameter("txtCodigoPersona"));
          String DPI = request.getParameter("txtDPI");
          String Nombre = request.getParameter("txtNombres");
          nuevaPersona.setCodigoPersona(codigoPersona);
          nuevaPersona.setDPI(DPI);
          nuevaPersona.setNombrePersona(Nombre);
          nuevaPersonaDao.edit(nuevaPersona);
          try {
              Thread.sleep(2000);
              acceso=listar;
          } catch (InterruptedException ex) {
              Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
          }
      }else if(action.equalsIgnoreCase("eliminar")){
          
          codigoPersona = Integer.parseInt(request.getParameter("codigoPersona"));
          nuevaPersona.setCodigoPersona(codigoPersona);
          nuevaPersonaDao.eliminar(codigoPersona);
          try {
              Thread.sleep(2000);
              acceso=listar;
          } catch (InterruptedException ex) {
              Logger.getLogger(Controlador.class.getName()).log(Level.SEVERE, null, ex);
          }
      }else if(action.equalsIgnoreCase("login")){
          String user = request.getParameter("txtUsuario");
          String pass = request.getParameter("pswContrasena");
          String rol = request.getParameter("rol");
          nuevoUsuario.setUsuario(user);
          nuevoUsuario.setPassword(pass);
          nuevoUsuario.setRol(rol);
          PersonaDAO personaDao = new PersonaDAO();
          boolean validacion = personaDao.login(nuevoUsuario);
          if(validacion  == true ){
              if(rol.equals("Administrador")){
                  acceso = listar;
              }else{
                  acceso = listarUser;
              }
          }else{
              acceso = home;
          }
      }
      
      RequestDispatcher vista = request.getRequestDispatcher(acceso);
      vista.forward(request, response);
      
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
    }
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
