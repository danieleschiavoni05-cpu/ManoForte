package org.elis.manoforte.controller.immagine;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.elis.manoforte.dao.definition.DaoFactory;
import org.elis.manoforte.dao.definition.ImmagineDAO;
import org.elis.manoforte.exception.NoImageException;
import org.elis.manoforte.utility.Utility;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

@WebServlet("/getImmagine")
public class GetImmagineServlet extends HttpServlet {
    private ImmagineDAO immagineDAO;
    @Override
    public void init()throws ServletException {
        immagineDAO = DaoFactory.getInstance().getImmagineDAO();
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)throws IOException {
        try{
            response.setContentType("image/*");
            String path = request.getParameter("path");
            	
            String pathConvertito = Paths.get(System.getenv("propicPath"), path).toString();
            File file = Utility.getFile(pathConvertito);

            response.setContentLength((int)file.length());
            Files.copy(file.toPath(), response.getOutputStream());
          
        }catch(NoSuchFileException | NoImageException e){
            response.sendRedirect(Utility.DEFAULT_PROPIC_PATH);
        }

    }
}
