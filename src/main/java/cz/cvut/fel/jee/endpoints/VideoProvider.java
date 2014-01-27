/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package cz.cvut.fel.jee.endpoints;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.util.logging.Logger;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cz.cvut.fel.jee.annotations.VideoFilesystem;
import cz.cvut.fel.jee.ejb.VideoService;
import cz.cvut.fel.jee.model.Video;
import org.infinispan.io.GridFilesystem;

/**
 *
 * @author saljack
 */
@WebServlet(name = "VideoProvider", urlPatterns = {"/video"})
public class VideoProvider extends HttpServlet {

    @Inject
    @VideoFilesystem
    GridFilesystem fs;

    @Inject
    VideoService videoService;

    @Inject
    Logger log;

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        Long videoID;
        Video entity = null;
        File file = null;

        try {
            videoID = Long.valueOf(request.getParameter("videoid"));
            entity = videoService.find(videoID);
            file = videoService.getVideoFile(videoID);
        } catch (NumberFormatException ex) {

        }
        if (file == null || !file.exists() || entity == null) {
            log.warning("File not found");
            response.sendError(HttpServletResponse.SC_NOT_FOUND);
        } else {
            response.reset();
            response.setContentType(entity.getMimetype());
            response.setContentLength((int) file.length());
            OutputStream out = response.getOutputStream();
            InputStream in = fs.getInput(file);
            try {
                byte[] buffer = new byte[4096];
                int length;
                while ((length = in.read(buffer)) > 0) {
                    out.write(buffer, 0, length);
                }
                log.warning("File is downloaded with size: " + file.length());
            } catch (IOException ioe) {
                log.warning(ioe.getMessage());
            } finally {
                try {
                    in.close();
                    out.flush();
                    out.close();
                } catch (IOException ioe) {
                    log.warning(ioe.getMessage());
                }
            }

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
