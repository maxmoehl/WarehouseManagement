# Programmierprojekt

Warehousemanagment Software für Günther Steiner

### Weitere Ressourcen:
* [Protokolle und Infos](https://github.com/maxmoehl/WarehouseManagment/wiki)
* [Dokumentation des Java-Codes als JavaDoc](https://maxmoehl.github.io/)

## Setup-Guide:
Falls nicht vorhanden: aktuelle Java-Version installieren.

[Aktuelle Release](https://github.com/maxmoehl/WarehouseManagment/releases) herunterladen und ausführen.

## Userdoku
Komponenten:
1. [Menüleiste](https://github.com/maxmoehl/WarehouseManagment/blob/master/README.md#1-men%C3%BCleiste)
2. [Startseite](https://github.com/maxmoehl/WarehouseManagment/blob/master/README.md#2-startseite)
3. [Karte des Warenlagers](https://github.com/maxmoehl/WarehouseManagment/blob/master/README.md#3-karte-des-warenlagers)
4. [Lagerübersicht](https://github.com/maxmoehl/WarehouseManagment/blob/master/README.md#4-lager%C3%BCbersicht)
5. [Lieferungsübersicht](https://github.com/maxmoehl/WarehouseManagment/blob/master/README.md#5-lieferungs%C3%BCbersicht)
6. [Lagerbestandswarnungen](https://github.com/maxmoehl/WarehouseManagment/blob/master/README.md#6-lagerbestandswarnungen)

### 1. Menüleiste
Unter dem Punkt exportieren können die aktuellen Lagerbestände und die Lieferungsliste als CSV-Datei exportiert werden. Dazu einfach das Menü öffnen, Option wählen und einen Speicherort angeben.

### 2. Startseite
Die Startseite ist aufgeteilt in ca 2/3 Karte und 1/3 Zusatzoptionen. Die Map zeigt das aktuelle Warenlager und alle Bewegungen an. Die großen Menübuttons zeigen komprimiert zusätzliche Informationen an und durch anklicken kann man ein extra Detailfenster öffnen.

### 3. Karte des Warenlagers
Die Karte ist interaktiv, zeigt eine Übersicht aller im Lager befindlichen Objekte an (Lagereinheiten, Roboter und Andockstellen). Durch anklicken der einzelnen Lagereinheiten öffnet sich ein Konfigurationsfenster in dem Informationen angezeigt werden und in dem man den Materialtyp ändern kann. In den Andockstellen gibt es einen + und - Button um an der Andockstelle einen neuen Roboter zu erzeugen beziehungsweise zu entfernen. Wenn ein Roboter entfernt wird arbeitet er so lange weiter bis er alle Materialien aus dem Inventar entfernt hat.

### 4. Lagerübersicht
Zeigt in tabellarischer Form die aktuellen Lagerbestände und die Konfiguration an.

### 5. Lieferübersicht
Zeigt in tabellarischer Form alle Lieferungen mit ihren Informationen an.

### 6. Lagerbestandswarnung
Ein Lagerbestand wird als Niegrid bezeichnet, sollte er unter 5% liegen (Aktueller Bestand/Gesamtkapazität der Lagereinheit). Jede Niedrige Lagereinheit wird in der Tabelle nach drücken auf den Knopf "Niedrige Lagerbestände" angezeigt. Des weiteren wird eine Kurzansicht anhand der Progressbar (die auf dem Knopf zu finden ist) die niedrigste Lagereinheit anzeigen. 
