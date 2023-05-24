# Java_RideSharing
Proiect pentru cursul de Programare Avansata pe Obiecte

## Descriere
Scopul acestui proiect este de a simula structura unui serviciu de Ride-Sharing (ex. Bolt, Uber), si de a implementa cateva dintre functionalitatile 
cuprinse intr-un astfel de serviciu.

## Etapa 1

### Definirea Sistemului
Sistemul este constituit din urmatoarele clase:

* Vehicul, clasa abstracta care are urmatoarele subclase, care salveaza detaliile fiecarui mijloc de transport
  + Masina
  + Elicopter
  + Trasura
  + Motocicleta cu atas
* User, clasa abstracta care are urmatoarele subclase
  + Conducator
  + Calator
* CardPlata, clasa ce va fi folosita pentru a salva detaliile de plata ale fiecarui calator
* Cupon - cupoane de reduceri, acordate de asemenea calatorilor pe baza fidelitatii lor
* Cursa

Functionalitatile implementate sunt:
1. Create, read pe Useri
2. Create, read pe Vehicule
3. Create, read pe (Tipuri de) Cupoane
4. Create, read pe Carduri bancare
5. Create, read pe Curse
6. Acordare cupoane calatorilor pe baza numarului de curse efectuate (cate un cupon random per 3 curse)
7. Afisarea distantei maxime a calatoriilor pe care le poate face un calator cu fiecare vehicul in functie de soldul cumulat de pe toate cardurile sale
8. Acordarea unui bonus de 20 ron fiecarui conducator pentru fiecare 5 curse efectuate
9. Protectia animalelor a interzis abuzul cailor! Stergea tuturor trasurilor cu un singur cal (e pus la prea multa munca)
10. Taxarea fiecarei curse daca nu au fost deja taxate
11. Afisarea unor statistici sumare:
    * Soferul cu cele mai multe curse efectuate
  	* Calatorul cu cele mai multe curse efectuate


### Detalii de implementare
* Toate campurile sunt private si pot fi accesate si modificate prin metode publice
* Exista aplicatii ale conceptului de agregare, de exemplu campurile "carduri" si "cupoane" din clasa "Calator", ce folosesc colectii de obiecte.
* Exista o colectie sortata: in clasa "Administrativ", a cupoanelor, este folosit un TreeSet, care foloseste o clasa creata special pentru aceasta sarcina, care implementeaza interfata "Comparator"
* Mostenirea este utilizata pentru ierarhiile create de clasele abstracte "Vehicul" si "User"
* Clasa statica "AppService" implementeaza toate operatiunile aplicatiei, prin metode statice
* Meniul text-based (sper ca in viitor GUI) este implementat in clasa Main
* Metodele de adaugare se folosesc, in mare, de o rigurozitate corespunzatoare (verificare REGEX a campurilor introduse, exception handling)


## Etapa 2
### Partea 1 
Extindeti proiectul din prima etapa prin realizarea persistentei utilizand o baza de date relationala si JDBC

Pasii urmati in rezolvarea cerintei
1. Instalarea software-ului XAMPP, pentru crearea si gazduirea bazei de date MySQL
2. Crearea urmatoarelor tabele, avand coloane adecvate
    * AssociativeCuponUser
    * Calator
    * CardPlata
    * Conducator
    * Cupon
    * User
3. Crearea contextului bazei de date in meniul principal, prin importarea claselor specifice, activarea driverelor pentru SGBD incluse de catre JDBC
4. Crearea unui serviciu destinat operatiunilor specifice bazei de date: clasa DataBaseService din packetul services, care implementeaza operatii CRUD pe tabelele:
    * Calator
    * CardPlata
    * Cupon
    * Conducator
5. Extinderea structurilor de date utilizate pentru stocarea nepersistenta a datelor prin adaugarea datelor acestora la baza de date
6. Sincronizarea operatiilor bazei de date cu structurile de date anterior mentionate, pentru a pastra un grad ridicat de incapsulare (operatiile avansate, de tipul taxarii erau implementate pe baza structurilor de date, ceea ce am vrut sa si pastrez, in ciuda implemetarii unei baze de date mai avansate)
7. Adaptarea noilor operatii, de tip Update si Delete in meniul principal bazat pe text al aplicatiei

### Partea 2
Realizati un serviciu de audit

In realizarea acestei cerinte am creat o clasa de tip serviciu noua, intitulata "AuditService", inclusa in pachetul services, care indeplineste cerinta folosind o singura metoda.
  * Metoda deschide fisierul si scrie la sfarsitul sau un string primit ca parametru, alaturi de un timestamp, primit de asemenea ca parametru.
  * Metoda este apelata in mod static la sfarsitul fiecarei operatii BD din serviciul DataBaseService, asadar asigura functionalitate maxima cu o implementare minima
