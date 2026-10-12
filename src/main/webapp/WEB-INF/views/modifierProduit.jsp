<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Modifier un produit - Gestion Boutique</title>
</head>
<body>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<main class="page-container">

    <header class="page-header">
        <h1>Modifier un produit</h1>
        <p>Mettez à jour les informations du produit.</p>
    </header>

    <c:if test="${not empty messageErreur}">
        <div class="alert-error">${messageErreur}</div>
    </c:if>

    <form method="post"
          action="${pageContext.request.contextPath}/produits/modifier"
          class="form-card">

        <input type="hidden" name="id" value="${produit.id}">

        <div class="form-group">
            <label for="nom">Nom du produit *</label>
            <input type="text" id="nom" name="nom" class="form-control"
                   value="${produit.nom}" required maxlength="150">
        </div>

        <div class="form-row">
            <div class="form-group">
                <label for="prixAchat">Prix d'achat (FCFA) *</label>
                <input type="number" step="0.01" id="prixAchat" name="prixAchat"
                       class="form-control" value="${produit.prixAchat}"
                       required min="0">
            </div>

            <div class="form-group">
                <label for="prixVente">Prix de vente (FCFA) *</label>
                <input type="number" step="0.01" id="prixVente" name="prixVente"
                       class="form-control" value="${produit.prixVente}"
                       required min="0">
            </div>
        </div>

        <div class="form-group">
            <label for="stock">Stock actuel *</label>
            <input type="number" id="stock" name="stock"
                   class="form-control" value="${produit.stock}"
                   required min="0">
            <small class="form-hint">
                Le stock peut aussi être modifié automatiquement par les achats et les livraisons.
            </small>
        </div>

        <div class="form-actions">
            <a href="${pageContext.request.contextPath}/produits/recherche"
               class="btn-secondary">Annuler</a>
            <button type="submit" class="btn-primary">Enregistrer les modifications</button>
        </div>

    </form>

</main>

<%@ include file="footer.jsp" %>
</body>
</html>