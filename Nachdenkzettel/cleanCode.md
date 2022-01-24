Nachdenkzettel Clean Code

                                                                          
##1.   Klassenexplosion (Schwierig..)

    class Formularfeld;
    class Textfeld extends Formularfeld;
    class Zahlfeld extends Formularfeld;
    class TextUndZahlFeld extends Formularfeld;
    class TextfeldOCR extends Textfeld;
    class ZahlfeldOCR extends Zahlfeld;
    class TextUndZahlFeldOCR extends TextUndZahlFeld;
    class TextfeldSonderZ extends TextUndZahlFeld;
    class TextfeldOCRSonderZ extends TextUndZahlFeldOCR;
    class ..... 

> Jede weitere Eigenschaft oder Spezialisierung führt zu vielen neuen Klassen durch Kombination. Die Folge ist explosives Anwachsen der Zahl der Klassen mit identischem Code. (Lösung?)
                 
Eine Möglichkeit wäre Formularfeld als Interface zu machen mit abstrakten Klassen, die in den jeweiligen Klassen so definiert werden, wie dort nötig.

##2. Der verwirrte und der nicht-verwirrte Indexer
**Was genau unterscheidet die beiden Indexer? Wieso ist der eine „verwirrt“?**

Der verwirrte macht seine Attribute nicht final. Und hat setter Methoden. <br>
Mit anderen Worten: Er versteht die Vorteile von immutable nicht.


##3. Korrekte Initialisierung und Updates von Objekten

public class Address {
                                                                       
	private String City;
	private String Zipcode;
	private String Streetname;
	private String Number;

	public void setCity (String c) {
		City = c;
	}
	public void setZipcode (String z) {
		Zipcode = z;
	}
}
Wie initialisieren Sie Address richtig? Wie machen Sie einen korrekten Update der Werte?

     public Address(String city, String zipcode, String streetname, String number){
            this.City = city;
            this.Zipcode = zipcode;
            this.Streetname = streetname;
            this.Number = number;
    }
 keine Setter!!!

##4. Kapselung und Seiteneffekte

  public class Person {

	public Wallet wallet = new Wallet();
	int balance = 0;

	public Wallet getWallet(void) {
		return wallet;
	}
	
	public addMoney(int money) {
	 	wallet.add(money);
		balance = wallet.size();

	public int getBalance() {
	 	return balance;
            }
}
                                                                                   
Reparieren Sie die Klasse und sorgen Sie dafür, dass die Gültigkeit der Objekte erhalten bleibt und keine Seiteneffekte auftreten.

public class Person {

	private Wallet wallet = new Wallet();
	private int balance = 0;

	public Wallet getWallet(void) {
		return new Wallet(wallet);  //a copy of the wallet
	}
	
	public addMoney(int money) {
	 	wallet.add(money);
		balance = wallet.size();

	public int getBalance() {
	 	return balance;
            }
}

                                                    
