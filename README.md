# CITY8_REGISTER
ce projet permet d'enregistrer les noms des villes entrées dans un champs de texte de l'application dans une base de données

Il a été conçu par les membres du groupe 5:
- KULE WA-KANGITSI Robert
- LUKENGERWA MALY'RO
- TSONGO BISANGI
- BUJIRIRI MWINJA
- KAVIRA KAMATE
- PALUKU MALEKANI
- KASEREKA KIYANA
- AGANO MUTIMA
- BAHATI KAMALEBO

    Ce code fonctionne avec la base de donnée de type MYSQL;
    Vous pouvez modifier le mot de passe du serveur

    INSTRUCTIONS DU CODE

    * vous devez d'abord créer une base de données MYSQL 
    * Et le mot de passe du serveur doit etre "kule@2002"
      soit, il faudra modifier le mot de passe du projet
    * dans Mysql vous devriez tapées ces réquetes
        CREATE DATABASE ville;
        USE ville;
        CREATE TABLE villes(id SMALLINT PRIMARY KEY AUTO_INCREMENT,nom VARCHAR(25));
    * Vous devriez aussi ajouter un fichier jar Mysql_JDBC au projet
    * Et maintenant vous pouvez executer le code la classe Main du projet
