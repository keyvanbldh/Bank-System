import java.io.IOException;

public class Main {
    public static void main(String[] args) {
        try {
            BankSystem bankSystem = new BankSystem();
            bankSystem.addCustomer("Ali Rezaei", 5000);
            bankSystem.addCustomer("Sara Ahmadi", 7500);
            bankSystem.addCustomer("Reza Mohammadi", 3000);
            bankSystem.addCustomer("Maryam Karimi", 6200);
            bankSystem.deposit("Ali Rezaei", 2000);
            bankSystem.withdraw("Sara Ahmadi", 1500);
            bankSystem.transfer("Maryam Karimi", "Reza Mohammadi", 1000);
            bankSystem.sortCustomerByBalance();
            System.out.println("*** after sorting ***");
            bankSystem.showAllCustomers();
            System.out.println("***************");
            bankSystem.reportHighestBalance();
            bankSystem.saveData("bankData.txt");
            bankSystem.loadData("bankData.txt");

        } catch (IOException e) {
            System.out.println(e.getMessage());
        } catch (InsufficientBalanceException e) {
            System.out.println(e.getMessage());
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        }
    }
}
