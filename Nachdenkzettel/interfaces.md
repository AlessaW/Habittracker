##1. Spezifizieren Sie das Interface „Stecker“ für diese Implementation. 
 - Anzahl Löcher
 -  Position Löcher
 -  Erdung
 -  Position Erdung


##2. Ist das <br> a) eine korrekte Ableitung von der obigen Implementation? <br> b) eine korrekte Implementation Ihres Interfaces?
   
a) keine korrekte Ableitung, da die Erdung nicht passt -> falsche Form

b) joa weil es passt. Da wird zwar ein Feld (Erdung) nicht verwendet, aber es ist trotzdem kompatibel
   
##3. Und das hier?
 Ja?

##4. Wie seht es mit 220 V aus? Interface oder Implementation? Und das Material des Schukosteckers?
   Implementation

##5. Wieviel Spass hätten wir ohne die DIN Norm für Schukostecker oder Eurostecker?
    Hast du auch so viel Spaß? Bist du auch so glücklich jeden Tag...ich hab nen Stecker den ich mag, der nur nirgendwo passt.   

##6. Was gehört alles zum „Interface einer Klasse“ in Java? (Anders formuliert für UX-Leute: wenn ich von jemandem eine Klasse in meinem Code benutze: was ärgert mich, wenn es geändert wird?)
- Felder /Attribute
- Methodennamen

##7. „Class B implements X“. Jetzt fügen Sie eine neue Methode in Interface X ein. Was passiert?
Klasse B muss die neue Methode noch implementieren.
   
##8. Zwei Interfaces sind nicht voneinander abgeleitet, haben aber zufällig die gleiche Methode. Können Sie Implementationen dieser Interfaces polymorph behandeln?
    Interface X {
        public void foo();
    }
   
    Interface Y {
        public void foo();
    }

    class B implements Y {...}

    X x = new B();  ??
    x.foo();        ??
   
Nö. B erbt von X nicht Y, so kann 'new B()' gar nicht funktionieren.
   

##9. Ihr code enthält folgendes statement:  X xvar = new X(); <br> Was ist daran problematisch, wenn Sie eine Applikation für verschiedene Branchen/Kunden/Fälle bauen?
   
Es wird nicht funktionieren, weil X ein Interface ist

   

##10. Von ArrayList ableiten oder eigene Klasse „Catalog“ oder ähnlich bauen und ArrayList<> verwenden? <br> Sprich: soll man von Java Basisklassen ableiten? <br> Beispiele: Vegetable, VegetableCatalog Task, TaskList, GameObject, GameObjectList etc.
    
Hm, ich würde sagen nein? Man kann sie verwenden, aber ableiten ist doof.