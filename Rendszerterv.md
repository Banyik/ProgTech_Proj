# A projekt célja
Megrendelőnk egy játék áruháznak szeretett volna alkalmazást csinálni. Célja volt, hogy az áruház felületéhez csak bejelentkezett felhasználók férhessenek hozzá,
valamint különféle megrendelési (kiszállítási) opciók állhassanak az ügyfelek részére. A felületnek egyszerűnek és könnyen kezelhetőnek kell lennie.

## 2. Projektterv
Páros programozás miatt a projekten elvégzett "munkakörök" megegyeznek, amik az alábbiak:

 - Adatbázis tervezés és annak létrehozása
 - Frontend
 - Backend
 - Unit Tesztelés

## 3. Folyamatok modellje

![Folyamatok modellje](https://cdn.discordapp.com/attachments/817069854033051689/972926738659176518/ToyShop.png)

## 4. Követelmények
 - Funkcionális követelmények
   - Regisztráció biztosítása
   - Felhasználó bejelentkezési adatainak tárolása
   - Felhasználói jogkörök kialakítása
   - MySQL adatbázis alkalmazása
   - Játékok adatainak tárolása, lekérdezése
   - Bővíthetőség végett tesztvezérelt programozás
   
 - Nem funkcionális működés
   - A bejelentkezés nélküli felhasználók ne férhessenek hozzá az áruház tartalmához
   - Játékok módosítása és eltávolítása
     - ehhez csak az adminisztrációs személy férhet hozzá
   - Intuitív, egyszerű design
   
 - Törvényi előírások, szabályok
   - GDPR szabályoknak való megfelelés

## 5. Funkcionális terv
 - Rendszerszereplők
    - Admin  
    - User
 - Rendszerhasználati esetek és lefutásaik:
    - ADMIN:
        - Rendszer feletti korlátlan hozzáférés
        - Játékok létrehozása
        - Játékok szerkesztése
        - Játékok törlése
    
    - USER
        - Játékok lekérdezése
        - Játékok rendelése

## 6. Fizikai környezet
 Asztali alkalmazásként készült Java nyelven
 - Fejlesztői eszközök:
   - IntelliJ IDEA
   - Xampp

## 7. Architektúrális terv
Backend:

-A backend fejlesztéséhez szükséges egy adatbázis szerver, amit MySQL-lel valósítuttuk meg

-Frontend-en kiváltott események lekezelése a megfelelő módokon
  - Adatbázis műveletek
  - Ablakokkal kapcsolatos műveletek (átnavigálás, bezárás, előtérbe helyezés)
  - Események kiváltásához alkalmas function-ök legenerálása
  
##  8. Adatbázis terv
![Adatbázis Terv](https://cdn.discordapp.com/attachments/817069854033051689/972929808004702288/unknown.png)

## 9. Implementációs terv
Az ablakos alkalmazást JAVA nyelven, illetve SWING grafikus felület segítségével készítettük el. Két tervezési mintát építettünk a rendszerbe:
- Logoláshoz alkalmaztuk az Observer (figyelő) Design Pattern-t
- Rendelési optió kiválasztásához a Decorator (díszítő) Design Pattern-t

## 10. Tesztterv
Folyamatos Unit Tesztek írása a fejlesztés egyes fázisaiban, így biztosítva a rendszer bővíthetőségét a későbbiek során.

## 11. Telepítési terv
Egy asztali számítógép szükséges az alkalmazás futtatásához.



## 12. Karbantartási terv
“Az alkalmazás folyamatos üzemeltetése és karbantartása, mely

magában foglalja a programhibák elhárítását, a belső igények változása miatti

módosításokat, valamint a környezeti feltételek változása miatt

megfogalmazott program-, illetve állomány módosítási igényeket.

Karbantartás:

Corrective Maintenance: A megrendelő jelenti, ha bármi hibát vélt felfedezni.

Adaptive Maintenance: A program naprakészen tartása és finomhangolása.
