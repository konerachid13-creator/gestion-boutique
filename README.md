\# 🏪 Gestion Boutique — Application Java EE



!\[Java](https://img.shields.io/badge/Java-17-orange)

!\[Jakarta EE](https://img.shields.io/badge/Jakarta%20EE-10-blue)

!\[PostgreSQL](https://img.shields.io/badge/PostgreSQL-16-336791)

!\[Maven](https://img.shields.io/badge/Maven-3.8+-C71A36)

!\[License](https://img.shields.io/badge/License-MIT-green)



> Application complète de gestion pour une boutique de produits alimentaires.

> Développée en \*\*Java 17\*\*, \*\*Jakarta EE 10\*\*, \*\*PostgreSQL 16\*\* avec architecture \*\*MVC\*\*.



\---



\## 📸 Aperçu



\### Tableau de bord

!\[Dashboard](captures/02-dashboard.png)



\### Nouvel achat

!\[Nouvel achat](captures/04-nouvel-achat.png)



\### Ticket PDF

!\[Ticket](captures/05-ticket-pdf.png)



\*(Toutes les captures sont dans le dossier \[`captures/`](captures/))\*



\---



\## ✨ Fonctionnalités



\- 🔐 \*\*Authentification sécurisée\*\* (BCrypt + rôles ADMIN/VENDEUR)

\- 👥 \*\*Gestion des utilisateurs\*\* (ajout, modification, activation/désactivation)

\- 📦 \*\*Gestion des produits\*\* (ajout, recherche)

\- 🧑 \*\*Gestion des clients\*\* (CRUD complet)

\- 🚚 \*\*Gestion des grossistes\*\* (CRUD complet)

\- 🛒 \*\*Achats\*\* (multi-produits, décrémentation automatique du stock)

\- 📥 \*\*Livraisons\*\* (multi-produits, augmentation du stock)

\- 🎫 \*\*Impression de tickets\*\* au format PDF (iText 8)

\- 📜 \*\*Historiques\*\* avec filtres par date



\---



\## 🛠️ Stack technique



| Catégorie | Technologie |

|-----------|-------------|

| Langage | Java 17 |

| Plateforme | Jakarta EE 10 (Servlets, JSP, JSTL) |

| Base de données | PostgreSQL 16 |

| Pool de connexions | HikariCP |

| Sécurité | BCrypt |

| Génération PDF | iText 8 |

| Build | Maven |

| Serveur | Apache Tomcat 10.1 |



\---



\## 🏗️ Architecture



src/main/java/com/gestionboutique/

├── config/ → DatabaseConnection (HikariCP)

├── controller/ → 23 Servlets (points d'entrée HTTP)

├── dao/ → 6 DAO (accès PostgreSQL)

├── filter/ → AuthFilter, AdminFilter

├── model/ → 9 entités métier

├── service/ → 7 services (logique métier)

└── util/ → SessionUtil, HashGenerator





\*\*Pattern\*\* : MVC en couches (Controller → Service → DAO → Model)



\---



\## 🚀 Installation



\### Prérequis

\- JDK 17+

\- PostgreSQL 16+

\- Apache Tomcat 10.1+

\- Maven 3.8+



\### 1. Cloner le projet

```bash

git clone https://github.com/TON\_USERNAME/gestion-boutique.git

cd gestion-boutique



2.Créer la base de données

psql -U postgres -c "CREATE DATABASE gestion\_boutique;"

psql -U postgres -d gestion\_boutique -f ddl.sql



3.Configurer la connexion



db.url=jdbc:postgresql://localhost:5432/gestion\_boutique

db.user=postgres

db.password=VOTRE\_MOT\_DE\_PASSE



4.Compiler



mvn clean package



5.Déployer



Copiez target/gestion-boutique.war dans TOMCAT\_HOME/webapps/



6.Démarrer Tomcat



TOMCAT\_HOME/bin/startup.bat    (Windows)

TOMCAT\_HOME/bin/startup.sh     (Linux/Mac)



7.Accéder à l'application

http://localhost:8080/gestion-boutique/login





