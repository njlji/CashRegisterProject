// Author: Wreign Eyriel Hizon
// Section: IT1B
// GT CASH REGISTER
package com.mycompany.mavenproject4;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.time.*;
import java.time.format.*;


public class Mavenproject4 {
    
    static Scanner input = new Scanner(System.in);
    
    static String cashiername = "";
    
    static boolean writetofile = false;
    
    public static String dateTime(){
        LocalDateTime datetime = LocalDateTime.now();
        DateTimeFormatter myFormatObj = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");

        String formattedDate = datetime.format(myFormatObj);
        
        return formattedDate;
    }
    
    static void createFile(){
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt",true))){
            
        } catch (IOException e){
            System.out.println("Failed to write to the file.");
            e.printStackTrace();
        }
        
    }
    
    static void showHistory(){
        File carl = new File("transactions.txt"); 
        if(carl.exists()){
        try (BufferedReader reader = new BufferedReader(new FileReader("transactions.txt"))){
            String line;
            while((line = reader.readLine()) != null){
                System.out.println(line);
            }
        }catch (IOException e){
            System.out.println("Failed to read File.");
            e.printStackTrace();
        }
    }else{
            System.out.println("File does not exist.");
}
    }
    
    static void displayCart(ArrayList<String> cartname, ArrayList<Integer> cartprice, ArrayList<Integer> cartquantity){
        System.out.println("-------------------------------");
        System.out.println("\tCart:");
        
        if(writetofile){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt",true))){
            
            writer.write("-------------------------------\n\tCart:");
            writer.newLine();
            
        } catch (IOException e){
            System.out.println("Failed to write to the file.");
            e.printStackTrace();
        }
        }
        
        int carttotal = 0;
        for(int i = 0; i < cartname.size(); i++){
            System.out.print((i+1) + ". " + cartname.get(i) + "\tx");
            System.out.print(cartquantity.get(i) + "\t | P");
            int total = (cartprice.get(i) * cartquantity.get(i));
            String idk = Integer.toString(total);
            System.out.println(total);
            carttotal += total;
            
            if(writetofile){
               try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt",true))){
            
            writer.write((i+1) + ". " + cartname.get(i) + "\tx");
            writer.write(cartquantity.get(i) + "\t | P");
            writer.write(idk);;
            writer.newLine();
            
        } catch (IOException e){
            System.out.println("Failed to write to the file.");
            e.printStackTrace();
        } 
            }
            
        }
        System.out.println("\t\t\t\t\tTOTAL: P" + carttotal);
        System.out.println("-------------------------------");
        
        if(writetofile){
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt",true))){
            
            writer.write("\t\t\t\t\tTOTAL: P" + Integer.toString(carttotal));
            writer.write("\n-------------------------------");
           
            writer.newLine();
            
        } catch (IOException e){
            System.out.println("Failed to write to the file.");
            e.printStackTrace();
        } 
            }
        
    }
    
    static void addItem(ArrayList<String> cartname, ArrayList<Integer> cartprice, ArrayList<Integer> cartquantity, String[] signature, int[] price){
        int user = 0;
        int user1 = 0;
        char running = 'y';
        while (running == 'y'){ 
            System.out.print("Enter the number of your order: ");
            user = input.nextInt();
            user -= 1;
            System.out.print("Quantity: ");
            user1 = input.nextInt();
            
            cartname.add(signature[user]);
            cartprice.add(price[user]);
            cartquantity.add(user1);
            
            displayCart(cartname,cartprice,cartquantity);
            
            System.out.print("Do you want to add another? (y/n)");
            input.nextLine();
            running = input.nextLine().charAt(0);
        }
    }
    
    static void removeItem(ArrayList<String> cartname, ArrayList<Integer> cartprice, ArrayList<Integer> cartquantity){
        displayCart(cartname,cartprice,cartquantity);
        int selection = 0;
        System.out.print("Select a number you want to delete: ");
        selection = input.nextInt();
        
        cartname.remove(selection - 1);
        cartprice.remove(selection - 1);
        cartquantity.remove(selection - 1);
        
        System.out.println("----------------------------");
        displayCart(cartname,cartprice,cartquantity);
        System.out.println("Item Removed.");
    }
    
    static int checkoutCart(ArrayList<Integer> cartprice, ArrayList<Integer> cartquantity, ArrayList<String> cartname){
        int running = 1;
        int carttotal = 0;
        for(int i = 0; i < cartprice.size(); i++){
            int total = (cartprice.get(i) * cartquantity.get(i));
            carttotal += total;
        }
        System.out.println("Your Total is : P" + carttotal);
        System.out.print("Enter Cash Amount: P");
        
        
        
        int cashinput = input.nextInt();
        if(cashinput >= carttotal){
            System.out.println("Your change is : P" + (cashinput - carttotal));
            System.out.println("Thank you for ordering!");
            System.out.print("Checkout time: ");
            System.out.println(dateTime());
            System.out.println("Cashier name: " + cashiername);
            
            running = 1; // originally 0
        }else{
            System.out.println("----------------------------");
            System.out.println("Not Enough Cash!");
            System.out.println("Going back to selection.");
            System.out.println("----------------------------");
        }
        
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("transactions.txt",true))){
            
            writer.write("Your Total is : P" + Integer.toString(carttotal));
            writer.write("\nEnter Cash Amount: P" + Integer.toString(cashinput));
            writer.write("\nYour change is : P" + Integer.toString((cashinput - carttotal)));
            writer.write("\nThank you for ordering! \nCheckout time: ");
            writer.write(dateTime());
            writer.write("\nCashier name: " + cashiername);
        
            writer.newLine();
            
        } catch (IOException e){
            System.out.println("Failed to write to the file.");
            e.printStackTrace();
        }
        
        cartprice.clear();
        cartquantity.clear();
        cartname.clear();
        writetofile = false;
        
        return running;
    }
    
    static void displayMenu(){
        System.out.println("Menu: "); // https://philmenu.com/kfc-menu/
        System.out.println("Signature Meals:\n");
        System.out.println("1. 1pc Chicken with Side, Rice & Drink\t | P110");
        System.out.println("2. 2pc Chicken with Side, Rice & Drink\t | P180");
        System.out.println("3. 1pc Chicken with Rice, & Drink \t | P99");
        System.out.println("4. 2pc Chicken with Rice, & Drink \t | P170");
        System.out.println("5. Flavor Shots with Side, Rice & Drink | P89");
        System.out.println("6. Flavor Shots with Rice & Drink \t | P69");
        System.out.println("7. 1pc Chicken with Rice \t\t | P89");
        System.out.println("8. 2pc Chicken with Rice \t\t | P160");
        System.out.println("9. Flavor Shots with Rice \t\t | P50");
    }
    
    public static void main(String[] args) {
        
        createFile();
        
        boolean loggedin = false;
        
        ArrayList<String> UserName = new ArrayList<String>();
        ArrayList<String> Password = new ArrayList<String>();
        
        boolean loopp = false;
        int selection = 0;
        boolean loginselection = false; 
        while(!loggedin){
            System.out.println("--------------------------------");
            System.out.println("Sign in / Sign up");
            System.out.println("1. Sign in");
            System.out.println("2. Sign up");
            System.out.println("--------------------------------");
            System.out.print("Enter a Number: ");
            
            selection = input.nextInt();
            
            input.nextLine();
            
            String username = "";
            String password = "";
            while(selection > 0){
            switch(selection){
                case 1: // sign in
                    
                    if(UserName.size() > 0){
                    System.out.println("Sign In Page");
                    System.out.print("Enter Username: ");
                    username = input.nextLine().toLowerCase();
                    
                    System.out.print("Enter Password: ");
                    password = input.nextLine();
                    for(int i = 0; i < UserName.size(); i++){
                        if(!UserName.get(i).equals(username)){
                            System.out.print("Username or Password Invalid. Please Try again.");
                        }else{
                            if(Password.get(i).equals(password)){
                                System.out.println("Login Valid! Entering Cash Register");
                                selection = 0;
                                loggedin = true;
                                loopp = true;
                                
                                cashiername = username;
                                
                            }else{
                                System.out.print("Username or Password Invalid. Please Try again.");
                            }
                        }
                    }
                    }else{
                        System.out.println("No Users Registered. Please Sign Up.");
                        selection = 0;
                    }
                    break;
                case 2: // sign up
                    System.out.println("Sign Up Page");
                    
                    boolean usernamee = true;
                    
                    while(usernamee){
                    System.out.print("Enter Username(must be alphanumeric and 5 to 15 characters long): ");
                    username = input.nextLine().toLowerCase();
                    
                    Pattern pattern = Pattern.compile("^[a-zA-Z0-9]{5,15}$");
                    Matcher matcher = pattern.matcher(username);
                    boolean isMatch = matcher.matches();
                    if(isMatch){
                        UserName.add(username);
                        System.out.println("Username Valid");
                        usernamee = false;
                    }else{
                        System.out.println("Username Invalid. Try again.");
                    }
                    }
                    
                    boolean passwordd = true;
                    
                    while(passwordd){
                    System.out.print("Enter Password(must contain at least one uppercase letter, one number, and be 8 to 20 characters long): ");
                    password = input.nextLine();
                    
                    Pattern pattern = Pattern.compile("^(?=.*[A-Za-z])(?=.*\\d)[A-Za-z\\d]{8,20}$");
                    Matcher matcher = pattern.matcher(password);
                    boolean isMatch = matcher.matches();
                    if(isMatch){
                        Password.add(password);
                        System.out.println("Password Valid");
                        passwordd = false;
                    }else{
                        System.out.println("Password Invalid. Password needs to include at least one uppercase letter, one number, and be 8 to 20 characters long Try again.");
                    }
                    }
                    selection = 0;
                    break;
                    
                case 3:
                    System.out.println("TESTING");
                    System.out.println(UserName.get(0));
                    System.out.println(UserName.size());
                    for(int i = 0; i < UserName.size(); i++){
                        System.out.println(i);
                        System.out.println(UserName.get(i));
                    }
                    break;
                case 5:
                    System.out.println("Login Valid! Entering Cash Register");
                    selection = 0;
                    loggedin = true;
                    loopp = true;
                default:
                    selection = 0;
                    
            }
            }
        }
        
        loopp = false;
        while(loopp)
        System.out.println("-----------------------");
        System.out.println("Welcome to KFC!\n");
        displayMenu();
        String[] signature = {"1pc Chicken with Side, Rice & Drink", "2pc Chicken with Side, Rice & Drink", "1pc Chicken with Rice, & Drink", "2pc Chicken with Rice, & Drink", "Flavor Shots with Side, Rice & Drink", "Flavor Shots with Rice & Drink", "1pc Chicken with Rice", "2pc Chicken with Rice", "Flavor Shots with Rice"};
        int[] price = {110,180,99,170,89,69,89,160,50};
       
        ArrayList<String> cartname = new ArrayList<String>();
        ArrayList<Integer> cartprice = new ArrayList<Integer>();
        ArrayList<Integer> cartquantity = new ArrayList<Integer>();
        
        addItem(cartname, cartprice, cartquantity, signature, price);
        
        int running = 1;
        selection = 0;
        while(running == 1){
            System.out.println("What would you want to do next?");
            System.out.println("1. Add more items?");
            System.out.println("2. Remove an item?");
            System.out.println("3. Checkout?");
            System.out.println("4. Show Transaction History?");
            System.out.println("5. Exit");
            System.out.println("-------------------");
            System.out.print("Enter a number: ");
            selection = input.nextInt();
            
            switch(selection){
                case 1:
                    displayMenu();
                    displayCart(cartname,cartprice,cartquantity);
                    addItem(cartname, cartprice, cartquantity, signature, price);
                    break;
                case 2:
                    removeItem(cartname,cartprice,cartquantity);
                    break;
                case 3:
                    writetofile = true;
                    displayCart(cartname,cartprice,cartquantity);
                    running = checkoutCart(cartprice,cartquantity,cartname);
                    break;
                case 4:
                    showHistory();
                    break;
                case 5:
                    running = 0;
                    break;
            }   
        }
    }
}