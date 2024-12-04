package sv.edu.ues.occ.ingenieria.prn335_2024.cine.bondary.rest.server;

import jakarta.annotation.Resource;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.PathParam;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import jakarta.ws.rs.core.StreamingOutput;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;

import javax.net.ssl.SSLEngineResult;
import javax.sql.DataSource;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("reporte")
public class ReporteResource implements Serializable {

    @Resource (lookup = "jdbc/pgdb")
    DataSource poolDeConexiones;


    @GET
    @Path("{reporte}")
    public Response getReporte(
            @PathParam("reporte")
            String reporte
    ){
        HashMap parametros = new HashMap();
        String path;
        switch (reporte){
            case "tipo_sala":
                path = "/repostes/TipoSalaRPT.jasper";
                break;
            default:
                return Response.status(Response.Status.NOT_FOUND).
                        header("Report-NotFound", reporte).
                        build();
        }
        if(path!=null){
            try{
                InputStream fuenteReporte = getClass().getResourceAsStream(path);
                if(fuenteReporte!=null){
                    JasperPrint impresor = JasperFillManager.fillReport(fuenteReporte, parametros, poolDeConexiones.getConnection());

                    StreamingOutput salida = new StreamingOutput() {
                        @Override
                        public void write(OutputStream outputStream) throws IOException, WebApplicationException {
                            try {
                                JasperExportManager.exportReportToPdfStream(impresor, outputStream);
                            } catch (JRException e) {
                                throw new IOException("No se pudo exportar el reporte");
                            }

                        }
                    };
                    return Response.ok(salida, "aplication/pdf").build();
                }

            }catch (Exception e){
                Logger.getLogger(getClass().getName()).log(Level.SEVERE,e.getMessage());
            }

        }
        return Response.serverError().build();
    }
}