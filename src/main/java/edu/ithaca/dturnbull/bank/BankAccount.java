package edu.ithaca.dturnbull.bank;

public class BankAccount {

    private String email;
    private double balance;

    /**
     * @throws IllegalArgumentException if email is invalid
     */
    public BankAccount(String email, double startingBalance){
        boolean emailValid = false;
        boolean balanceValid = false;

        if (isEmailValid(email)){
            this.email = email;
            emailValid = true;
        }
        if (isAmountValid(startingBalance)) {
            this.balance = startingBalance;
            balanceValid = true;
        }
        if (!emailValid || !balanceValid) {
            throw new IllegalArgumentException("Email address and/or balance is invalid, cannot create account");
        }
    }

    public double getBalance(){
        return balance;
    }

    public String getEmail(){
        return email;
    }

    /**
     * @post increases the balance by amount if amount is valid
     * <li>If amount is not valid, throw IllegalArgumentException.</li>
     */
    public void deposit (double amount) throws IllegalArgumentException{
        if (isAmountValid(amount)) {
            this.balance += amount;
        } else {
            throw new IllegalArgumentException("Deposit amount is invalid.");
        }
    }

    /**
     * @post decreases amount from balance and deposits to another account's balance
     * <li>If amount is not valid, throw IllegalArgumentException.</li>
     * <li>If transferee is not valid, throw IllegalArgumentException.</li>
     */
    public void transfer (double amount, BankAccount transferAccount) throws IllegalArgumentException, InsufficientFundsException{
        if (transferAccount==null) {
            throw new IllegalArgumentException("Transfer account is invalid.");
        } else {
            this.withdraw(amount);
            transferAccount.deposit(amount);
        }
    }

    /**
     * @post reduces the balance by amount if amount is non-negative and smaller than balance
     */
    public void withdraw (double amount) throws InsufficientFundsException{
        if(amount < 0){
            throw new IllegalArgumentException("Can not withdraw negative moeny");
        }
        else if (amount <= balance){
            balance -= amount;
        }
        else {
            throw new InsufficientFundsException("Not enough money");
        }
    }

    /**
     * <li>If amount is negative, return false.</li>
     * <li>If amount is has more than two decimal points, return false.</li>
     */
    public static boolean isAmountValid(double amount) {
        if (amount < 0) {
            return false;
        } 
        String amountStr = Double.toString(Math.abs(amount));
        int integerPlaces = amountStr.indexOf('.');
        if (((amountStr.length() - integerPlaces - 1) > 2))  {
            return false;
        }
        return true;
    }


    public static boolean isEmailValid(String email) {
        if (email.length()==0) { // empty string
            return false;
        }
        if (email.indexOf("@")== -1) { // no @ symbol
            return false;
        }
        if (email.contains("@@")) { // 2 @ symbol
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
        } else if (domain.length()-domain.lastIndexOf(".") <= 2) { // domain less than 2 characters
            return false;
        } else {
            return true;
        }
    }
}