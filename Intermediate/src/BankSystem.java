import java.io.BufferedReader;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class BankSystem {
    ArrayList<String> customers = new ArrayList<>();
    HashMap<String, Integer> balances = new HashMap<>();

    public void addCustomer(String name, int initialBalance) {

        if (initialBalance < 0) {
            throw new IllegalArgumentException(
                    "Initial balance cannot be negative!"
            );
        }

        for (String customer : customers) {
            if (name.equalsIgnoreCase(customer)) {
                throw new IllegalArgumentException(
                        "Customer already exists!"
                );
            }
        }

        customers.add(name);
        balances.put(name, initialBalance);
    }

    public void deposit(String name, int amount) {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive!");
        }
        for (String s : balances.keySet()) {
            if (name.equalsIgnoreCase(s)) {
                balances.put(s, balances.get(s) + amount);
                return;
            }
        }
        throw new IllegalArgumentException(
                "Customer does not exist!");

    }

    public void withdraw(String name, int amount) throws InsufficientBalanceException {
        if (amount <= 0) {
            throw new IllegalArgumentException("Amount must be positive!");
        }
        for (String s : balances.keySet()) {
            if (name.equalsIgnoreCase(s)) {
                int balance = balances.get(s);
                if (balance < amount) {
                    throw new InsufficientBalanceException("Insufficient balance!");
                }
                balances.put(s, balances.get(s) - amount);
                return;
            }

        }
        throw new IllegalArgumentException("Customer does not exist!");
    }

    public void transfer(String from, String to, int amount) throws InsufficientBalanceException {
        boolean fromCustomer = false;
        boolean toCustomer = false;
        for (String a : customers) {
            if (from.equalsIgnoreCase(a)) {
                fromCustomer = true;
                break;
            }
        }
        for (String s : customers) {
            if (to.equalsIgnoreCase(s)) {
                toCustomer = true;
                break;
            }
        }

        if (!fromCustomer || !toCustomer) {
            throw new IllegalArgumentException("Customer does not exist!");
        }

        withdraw(from, amount);
        deposit(to, amount);
    }

    public void sortCustomerByBalance() {
        for (int i = 0; i < customers.size(); i++) {
            for (int j = i + 1; j < customers.size(); j++) {
                if (balances.get(customers.get(i)) < balances.get(customers.get(j))) {
                    String temp = customers.get(i);
                    customers.set(i, customers.get(j));
                    customers.set(j, temp);
                }
            }
        }
    }

    public void showAllCustomers() {
        for (String s : customers) {
            System.out.println("Name: " + s + "| Balance: " + balances.get(s));

        }
    }

    public void reportHighestBalance() {
        if (customers.size() == 0) {
            System.out.println("No customers found!");
        } else {

            sortCustomerByBalance();
            System.out.println(customers.get(0) + ": " + balances.get(customers.get(0)));
        }
    }

    public void saveData(String fileName) throws IOException {
        try (FileWriter fileWriter = new FileWriter(fileName)) {
            for (String s : balances.keySet()) {
                fileWriter.write(s + "," + balances.get(s) + "\n");
            }
        }
    }

    public void loadData(String fileName) throws IOException {
        ArrayList<String> newCustomers = new ArrayList<>();
        HashMap<String, Integer> newBalances = new HashMap<>();

        try (BufferedReader bufferedReader =
                     new BufferedReader(new FileReader(fileName))) {

            String line;

            while ((line = bufferedReader.readLine()) != null) {
                String[] data = line.split(",");

                if (data.length != 2) {
                    throw new IOException("Invalid file format!");
                }

                newCustomers.add(data[0]);
                newBalances.put(data[0], Integer.parseInt(data[1]));
            }
        }

        customers = newCustomers;
        balances = newBalances;
    }
}