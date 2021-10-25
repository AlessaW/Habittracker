import java.util.ArrayList;
import java.util.List;

public class Bank
{
    private final String name;
    private final String address;
    private final String bankCode;
    private final String countryCode;
    private final List<Customer> Customers = new ArrayList<Customer>();
    
    private int id = 0;
    private final int idLength = 10;
    
    public Bank(String name, String address, String bankCode, String countryCode)
    {
        this.name = name;
        this.address = address;
        this.bankCode = bankCode;
        this.countryCode = countryCode;
    }
    
    public String getName()
    {
        return name;
    }
    
    public String getAddress()
    {
        return address;
    }
    
    public String getBankCode()
    {
        return bankCode;
    }
    
    public void addCustomer(String forename, String surname)
    {
        Customers.add(new Customer(forename, surname, generateCardID()));
    }
    
    private String generateCardID()
    {
        // does something generic
        return countryCode + bankCode + String.format("%0"+idLength+"d",++id);
    }
}
