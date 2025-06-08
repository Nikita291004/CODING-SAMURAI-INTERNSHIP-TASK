import java.util.*;
public class calculator{
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        double num1,num2,result;
        char operator;
        System.out.println("Simple Calculator");

        System.out.println("********************");

        System.out.println("Enter your first number");
        num1 = scanner.nextDouble();
        

        System.out.println("Enter an operator (+,-,*,/)");
        operator = scanner.next().charAt(0);

        System.out.println("Enter second number");
        num2 = scanner.nextDouble();

        switch(operator){
            case '+':
              result = num1 +num2;
              System.out.println("result is " + result);
              break;
            case '-':
                result = num1 -num2;
                System.out.println("Result is "+ result);
                break;
            case '*':
              result = num1 *num2;
              System.out.println("Result is" + result);
              break;
            case '/':
                if(num2 == 0){
                    System.out.println("cannot divide by zero");

                }else{
                    result = num1/ num2;
                    System.out.println("Result is" + result);
                }
                break;
            default:
                System.out.println("Invalid operator");


        }
        scanner.close();
    }   
}