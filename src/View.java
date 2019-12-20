import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class View extends JFrame implements KeyListener, ActionListener  {

    private JPanel mainPanel, ternaryPanel, baseTenPanel;
    private JButton up, down, zero, clear, plus, times, minus, divide, equal, convertToBaseTen, convertToTernary;
    private JLabel screen_message;
    private String message = "", ternary = "";
    private Calculator calculator = new Calculator();
    private String operator = "";
    private String firstTerm = "";
    private String secondTerm = "";

    public View() {
        super("Balanced Ternary");
        mainPanel = new JPanel();
        mainPanel.setLayout(new CardLayout());

        ternaryPanel = new JPanel();
        ternaryPanel.setLayout(new GridLayout(5, 1));
        mainPanel.add(ternaryPanel);
        baseTenPanel = new JPanel();
        baseTenPanel.setLayout(new GridLayout(4, 1));
        mainPanel.add(baseTenPanel);

        this.setContentPane(mainPanel);
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setFocusable(true);
        this.setResizable(false);

        JPanel answerPanel = new JPanel();
        answerPanel.setLayout(new BorderLayout());
        ternaryPanel.add(answerPanel);
        screen_message = new JLabel(message);
        answerPanel.add(screen_message, BorderLayout.EAST);

        JPanel digitPanel = new JPanel();
        digitPanel.setLayout(new GridLayout(1, 4));
        digitPanel.setBackground(Color.GRAY);
        ternaryPanel.add(digitPanel);

        JPanel operatorPanel = new JPanel();
        operatorPanel.setLayout(new GridLayout(1, 4));
        operatorPanel.setBackground(Color.GRAY);
        ternaryPanel.add(operatorPanel);

        JPanel equalPanel = new JPanel();
        equalPanel.setLayout(new GridLayout(1, 1));
        equalPanel.setBackground(Color.GRAY);
        ternaryPanel.add(equalPanel);

        JPanel convertPanel = new JPanel();
        convertPanel.setLayout(new GridLayout(1, 2));
        convertPanel.setBackground(Color.GRAY);
        ternaryPanel.add(convertPanel);

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
        convertToBaseTen = new JButton("convert to base 10");
        convertPanel.add(convertToBaseTen);
        convertToTernary = new JButton("convert to ternary");
        convertPanel.add(convertToTernary);

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
        firstTerm = message;
        operator = "+";
        message = "";
    }

    private void minus() {
        firstTerm = message;
        operator = "-";
        message = "";
    }

    private void times() {
        firstTerm = message;
        operator = "x";
        message = "";
    }

    private void divide() {
        firstTerm = message;
        operator = "/";
        message = "";

    }
    private void clear() {
        message = "0";
        firstTerm = "";
        secondTerm = "";
        operator = "";
        updateScreen();
    }

    private void equals() {
        secondTerm = message;
        if (!operator.equals("")) {
            switch (operator) {
                case "+": message = calculator.sum(firstTerm, secondTerm); break;
                case "-": message = calculator.difference(firstTerm, secondTerm); break;
                case "x": message = calculator.product(firstTerm, secondTerm); break;
            }
            updateScreen();
            firstTerm = message;
            secondTerm = "";
            message = "";
        }
    }

    private void convertToBaseTen() {
        message = "" + calculator.convertToBaseTen(message);
        updateScreen();
    }

    private void convertToBalancedTernary() {
        message = "" + calculator.convertToBalancedTernary(message);
        updateScreen();
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
            minus();
        } else if (code == KeyEvent.VK_X || code == KeyEvent.VK_ASTERISK) {
            times();
        } else if (code == KeyEvent.VK_SLASH) {
//            divide(); // need to implement
        } else if (code == KeyEvent.VK_C) {
            clear();
        } else if (code == KeyEvent.VK_EQUALS) {
            equals();
        }
    }

    @Override
    public void actionPerformed(ActionEvent evt) {
        String command = evt.getActionCommand();
        if (command.equals("C")) {
            clear();
        } else if (command.equals("^")) {
            up();
        } else if (command.equals("v")) {
            down();
        } else if (command.equals("0")) {
            zero();
        } else if (command.equals("+")) {
            plus();
        } else if (command.equals("-")) {
            minus();
        } else if (command.equals("x")){
            times();
        } else if (command.equals("/")){
//            divide();
        }else if (command.equals("=")) {
            equals();
        } else if (command.equals("convert to base 10")) {
            if (calculator.isBalancedTernary) {
                convertToBaseTen();
            } else {
                convertToBalancedTernary();
            }

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
        convertToBaseTen.addActionListener(listener);
        convertToTernary.addActionListener(listener);
    }

    public void updateScreen() {
        screen_message.setText(message);
        screen_message.requestFocus();
        screen_message.updateUI();
        mainPanel.updateUI();
        ternaryPanel.updateUI();
        baseTenPanel.updateUI();
        mainPanel.repaint();
        ternaryPanel.repaint();
        baseTenPanel.repaint();
        this.requestFocus();
        this.repaint();
    }

    @Override
    public void requestFocus() {
        mainPanel.requestFocus();
    }

}
