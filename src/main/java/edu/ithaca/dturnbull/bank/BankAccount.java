package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        if (isEmailValid(email)){
            this.email = email;
            this.balance = startingBalance;
        }
        else {
            throw new IllegalArgumentException("Email address: " + email + " is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }


    public static boolean isEmailValid(String email) {
        if (email.length()==0) { // empty styring
            return false;
        }
        if (email.indexOf("@")== -1) { // no @ symbol
            return false;
        }
        int firstCharInt = email.codePointAt(0);
        int lastCharInt = email.codePointAt(email.length()-1);
        String domain = email.substring(email.indexOf("@")+1, email.length());

        if (email.indexOf('.')-1 <= 0) { // period is first char or doesnt exist
            return false;
        } else if (!domain.contains(".")) { // no period after @ symbol
            return false;
        } else if (email.contains("..")) { // adjacent periods
            return false;
        } else if (email.contains("#")) { // invalid symbol
            return false;
        } else if (firstCharInt < 97 || firstCharInt > 122) { // first character isnt valid
            return false;
        } else if (lastCharInt < 97 || lastCharInt > 122) { // last character isnt valid
            return false;
        } else if (domain.length()-domain.lastIndexOf(".") < 2) { // domain less than 2 characters
            return false;
        } else {
            return true;
        }
    }
}