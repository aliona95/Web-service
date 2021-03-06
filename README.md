# Asmenų web servisas

## Trečia užduotis

### Norint paleisti

#### 1. Nueiti į aplanką task3

#### 2. Atsiųsti failą:

```
docker-compose.yml 
```

#### 3. Paleisti komandą:

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

#### Administratoriaus

```
/login

{
      "username": "eimantas",
      "password": "eima123"
}
```

#### Vartotojo

```
/login

{
      "username": "user",
      "password": "user"
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
