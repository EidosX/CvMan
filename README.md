# CV Man

## Informations concernant le premier rendu

Pour l'instant je sert le front-end via le serveur de Nuxt, qui tourne en parallèle de Tomcat.

Pour le rendu final, je ferais en sorte que Nuxt compile le front-end en SSG pour avoir des fichiers statiques, qui seront servis par tomcat.  
Cela se fera surement avec un script npm (ou yarn)

J'ai désactivé Spring Security pour l'instant, en attendant de le refaire avec les JWT.

## Pour démarrer le projet

- Démarrer le serveur de développement frontend:
  ```
  cd cvman-frontend
  yarn dev
  ```
- Dans un autre terminal, démarrer le serveur tomcat:
  ```
  gradle run
  ```
- Accéder au site sur http://localhost:3000/users
