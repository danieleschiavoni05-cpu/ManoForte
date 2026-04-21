package org.elis.manoforte.controller.immagine;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.MultipartConfig;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.*;
import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ImmagineDAO;
import org.elis.manoforte.model.Immagine;
import org.elis.manoforte.model.Utente;
import org.elis.manoforte.utility.dto.DTOGenericResponse;
import tools.jackson.databind.ObjectMapper;
import tools.jackson.databind.json.JsonMapper;
import tools.jackson.datatype.hibernate6.Hibernate6Module;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;

@WebServlet("/updateImage")
@MultipartConfig(
        fileSizeThreshold = 1024 * 1024,
        maxFileSize = 1024 * 1024 * 10,
        maxRequestSize = 1024 * 1024 * 15
)
public class UpdateImageServlet extends HttpServlet {

    private ImmagineDAO immagineDAO;

    public UpdateImageServlet() {
        super();
    }

    public  void init() throws ServletException {
        immagineDAO = DaoFactory.getInstance().getImmagineDAO();
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.setContentType("application/json");
        response.setCharacterEncoding("UTF-8");

        PrintWriter outJson = response.getWriter();
        ObjectMapper mapper = JsonMapper.builder().addModule(new Hibernate6Module()).build();

        HttpSession session = request.getSession();
        Utente loggedUser = (Utente)session.getAttribute("utenteLoggato");


        // aggiungere una variabile d'ambiente con il percorso (cartella inclusa)
        // di dove si vogliono salvare le foto "D:\Desktop\Propic"
        Part part = request.getPart("propic");
        Path folderPath = Paths.get(System.getenv("propicPath"));
        String ext = part.getSubmittedFileName().substring(part.getSubmittedFileName().lastIndexOf('.'));
        String fileName = "propic_"+loggedUser.getNome()+loggedUser.getCognome()+ext;

        if(!Files.exists(folderPath)) Files.createDirectory(folderPath);

        Files.copy(part.getInputStream(), folderPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

        try {
            if(immagineDAO.getImmagineByIdUtente(loggedUser.getId())==null){
                Immagine img = new Immagine();
                img.setNome(fileName);
                img.setPercorso(fileName);
                img.setUtente(loggedUser);
                img.setFotoProfilo(true);
                immagineDAO.inserisciImmagine(img);
            }
            DTOGenericResponse genericResponse = new DTOGenericResponse(true, "Immagine caricata con successo.");
            outJson.print(mapper.writeValueAsString(genericResponse));
            outJson.flush();
        } catch (Exception e) {
            DTOGenericResponse genericResponse = new DTOGenericResponse(false, "Errore nel caricamento dell' immagine.");
            outJson.print(mapper.writeValueAsString(genericResponse));
            outJson.flush();
        }
    }
}
