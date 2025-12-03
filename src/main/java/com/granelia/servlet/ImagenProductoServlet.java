package com.granelia.servlet;
import com.granelia.dao.productDao;
import com.granelia.dto.productDto;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.io.OutputStream;

import jakarta.ejb.EJB;

@WebServlet("/imagenProducto")
public class ImagenProductoServlet extends HttpServlet {

    @EJB
    private productDao productDao;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        String idParam = req.getParameter("id");
        if (idParam == null) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "Falta parámetro id");
            return;
        }

        int id;
        try {
            id = Integer.parseInt(idParam);
        } catch (NumberFormatException e) {
            resp.sendError(HttpServletResponse.SC_BAD_REQUEST, "id inválido");
            return;
        }

        productDto dto = productDao.buscarPorId(id);
        System.out.println("imagenProducto -> id=" + id + ", dto=" + dto);

        if (dto == null || dto.getImagenBytes() == null) {
            System.out.println("Imagen null o producto no encontrado para id=" + id);
            resp.sendError(HttpServletResponse.SC_NOT_FOUND, "Producto o imagen no encontrados");
            return;
        }

        String contentType = dto.getImagenContentType();
        if (contentType == null || contentType.isBlank()) {
            contentType = "image/jpeg";
        }

        resp.setContentType(contentType);
        resp.setContentLength(dto.getImagenBytes().length);

        try (OutputStream os = resp.getOutputStream()) {
            os.write(dto.getImagenBytes());
        }
    }
}

