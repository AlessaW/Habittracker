public class Customer
{
    private final String forename;
    private final String surname;
    private final String cardID;
    
    public Customer(String forename, String surname, String cardID)
    {
        this.forename = forename;
        this.surname = surname;
        this.cardID = cardID;
    }
    
    public String getForename()
    {
        return forename;
    }
    
    public String getSurname()
    {
        return surname;
    }
    
    public String getCardID()
    {
        return cardID;
    }
    
    public void depositMoney(int amount)
    {
        // does something fancy
    }
    
    public void withdrawMoney(int amount)
    {
        // does something cool
    }
    
    public int getBankBalance()
    {
        // does something easy
        return 0;
    }
    
    public String[] getBankStatement()
    {
        // does something descriptive
        return null;
    }
}
