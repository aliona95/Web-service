# Asmenu web servisas
Norint paleisti servisa, reikia paleisti komandas:

docker build -t people:1 .

docker run -d -p 80:4321 people:1

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

