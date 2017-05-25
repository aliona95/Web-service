# Asmenų web servisas

## Trečia užduotis

### Norint paleisti

#### 1. Atsiųsti failą:

```
docker-compose.yml 
```

#### 2. Paleisti komandą:

```
docker-compose up -d
```

### Registracija

```
/register

{
      "username": "petras",
      "password": "petras123",
      "permissions": "admin"
}

{
      "username": "marius",
      "password": "marius123",
      "permissions": "user"
}
```

### Prisijungimas

```
/login

{
      "username": "eimantas",
      "password": "eimantas123"
}
```

## Antra užduotis

### Norint paleisti abu servisus:

#### 1. Atsisiųsti failą:

```
docker-compose.yml 
```

#### 2. Paleisti komandą:

```
docker-compose up -d
```
### Naujos komandos:

```
/people/companies GET
/people/{id}/company GET
/people/company/{id} GET
/people/company POST
/people/company/{id} PUT
```
### Pavyzdiniai duomenys
```
{
      "name":"Petras",
      "surname":"Petraitis",
      "gender":"male",
      "address":"Ikalnes 45",
      "companyId":[5,1]
}
```

## Pirma užduotis

### Norint paleisti servisą, reikia paleisti komandas:

```
docker build -t people:1 .

docker run -d -p 80:4321 people:1
```

### DockerHub'e:

```
docker pull eima1995/people:1

docker run -d -p 80:4321 eima1995/people:1
```

### Komandos

```
/people GET, POST
/people/{id} GET, PUT, DELETE
/people/name//{name} GET
/people/gender//{gender} GET
```

### Pavyzdiniai duomenys
```
{
      "name":"Petras",
      "surname":"Petraitis",
      "gender":"male",
      "address":"Ikalnes 45"
}
```
