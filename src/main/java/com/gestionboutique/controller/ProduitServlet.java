package com.gestionboutique.controller;

import com.gestionboutique.model.Produit;
import com.gestionboutique.service.ProduitService;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;
import java.math.BigDecimal;
import java.sql.SQLException;
import java.util.Collections;
import java.util.List;

@WebServlet(urlPatterns = {
        "/produits/ajouter",
        "/produits/recherche",
        "/produits/modifier",
        "/produits/supprimer"
})
public class ProduitServlet extends HttpServlet {

    private ProduitService produitService;

    @Override
    public void init() throws ServletException {
        produitService = new ProduitService();
    }

    // ==================== GET ====================
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String path = request.getServletPath();

        if ("/produits/ajouter".equals(path)) {
            afficherFormulaireAjout(request, response);
        } else if ("/produits/modifier".equals(path)) {
            afficherFormulaireModification(request, response);
        } else {
            afficherRecherche(request, response);
        }
    }

    // ==================== POST ====================
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");

        String path = request.getServletPath();

        if ("/produits/ajouter".equals(path)) {
            enregistrerProduit(request, response);
        } else if ("/produits/modifier".equals(path)) {
            modifierProduit(request, response);
        } else if ("/produits/supprimer".equals(path)) {
            supprimerProduit(request, response);
        } else {
            response.sendRedirect(request.getContextPath() + "/produits/recherche");
        }
    }

    // ==================== ACTIONS ====================

    private void afficherFormulaireAjout(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        request.setAttribute("titrePage", "Ajouter un produit");
        request.getRequestDispatcher("/WEB-INF/views/ajoutProduit.jsp")
               .forward(request, response);
    }

    private void afficherRecherche(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String nomRecherche = request.getParameter("nom");
        if (nomRecherche != null) nomRecherche = nomRecherche.trim();

        try {
            List<Produit> produits = produitService.rechercherProduits(nomRecherche);
            request.setAttribute("produits", produits);
            request.setAttribute("nomRecherche", nomRecherche);
        } catch (SQLException exception) {
            log("Erreur lors de la recherche des produits.", exception);
            request.setAttribute("produits", Collections.emptyList());
            request.setAttribute("messageErreur", "Impossible de charger les produits.");
        }

        // Message flash
        String flash = (String) request.getSession().getAttribute("flashMessage");
        if (flash != null) {
            request.setAttribute("messageSucces", flash);
            request.getSession().removeAttribute("flashMessage");
        }

        request.setAttribute("titrePage", "Rechercher un produit");
        request.getRequestDispatcher("/WEB-INF/views/rechercheProduit.jsp")
               .forward(request, response);
    }

    private void afficherFormulaireModification(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));

            // ⚠️ ICI : rechercherParId retourne Produit (ou null), PAS Optional
            Produit produit = produitService.rechercherParId(id);

            if (produit == null) {
                request.getSession().setAttribute("flashMessage", "Produit introuvable.");
                response.sendRedirect(request.getContextPath() + "/produits/recherche");
                return;
            }

            request.setAttribute("produit", produit);
            request.setAttribute("titrePage", "Modifier un produit");
            request.getRequestDispatcher("/WEB-INF/views/modifierProduit.jsp")
                   .forward(request, response);

        } catch (NumberFormatException | SQLException e) {
            log("Erreur chargement produit.", e);
            response.sendRedirect(request.getContextPath() + "/produits/recherche");
        }
    }

    private void enregistrerProduit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Produit produit = new Produit();
            produit.setNom(request.getParameter("nom"));
            produit.setPrixAchat(BigDecimal.valueOf(parseDouble(request.getParameter("prixAchat"), "prix d'achat")));
            produit.setPrixVente(BigDecimal.valueOf(parseDouble(request.getParameter("prixVente"), "prix de vente")));
            produit.setStock(parseInt(request.getParameter("stock"), "stock"));

            produitService.ajouterProduit(produit);

            request.getSession().setAttribute("flashMessage",
                    "Le produit \"" + produit.getNom() + "\" a bien été enregistré.");
            response.sendRedirect(request.getContextPath() + "/produits/recherche");
            return;

        } catch (IllegalArgumentException exception) {
            request.setAttribute("messageErreur", exception.getMessage());
        } catch (SQLException exception) {
            log("Erreur lors de l'enregistrement du produit.", exception);
            request.setAttribute("messageErreur", "Impossible d'enregistrer le produit.");
        }

        request.setAttribute("titrePage", "Ajouter un produit");
        request.getRequestDispatcher("/WEB-INF/views/ajoutProduit.jsp")
               .forward(request, response);
    }

    private void modifierProduit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        Long id = null;
        try {
            id = Long.parseLong(request.getParameter("id"));

            Produit produit = produitService.rechercherParId(id);

            if (produit == null) {
                request.getSession().setAttribute("flashMessage", "Produit introuvable.");
                response.sendRedirect(request.getContextPath() + "/produits/recherche");
                return;
            }

            produit.setNom(request.getParameter("nom"));
            produit.setPrixAchat(BigDecimal.valueOf(parseDouble(request.getParameter("prixAchat"), "prix d'achat")));
            produit.setPrixVente(BigDecimal.valueOf(parseDouble(request.getParameter("prixVente"), "prix de vente")));
            produit.setStock(parseInt(request.getParameter("stock"), "stock"));

            produitService.modifierProduit(produit);

            request.getSession().setAttribute("flashMessage",
                    "Le produit \"" + produit.getNom() + "\" a bien été modifié.");
            response.sendRedirect(request.getContextPath() + "/produits/recherche");
            return;

        } catch (IllegalArgumentException exception) {
            request.setAttribute("messageErreur", exception.getMessage());
        } catch (SQLException exception) {
            log("Erreur lors de la modification du produit.", exception);
            request.setAttribute("messageErreur", "Impossible de modifier le produit.");
        }

        // En cas d'erreur, on réaffiche le formulaire avec les valeurs actuelles
        try {
            if (id != null) {
                Produit produit = produitService.rechercherParId(id);
                if (produit != null) {
                    request.setAttribute("produit", produit);
                }
            }
        } catch (SQLException ignored) { }

        request.setAttribute("titrePage", "Modifier un produit");
        request.getRequestDispatcher("/WEB-INF/views/modifierProduit.jsp")
               .forward(request, response);
    }

    private void supprimerProduit(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        try {
            Long id = Long.parseLong(request.getParameter("id"));
            produitService.supprimerProduit(id);

            request.getSession().setAttribute("flashMessage", "Le produit a bien été supprimé.");
        } catch (NumberFormatException e) {
            request.getSession().setAttribute("flashMessage", "Identifiant invalide.");
        } catch (IllegalArgumentException e) {
            request.getSession().setAttribute("flashMessage", e.getMessage());
        } catch (SQLException e) {
            log("Erreur lors de la suppression du produit.", e);
            request.getSession().setAttribute("flashMessage",
                    "Impossible de supprimer ce produit (il est utilisé dans un achat ou une livraison).");
        }

        response.sendRedirect(request.getContextPath() + "/produits/recherche");
    }

    // ==================== HELPERS ====================

    private double parseDouble(String valeur, String champ) throws IllegalArgumentException {
        if (valeur == null || valeur.trim().isEmpty()) {
            throw new IllegalArgumentException("Le " + champ + " est obligatoire.");
        }
        try {
            double resultat = Double.parseDouble(valeur.trim());
            if (resultat < 0) {
                throw new IllegalArgumentException("Le " + champ + " doit être positif.");
            }
            return resultat;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Le " + champ + " est invalide.");
        }
    }

    private int parseInt(String valeur, String champ) throws IllegalArgumentException {
        if (valeur == null || valeur.trim().isEmpty()) {
            throw new IllegalArgumentException("Le " + champ + " est obligatoire.");
        }
        try {
            int resultat = Integer.parseInt(valeur.trim());
            if (resultat < 0) {
                throw new IllegalArgumentException("Le " + champ + " doit être positif.");
            }
            return resultat;
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Le " + champ + " est invalide.");
        }
    }
}