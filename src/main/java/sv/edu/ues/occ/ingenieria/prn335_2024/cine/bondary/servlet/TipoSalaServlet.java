package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.servlet;

import jakarta.inject.Inject;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.control.TipoSalaBean;
import sv.edu.ues.occ.ingenieria.prn335_2024.cine.entity.TipoSala;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.List;

@WebServlet(name = "TipoSalaServlet", urlPatterns = "TipoSala")
public class TipoSalaServlet extends HttpServlet{

    @Inject
    TipoSalaBean tsBean;

 @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     resp.setContentType("text/html");
     PrintWriter out = resp.getWriter();
     if(req.getParameter("nombre") != null) {
         TipoSala nuevo = new TipoSala();
         nuevo.setNombre(req.getParameter("nombre"));
         nuevo.setActivo(Boolean.valueOf(req.getParameter("activo")));
         nuevo.setComentarios(req.getParameter("comentarios"));
         nuevo.setExpresionRegular(req.getParameter("expresionRegular"));
         try{
             tsBean.create(nuevo);
             out.println("<html>");
             out.println("<body>");
             out.println("<h1> Tipo de Sala </h1>");
             out.println("<hr>");
             out.println("Tipo Sala guardado exitosamente"+ nuevo.getIdTipoSala());
             out.println("</body>");
             out.println("</html>");
             return;
         }catch(Exception e){
           e.printStackTrace();
           resp.setStatus(500);
           return;
         }
     }
     resp.setStatus(HttpServletResponse.SC_PRECONDITION_FAILED);
 }
 @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     resp.setContentType("text/html");
     PrintWriter out = resp.getWriter();
     List<TipoSala> registros = tsBean.findRange(0,10000000);
     out.println("<html>");
     out.println("<body>");
     out.println("<h1>Tipo de Sala</h1>");
     out.println("<table>");
     out.println("<tr>");
     out.println("<th>Id</th>");
     out.println("<th>Nombre</th>");
     out.println("<th>Activo</th>");
     out.println("<th>Comentarios</th>");
     out.println("<th>Expresion Regular</th>");
     out.println("</tr>");
     StringWriter sw = new StringWriter();
     for (TipoSala registro : registros) {
         sw.append("<tr>").append("<td>").append(registro.getIdTipoSala().toString()).append("</td><td>").append(registro.getNombre()).append("</td><td>").append(registro.getActivo()?"Activo":"Inactivo").append("</td><td>").append(registro.getComentarios()).append("</td><td>").append(registro.getExpresionRegular()).append("</td></tr>");
     }
     out.println(sw.toString());
     out.println("</table>");
     out.println("<hr>");
     out.println("</body>");
     out.println("</html>");
 }
 @Override
 protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
     resp.setContentType("text/html");
     PrintWriter out = resp.getWriter();

     try {
         // Verificar que el ID esté presente
         String idParam = req.getParameter("id");
         if (idParam != null) {
             Long id = Long.parseLong(idParam);

             // Buscar el registro existente
             TipoSala existente = tsBean.findById(id);
             if (existente != null) {
                 // Actualizar los campos si se proporcionan
                 if (req.getParameter("nombre") != null) {
                     existente.setNombre(req.getParameter("nombre"));
                 }
                 if (req.getParameter("activo") != null) {
                     existente.setActivo(Boolean.valueOf(req.getParameter("activo")));
                 }
                 if (req.getParameter("comentarios") != null) {
                     existente.setComentarios(req.getParameter("comentarios"));
                 }
                 if (req.getParameter("expresionRegular") != null) {
                     existente.setExpresionRegular(req.getParameter("expresionRegular"));
                 }

                 tsBean.update(existente);

                 out.println("<html>");
                 out.println("<body>");
                 out.println("<h1> Tipo de Sala Actualizado </h1>");
                 out.println("<hr>");
                 out.println("Tipo Sala actualizado exitosamente: " + existente.getIdTipoSala());
                 out.println("</body>");
                 out.println("</html>");
             } else {
                 // Si no se encuentra el registro
                 resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                 out.println("No se encontró el tipo de sala con ID: " + id);
             }
         } else {
             // Si no se proporciona el ID
             resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
             out.println("Se requiere el parámetro 'id' para actualizar un TipoSala");
         }
     } catch (Exception e) {
         e.printStackTrace();
         resp.setStatus(500);
     }
 }
    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();

        try {
            // Verificar que el ID esté presente
            String idParam = req.getParameter("id");
            if (idParam != null) {
                Long id = Long.parseLong(idParam);

                // Buscar el registro existente
                TipoSala existente = tsBean.findById(id);
                if (existente != null) {

                    tsBean.delete(existente);

                    out.println("<html>");
                    out.println("<body>");
                    out.println("<h1> Tipo de Sala Eliminado </h1>");
                    out.println("<hr>");
                    out.println("Tipo Sala eliminado exitosamente: " + existente.getIdTipoSala());
                    out.println("</body>");
                    out.println("</html>");
                } else {
                    // Si no se encuentra el registro
                    resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
                    out.println("No se encontró el tipo de sala con ID: " + id);
                }
            } else {
                // Si no se proporciona el ID
                resp.setStatus(HttpServletResponse.SC_BAD_REQUEST);
                out.println("Se requiere el parámetro 'id' para eliminar un TipoSala");
            }
        } catch (Exception e) {
            e.printStackTrace();
            resp.setStatus(500);
        }
    }
}
