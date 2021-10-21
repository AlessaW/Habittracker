## 3.Wie kommt man vom Use-Case-Diagramm zum Klassendiagramm?

Als Daumenregel gilt: Alle Substantive werden zu Klassen und alle Verben werden zu Methoden. Unter Umständen sind aber die notwendigen Methoden in einer anderen Klasse, je nachdem wo die Methode mehr Sinn macht. <br>
Beispiel: Use-Case: create file -> Klasse: file, Methode: create -> in der Klasse File manager

## 4.Was ist der Unterschied zwischen Aggregation und Komposition?
Aggregation: Teil kann ohne das Ganze existieren <br>
Komposition: abhängig von dem Ganzen, kein Teil, kein Ganzes

### Was bedeutet der Unterschied in einer Datenbank?
Bei einer Aggregation, wenn das Ganze gelöscht wird, bleib das Teil vom Ganzen unverändert.
Bei einer Komposition muss das Teil gelöscht werden, wenn das Ganze gelöscht wird.
## 6.Was bedeutet es, wenn eine Klasse in UML ein "public" Attribut hat?
Im Klassendiagramm bedeutet das, dass es mit einem + gekennzeichnet ist.
Und in der Java Implementierung wird die Klasse auch auf public gesetzt.