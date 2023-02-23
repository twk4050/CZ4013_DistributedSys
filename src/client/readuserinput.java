package client;

import java.util.Scanner;

public class readuserinput {
    static Scanner input = new Scanner(System.in);

    public readuserinput(){}

    public static int inputInt(){
        try{
            int number = Integer.parseInt(input.nextLine().trim());
            System.out.println(number+" was entered.");
            return number;
        }
        catch(Exception e){
            System.out.println("Invalid Integer was entered.Please Try again");
            return inputInt();
        }
    }
    public static String inputStr(){
        try{
            String line = input.nextLine().toLowerCase().trim();
            while(line == ""){
                System.out.println("Nothing was entered.");
                line = input.nextLine().toLowerCase().trim();
            }
            System.out.println(line+" was entered.");
            return line;
        }
        catch(Exception e){
            System.out.println("Invalid String was entered.Please Try again");
            return inputStr();
        }
    }
    public static float inputFloat(){
        try{
            float number = input.nextFloat();
            System.out.println(number+" was entered.");
            return number;
        }
        catch(Exception e){
            System.out.println("Invalid float number was entered.Please Try again");
            return inputFloat();
        }
    }
    public static double inputDouble(){
        try{
            double number = input.nextDouble();
            System.out.println(number+" was entered.");
            return number;
        }
        catch(Exception e){
            System.out.println("Invalid double number was entered.Please Try again");
            return inputFloat();
        }
    }

    public static void closeInputScanner(){
        input.close();
    }
}
