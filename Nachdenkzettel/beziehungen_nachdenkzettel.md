# Nachdenkzettel Beziehungen/Vererbung
## 1.
**„Class B extends X“. Jetzt fügen Sie eine neue Methode in X ein. Müssen Sie B anpassen?**

Wenn es eine abstrakte Methode ist, muss diese in B noch definiert werden. Ansonsten muss B nicht verändert werden.

## 2.1 
**Class B extends X  { <br>
   public void newMethodinB()  {  .... } <br>
} <br>
Jetzt fügen Sie eine neue public Methode in ihre abgeleitete Klasse ein. Sie möchten diese neue
Methode im Code verwenden. Prüfen Sie die folgenden Codezeilen: <br>
    X x = new B(); <br>
    x.newMethodinB(); <br>
Was stellen Sie fest?**

Das Objekt ist vom Typ X und verfügt deswegen über die Methode in B nicht. Entweder muss man die Methode in X abstrakt anlegen oder konkret und dann überschreiben.



## 2.2
**Class B extends X  { <br>
    @override           <br>
    public void methodinB()  {  .... } <br>
} <br>
Jetzt überschreiben Sie eine Methode der Basisklasse in ihrer abgeleitete Klasse. Sie möchten diese
neue Methode im Code verwenden. Prüfen Sie die folgenden Codezeilen: <br>
    X x = new B(); <br>
    x.methodinB(); <br>
Was stellen Sie fest?**

Klappt. <br>
(räusper) Die überschriebene Version in der Klasse B wird ausgeführt nicht die Definition in X.

## 3. 
**Versuchen Sie „Square“ von Rectangle abzuleiten (geben Sie an welche Methoden Sie in die Basisklasse tun und welche Sie in die abgeleitete Klasse tun)** 

Rectangle: Rectangle(width, heigth) { <br>
    this.width = width; <br>
    this.heigth = heigth; <br>
}

Square: Square(width) { <br>
    super(width,width); <br>
}

// This is Super();


## 4. 
**Jetzt machen Sie das Gleiche umgekehrt: Rectangle von Square ableiten und die Methoden verteilen.**

Square: Square(width) { <br>
    this.width = width; <br>
}

Rectangle: Rectangle(width, heigth) { <br>
    super(width); <br>
    this.heigth = heigth; <br>
}

// This is Mist();


## 5.
**Nehmen Sie an, „String“ wäre in Java nicht final. Die Klasse Filename „extends“ die Klasse String.<br>
Ist das korrekt? <br>
Wie heisst das Prinzip dahinter?**

Ja, weil Baum!

Das einzige, was uns dazu weitergehend einfällt, ist Polymorphismus...aber das macht irgendwie nicht so Sinn?

Weitergehend haben wir überlegt, dass das Prinzip ja Vererbung heißt. Und dass es aber keinen Sinn macht, String zu extenden, weil man String ja einfach so verwenden kann.
