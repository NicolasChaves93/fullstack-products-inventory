# -------------------------------------
# ETAPA 1: BUILD (Compilación)
# -------------------------------------
FROM node:20-alpine AS build
WORKDIR /app

# Copia e instala dependencias
COPY package.json .
COPY package-lock.json .
RUN npm install

# Copia el código fuente
COPY . .

# Comando de compilación de Angular.
# Esto genera los archivos estáticos en /app/dist/<nombre-del-proyecto>
RUN npm run build -- --configuration production

# -------------------------------------
# ETAPA 2: RUNTIME (Ejecución con Nginx)
# -------------------------------------
# Usar Nginx es una práctica común para servir apps estáticas como Angular.
FROM nginx:alpine

COPY --from=build /app/dist/store-catalog-mgmt/browser /usr/share/nginx/html

# Expón el puerto que Nginx usará
EXPOSE 80

# Comando para iniciar Nginx
CMD ["nginx", "-g", "daemon off;"]