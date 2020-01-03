
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
//        String num1 = "^^v^0000", num2 = "0v0vv";
        String num1 = "v^00^", num2 = "vv^^v0v0vv";

        String num1b10 = cleanConvertToBaseTen(num1);
        String num2b10 = cleanConvertToBaseTen(num2);

        String newNum1 = cleanConvertToBalancedTernary(num1b10);
        String newNum2 = cleanConvertToBalancedTernary(num2b10);

//        System.out.println(test.flip(num1));
//        System.out.println(test.sum(test.flip(num2), num2));
//        System.out.println(test.sum(num1, num2));
//        System.out.println(test.convertToBaseTen(test.sum(num1, num2)));
//        System.out.println(test.product(num1, num2));
//        System.out.println(test.convertToBaseTen(test.product(num1, num2)));


        View view = new View();
        view.setSize(400, 600);
        view.setLocation(100, 100);
        view.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        view.setVisible(true);
    }

    public static String cleanConvertToBaseTen(String b3num) {
        Calculator test = new Calculator();
        String converted = test.convertToBaseTen(b3num);
        System.out.printf("Balanced ternary number: %s\n", b3num);
        System.out.printf("Base ten number: %s\n\n", converted);
        return converted;
    }

    public static String cleanConvertToBalancedTernary(String b10num) {
        Calculator test = new Calculator();
        String converted = test.convertToBalancedTernary(b10num);
        System.out.printf("Base ten number: %s\n", b10num);
        System.out.printf("Balanced ternary number: %s\n\n", converted);
        return converted;
    }

}
