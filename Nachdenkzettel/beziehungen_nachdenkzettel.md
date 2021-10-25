# Nachdenkzettel Beziehungen/Vererbung
## 1. „Class B extends X“. Jetzt fügen Sie eine neue Methode in X ein. Müssen Sie B anpassen?
Wenn es eine abstrakte Methode ist, muss diese in B noch definiert werden. Ansonsten muss B nicht verändert werden.

## 2.1 
Class B extends X  {
   public void newMethodinB()  {  .... }
}
Jetzt fügen Sie eine neue public Methode in ihre abgeleitete Klasse ein. Sie möchten diese neue
Methode im Code verwenden. Prüfen Sie die folgenden Codezeilen:
    X x = new B();
    x.newMethodinB();
Was stellen Sie fest?

Das Objekt ist vom Typ X und verfügt deswegen über die Methode in B nicht. Entweder muss man die Methode in X abstrakt anlegen oder konkret und dann überschreiben.



## 2.2
Class B extends X  {
    @override           
    public void methodinB()  {  .... }
}
Jetzt überschreiben Sie eine Methode der Basisklasse in ihrer abgeleitete Klasse. Sie möchten diese
neue Methode im Code verwenden. Prüfen Sie die folgenden Codezeilen:
    X x = new B();
    x.methodinB();
Was stellen Sie fest?

Klappt.

## 3. Versuchen Sie „Square“ von Rectangle abzuleiten (geben Sie an welche Methoden Sie in die Basisklasse tun und welche Sie in die abgeleitete Klasse tun)
Rectangle: Rectangle(width, heigth) {
    this.width = width;
    this.heigth = heigth;
}

Square: Square(width) {
    super(width,width);
}
// This is Super();


## 4. Jetzt machen Sie das Gleiche umgekehrt: Rectangle von Square ableiten und die Methoden verteilen.
Square: Square(width) {
    this.width = width;
}

Rectangle: Rectangle(width, heigth) {
    super(width);
    this.heigth = heigth;
}
// This is Mist();


## 5. Nehmen Sie an, „String“ wäre in Java nicht final. Die Klasse Filename „extends“ die Klasse String. Ist das korrekt? Wie heisst das Prinzip dahinter?
Ja, weil Baum!

Das einzige, was uns dazu weitergehend einfällt, ist Polymorphismus...aber das macht irgendwie nicht so Sinn?