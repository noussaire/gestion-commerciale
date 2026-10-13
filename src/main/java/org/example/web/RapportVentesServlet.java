package org.example.web;

import org.example.dto.RapportVentesCategorie;
import org.example.service.RapportVentesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.util.List;

@WebServlet("/rapport-ventes")
public class RapportVentesServlet extends HttpServlet {
    private final RapportVentesService rapportVentesService = new RapportVentesService();

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        List<RapportVentesCategorie> rapports = rapportVentesService.ventesDuMoisPrecedent();

        response.setContentType("text/html;charset=UTF-8");
        try (PrintWriter out = response.getWriter()) {
            out.println("<!doctype html>");
            out.println("<html lang=\"fr\"><head><meta charset=\"UTF-8\"><title>Rapport des ventes</title></head><body>");
            out.println("<h1>Rapport des ventes du mois precedent</h1>");
            out.println("<table border=\"1\" cellpadding=\"6\" cellspacing=\"0\">");
            out.println("<thead><tr><th>Categorie</th><th>Chiffre d'affaires</th></tr></thead><tbody>");
            for (RapportVentesCategorie rapport : rapports) {
                BigDecimal total = rapport.getChiffreAffaires() != null
                        ? rapport.getChiffreAffaires()
                        : BigDecimal.ZERO;
                out.printf("<tr><td>%s</td><td>%s</td></tr>%n",
                        escape(rapport.getCategorieNom()),
                        total);
            }
            out.println("</tbody></table>");
            out.println("</body></html>");
        }
    }

    private String escape(String value) {
        if (value == null) {
            return "";
        }
        return value.replace("&", "&amp;")
                .replace("<", "&lt;")
                .replace(">", "&gt;")
                .replace("\"", "&quot;");
    }
}
