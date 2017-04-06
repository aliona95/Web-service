# Asmenu web servisas
Norint paleisti servisà, reikia paleisti komandas:
docker build -t people:1 .
docker run -d -p 80:4321 people:1

# Komandos
/people GET, PUT
/people/1 GET, POST, DELETE
/people/name//Petras GET
/people/gender//male GET

# Pavyzdiniai Duomenys
{
   "id":1,
   "name":"Petras",
   "surname":"Petraitis",
   "gender":"male",
   "address":"Ikalnes 45"
}

