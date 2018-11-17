
import javax.swing.JFrame;

public class Main {

    public static void main(String[] args) {
        Calculator test = new Calculator();
//        String num1 = "^^v^0000", num2 = "0v0vv";
        String num1 = "v^00^", num2 = "vv^^v0v0vv";

        String num1b10 = test.convertToBaseTen(num1);
        String num2b10 = test.convertToBaseTen(num2);

        System.out.println(test.convertToBaseTen(num1));
        System.out.println(test.convertToBaseTen(num2));

        System.out.println(test.convertToBalancedTernary(num1b10));
        System.out.println(test.convertToBalancedTernary(num2b10));

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

}
