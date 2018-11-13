import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View extends JFrame implements KeyListener, ActionListener  {

    private JPanel mainPanel;
    private JButton up, down, zero, clear, plus, times, minus, divide, equal, convert;
    private JLabel screen_message;
    private String message = "";
    private Calculator calculator = new Calculator();

    public View() {
        super("Balanced Ternary");
        mainPanel = new JPanel();
        mainPanel.setLayout(new GridLayout(4, 1));
        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setFocusable(true);
        this.setResizable(false);

        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BorderLayout());
        mainPanel.add(answerPanel);
        screen_message = new JLabel(message);
        answerPanel.add(screen_message, BorderLayout.EAST);

        JPanel digitPanel = new JPanel();
        digitPanel.setLayout(new GridLayout(1, 4));
        digitPanel.setBackground(Color.GRAY);
        mainPanel.add(digitPanel);

        JPanel operatorPanel = new JPanel();
        operatorPanel.setLayout(new GridLayout(1, 4));
        operatorPanel.setBackground(Color.GRAY);
        mainPanel.add(operatorPanel);

        JPanel equalPanel = new JPanel();
        equalPanel.setLayout(new GridLayout(2, 1));
        equalPanel.setBackground(Color.GRAY);
        mainPanel.add(equalPanel);


        up = new JButton("^");
        down = new JButton("v");
        zero = new JButton("0");
        clear = new JButton("C");
        digitPanel.add(up);
        digitPanel.add(down);
        digitPanel.add(zero);
        digitPanel.add(clear);

        plus = new JButton("+");
        minus = new JButton("-");
        times = new JButton("x");
        divide = new JButton("/");
        operatorPanel.add(plus);
        operatorPanel.add(minus);
        operatorPanel.add(times);
        operatorPanel.add(divide);

        equal = new JButton("=");
        equalPanel.add(equal);
        convert = new JButton("convert to base 10");
        equalPanel.add(convert);

        addActionListener(this);
    }

    private void up() {
        message = message + "^";
        updateScreen();
    }

    private void down() {
        message = message + "v";
        updateScreen();
    }

    private void zero() {
        message = message + "0";
        updateScreen();
    }

    private void plus() {
        calculator.setFirstTerm(message);
        calculator.setOperator("+");
        message = "";
    }

    private void times() {
        calculator.setFirstTerm(message);
        calculator.setOperator("x");
        message = "";
    }

    private void clear() {
        message = "0";
        calculator.setFirstTerm("");
        calculator.setSecondTerm("");
        calculator.setOperator("");
        updateScreen();
    }

    private void equals() {
        calculator.setSecondTerm(message);
        String operator = calculator.getOperator();
        if (!operator.equals("")) {
            switch (operator) {
                case "+": message = calculator.sum(); break;
                case "x": message = calculator.product(); break;
            }
            updateScreen();
            calculator.setFirstTerm(message);
            calculator.setSecondTerm("");
            calculator.setOperator("");
            message = "";
        }
    }

    private void convert() {
        message = "" + calculator.convertToBaseTen(message);
        updateScreen();
        message = "";
    }

    @Override
    public void keyTyped(KeyEvent evt) {
        int code = evt.getKeyCode();
        if (code == KeyEvent.VK_UP || code == KeyEvent.VK_CIRCUMFLEX) {
            up();
        } else if (code == KeyEvent.VK_DOWN || code == KeyEvent.VK_V) {
            down();
        } else if (code == KeyEvent.VK_0) {
            zero();
        } else if (code == KeyEvent.VK_PLUS) {
            plus();
        } else if (code == KeyEvent.VK_MINUS) {
            calculator.setFirstTerm(message);
            calculator.setOperator("-");
            message = "0";
        } else if (code == KeyEvent.VK_X || code == KeyEvent.VK_ASTERISK) {
            times();
        } else if (code == KeyEvent.VK_SLASH) {
            calculator.setFirstTerm(message);
            calculator.setOperator("/");
            message = "0";
        } else if (code == KeyEvent.VK_C) {
            clear();
        } else if (code == KeyEvent.VK_EQUALS) {
            equals();
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String command = evt.getActionCommand();
        if (command.equals("^")) {
            up();
        } else if (command.equals("v")) {
            down();
        } else if (command.equals("0")) {
            zero();
        } else if (command.equals("+")) {
            plus();
        } else if (command.equals("x")){
            times();
        } else if (command.equals("=")) {
            equals();
        } else if (command.equals("convert to base 10")) {
            convert();
        }
    }

    @Override
    public void keyPressed(KeyEvent evt) { }

    @Override
    public void keyReleased(KeyEvent evt) { }

    @Override
    public void addKeyListener(KeyListener listener) {
        super.addKeyListener(listener);
        mainPanel.addKeyListener(listener);
    }

    public void addActionListener(ActionListener listener) {
        up.addActionListener(listener);
        down.addActionListener(listener);
        zero.addActionListener(listener);
        clear.addActionListener(listener);
        plus.addActionListener(listener);
        times.addActionListener(listener);
        minus.addActionListener(listener);
        divide.addActionListener(listener);
        equal.addActionListener(listener);
        convert.addActionListener(listener);
    }

    public void updateScreen() {
        screen_message.setText(message);
        screen_message.requestFocus();
        screen_message.updateUI();
        mainPanel.updateUI();
        mainPanel.repaint();
        this.requestFocus();
        this.repaint();
    }

    @Override
    public void requestFocus() {
        mainPanel.requestFocus();
    }

}
