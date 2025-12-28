FROM node:20-alpine

WORKDIR /app

# Kopiowanie plików zależności
COPY package*.json ./

# Instalacja zależności
RUN npm install

# Kopiowanie reszty kodu (opcjonalne przy dev, ale dobra praktyka)
COPY . .

# Domyślna komenda (zostanie nadpisana przez docker-compose, ale warto ją mieć)
CMD ["npm", "run", "dev"]