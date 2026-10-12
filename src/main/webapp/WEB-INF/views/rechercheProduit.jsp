<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>

<%@ taglib prefix="c" uri="jakarta.tags.core" %>

<!DOCTYPE html>
<html lang="fr">
<head>
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Rechercher un produit - Gestion Boutique</title>
</head>
<body>

<%@ include file="header.jsp" %>
<%@ include file="menu.jsp" %>

<main class="page-content">

    <section class="page-title-section">
        <h1>Rechercher un produit</h1>
        <p>Recherchez un produit par son nom.</p>
    </section>

    <c:if test="${not empty messageSucces}">
        <div class="alert-success">${messageSucces}</div>
    </c:if>

    <c:if test="${not empty messageErreur}">
        <div class="alert-error">${messageErreur}</div>
    </c:if>

    <section class="search-card">

        <form action="${pageContext.request.contextPath}/produits/recherche"
              method="get"
              class="search-form">

            <div class="form-group">
                <label for="nom">Nom du produit</label>
                <input type="text"
                       id="nom"
                       name="nom"
                       class="form-control"
                       value="${param.nom}"
                       placeholder="Exemple : Riz"
                       maxlength="150">
            </div>

            <div class="form-actions">
                <button type="submit" class="btn-primary">Rechercher</button>

                <a href="${pageContext.request.contextPath}/produits/recherche"
                   class="btn-secondary">Réinitialiser</a>

                <a href="${pageContext.request.contextPath}/produits/ajouter"
                   class="btn-secondary">Ajouter un produit</a>
            </div>

        </form>

    </section>

    <section class="table-card">

        <div class="table-card-header">
            <h2>Liste des produits</h2>
        </div>

        <div class="table-container">

            <table class="data-table">

                <thead>
                    <tr>
                        <th>Identifiant</th>
                        <th>Nom</th>
                        <th>Prix d'achat</th>
                        <th>Prix de vente</th>
                        <th>Stock</th>
                        <th>Actions</th>
                    </tr>
                </thead>

                <tbody>

                    <c:choose>

                        <c:when test="${not empty produits}">

                            <c:forEach var="produit" items="${produits}">

                                <tr>
                                    <td>${produit.id}</td>
                                    <td>${produit.nom}</td>
                                    <td>${produit.prixAchat} FCFA</td>
                                    <td>${produit.prixVente} FCFA</td>
                                    <td>${produit.stock}</td>

                                    <td class="table-actions">
                                        <a href="${pageContext.request.contextPath}/produits/modifier?id=${produit.id}"
                                           class="btn-small btn-edit">Modifier</a>

                                        <form action="${pageContext.request.contextPath}/produits/supprimer"
                                              method="post"
                                              class="inline-form"
                                              onsubmit="return confirmerSuppression();">
                                            <input type="hidden" name="id" value="${produit.id}">
                                            <button type="submit" class="btn-small btn-delete">Supprimer</button>
                                        </form>
                                    </td>
                                </tr>

                            </c:forEach>

                        </c:when>

                        <c:otherwise>

                            <tr>
                                <td colspan="6" class="empty-table-message">
                                    <c:choose>
                                        <c:when test="${not empty nomRecherche}">
                                            Aucun produit ne correspond à la recherche
                                            "<strong>${nomRecherche}</strong>".
                                            <br><br>
                                            <a href="${pageContext.request.contextPath}/produits/recherche">
                                                Voir tous les produits
                                            </a>
                                        </c:when>
                                        <c:otherwise>
                                            Aucun produit enregistré pour le moment.
                                            <br><br>
                                            <a href="${pageContext.request.contextPath}/produits/ajouter">
                                                Ajouter un produit
                                            </a>
                                        </c:otherwise>
                                    </c:choose>
                                </td>
                            </tr>

                        </c:otherwise>

                    </c:choose>

                </tbody>

            </table>

        </div>

    </section>

</main>

<script>
    function confirmerSuppression() {
        return confirm("Voulez-vous vraiment supprimer ce produit ?");
    }
</script>

<%@ include file="footer.jsp" %>
</body>
</html>