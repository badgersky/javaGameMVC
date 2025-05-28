## javaGameMVC

### Aplikacja webowa zrobiona w frameworku Spring Boot jako projekt zaliczeniowy na zajęcia z programowania w Javie

### 1. Baza danych
#### 1.1 Baza danych składa się z 8 tabeli:

- Users - przechowująca dane o użytkowniku.
- Game - przechowująca dane o grach.
- Studio - przechowująca dane o studiach game developerskich.
- Genre - przechowująca typy gier.
- AccountType - przechowująca typy kont użytkowników.
- Likes - Relacja wiele do wielu pozwalająca przechowywać informacje o grach polubionych przez użytkowników.
- Genre-Game - Relacja wiele do wielu pzwalająca przypisać typy o gry.
- Studio-Game - Relacja wiele do wielu pozwalająca przypisać studia developreskie do gry.

#### 1.2 Diagram ERD bazy danych

![diagram erd](./images/java_project_erd.png)

### 2. Aplikacja
#### 2.1 Encje

W folderze entity utworzony encje, które reprezentują tabele z bazy dancyh. Model dancyh jest następnie przez Hibernate mapowany na tabele w bazie.
Wszystkie utworzone encje odpowiadają głównym tabelą z bazy:
- Users
- Game
- Studio
- Genre
- AccountType

Do utworzenia tabel pośrednich użyto adnotacji @ManyToMany:

![przykład many to many](./images/many_to_many.png)

Przykład całej encji Game:

![encja game](./images/game.png)

#### 2.2 Repozytoria

Repozytoria są interfejsami pozwalającymi na dostęp do danych za pośrednictwem encji.
Przykład GameRepository:

![game repository](./images/game_repository.png)

#### 2.3 Serwisy

Serwisy są klasami implementującymi logikę biznesową aplikacji. Pośredniczą pomiędzy kontrolerami, a bazą danych.
Przykład GameService:

![game service](./images/game_service.png)

#### 2.4 Kontrolery

