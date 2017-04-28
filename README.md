# Asmenu web servisas

## Antra užduotis

### Norint paleisti abu servisus, reikia paleisti komandas:

docker-compose.yml 

docker-compose up -d

## Pirma užduotis
### Norint paleisti servisą, reikia paleisti komandas:

docker build -t people:1 .

docker run -d -p 80:4321 people:1

### DockerHub'e:

docker pull eima1995/people:1

docker run -d -p 80:4321 eima1995/people:1

# Komandos
/people GET, POST

/people/{id} GET, PUT, DELETE

/people/name//{name} GET

/people/gender//{gender} GET

# Pavyzdiniai duomenys
{

      "id":1,
   
      "name":"Petras",
   
      "surname":"Petraitis",
   
      "gender":"male",
   
      "address":"Ikalnes 45"
   
}

