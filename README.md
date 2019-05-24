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
Am obersten Bereich der Anwendung befindet sich ein Menüstreifen, der sämtliche Exportfunktionalitäten bietet. Unter der Überschrift exportieren können die aktuellen Lagerbestände und die Lieferungsliste als CSV-Datei exportiert werden. Dazu einfach auf "Exportieren" in der Menüleiste clicken, danach entweder "Lagerbestände" oder "Anstehende Lieferungen" auswählen. Zu guter letzt muss noch der gewünschter Speicherort angegeben werden, und die Daten werden exportiert.  

### 2. Startseite
Die Startseite ist aufgeteilt in ca 2/3 Karte und 1/3 Zusatzoptionen. Die Map zeigt das aktuelle Warenlager und alle Bewegungen in Echtzeit an. Die großen Menübuttons zeigen komprimiert zusätzliche Informationen an, so wie z.B. die Gesamtauslastung anhand einer ProgressBar und durch anklicken wird ein extra Detailfenster geöffnet.

### 3. Karte des Warenlagers
Die Karte ist interaktiv und zeigt eine Übersicht aller im Lager befindlichen Objekte an (Lagereinheiten, Roboter und Andockstellen).  Durch anklicken der einzelnen Lagereinheiten öffnet sich ein Konfigurationsfenster in dem Informationen angezeigt werden und in dem man den Materialtyp der Lagereinheit ändern kann. In den Andockstellen gibt es einen + und - Button um an der Andockstelle einen neuen Roboter zu erzeugen beziehungsweise zu entfernen. Die Roboter werden somit an diese Andockstelle zugewiesen. Wenn ein Roboter entfernt wird arbeitet er so lange weiter bis er alle Materialien aus seinem Inventar entfernt hat.

### 4. Knopf: "Gesamtkapazität"
Dieser Knopf zeigt auf seiner Fläche zusätzlich zum Namen auch eine grafische Darstellung der Gesamtauslastung des Lagers. Sobald der Benutzer auf den Knopf drückt, werden die aktuellen Lagerbestände und die Konfiguration der Lagereinheiten in tabellarischer From angezeigt.

### 5. Knopf: "Anstehende Lieferungen"
Dieser Knopf zeigt auf dem ersten Blick einen Subtext an, der eine vereinfachte Übersicht zur nächsten Lieferung enthält. Nach Knopfdruck wird eine ausführlichere Tabelle sichtbar, die jegliche Information zu allen Lieferungen anzeigt. 

### 6. Lagerbestandswarnung
Auf dieser Fläche wird der niedrigste Bestand eines Materialtyps angezeigt (Aktueller Bestand/Gesamtkapazität der Lagereinheit). Des weiteren wird eine Kurzansicht anhand der Progressbar (die auf dem Knopf zu finden ist) die niedrigste Lagereinheit anzeigen. 
