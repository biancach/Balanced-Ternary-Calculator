import java.util.HashMap;
import java.util.Map;

public class Calculator {

    public boolean isBalancedTernary = true;

    private Map<Character, Integer> charToValMap = new HashMap<Character, Integer>(){
        {
            put('^', 1);
            put('v', -1);
            put('0', 0);
        }
    };


    public String convertToBaseTen(String a) {
//        if (isBalancedTernary) {
//            isBalancedTernary = false;
            int answer = 0;
            int place = 0;
            while (a.length() > 0) {
                int digit = charToValMap.get((a.charAt(a.length() - 1)));
                answer += digit * (int) Math.pow(3, place);
                place += 1;
                a = a.substring(0, a.length() - 1);
            }
            isBalancedTernary = false;
            return String.valueOf(answer);
//        }
//        throw new IllegalAccessError("shouldn't convert base 10 to base 10");
    }

    public String convertToBalancedTernary (String a) {
//        if (!isBalancedTernary) {
            int b10 = Integer.valueOf(a);
            isBalancedTernary = true;
            return recursiveConversion(b10);
//        }
//        throw new IllegalAccessError("shouldn't convert balanced ternary to balanced ternary");
    }

    private String recursiveConversion(int num) {
        int digits = 1;
        int counter = 1;
        int absNum = Math.abs(num);

        while (absNum > counter) {
            counter += Math.pow(3, digits);
            digits += 1;
        }

        String numToReturn = "";

        if (digits == 1) {
            if (num == 0) {
                return "0";
            } else if (num > 0) {
                return "^";
            } else {
                return "v";
            }
        } else {
            for (int i = 1; i < digits; i++) {
                numToReturn += "0";
            }
        }

        int numToSubtract;
        if (num > 0) {
            numToReturn = "^" + numToReturn;
            numToSubtract = (int) Math.pow(3, digits - 1);

        } else {
            numToReturn = "v" + numToReturn;
            numToSubtract = (int) (-1 * Math.pow(3, digits - 1));
        }

        if (digits > 1) {
            return sum(numToReturn, recursiveConversion(num - numToSubtract));
        } else {
            return numToReturn;
        }
    }

    public String sum(String a, String b) {
        if (a.length() > b.length()) {
            return removeLeadingZeros(sum(a, b, "0"));
        } else {
            return removeLeadingZeros(sum(b, a, "0"));
        }
    }

    // a should have more digits
    private String sum(String a, String b, String carry) {
        if (a.length() == 0) {
            return sumOneDigitOneDigit(b, carry);
        } else if (b.length() == 0) {
            return sumOneDigitOneDigit(a, carry);
        }

        String dig_a = a.substring(a.length() - 1);
        String dig_b = b.substring(b.length() - 1);
        String sum_with_carry, new_carry = "0", sum = "";

        String sum_dig = sumOneDigitOneDigit(dig_a, dig_b);
        if (sum_dig.length() == 1) {
            sum_with_carry = sumOneDigitOneDigit(sum_dig, carry);
        } else {
            sum_with_carry = sumTwoDigitsOneDigit(sum_dig, carry);
        }
        if (sum_with_carry.length() == 1) {
            sum = sum_with_carry;
        } else {
            new_carry = sum_with_carry.substring(0, 1);
            sum = sum_with_carry.substring(1, 2);
        }

        if (b.length() == 1) {
            return sum(a.substring(0, a.length() - 1), "0", new_carry) + sum;
        }

        return sum(a.substring(0, a.length() - 1), b.substring(0, b.length() - 1), new_carry) + sum;

    }

    private String sumOneDigitOneDigit(String a, String b){
        if (a.equals("0")) {
            return b;
        } else if (b.equals("0")) {
            return a;
        } else if (a.equals("^")){
            if (b.equals("v")) {
                return "0";
            } else {
                return "^v";
            }
        } else {
            if (b.equals("^")) {
                return "0";
            } else {
                return "v^";
            }
        }
    }

    private String sumTwoDigitsOneDigit(String a, String b) {
        if (a.equals("^^")) {
            if (b.equals("^")) {
                return "^vv";
            } else if (b.equals("v")) {
                return "^0";
            } else {
                return a;
            }
        } else if (a.equals("^0")) {
            if (b.equals("^")) {
                return "^^";
            } else if (b.equals("v")) {
                return "^v";
            } else {
                return a;
            }
        } else if (a.equals("^v")) {
            if (b.equals("^")) {
                return "^0";
            } else if (b.equals("v")) {
                return "^";
            } else {
                return a;
            }
        } else if (a.equals("v^")) {
            if (b.equals("^")) {
                return "v";
            } else if (b.equals("v")) {
                return "v0";
            } else {
                return a;
            }
        } else if (a.equals("v0")) {
            if (b.equals("^")) {
                return "v^";
            } else if (b.equals("v")) {
                return "vv";
            } else {
                return a;
            }
        } else if (a.equals("vv")) {
            if (b.equals("^")) {
                return "v0";
            } else if (b.equals("v")) {
                return "v^^";
            } else {
                return a;
            }
        } else {
            throw new Error("not a two digit input");
        }
    }

    public String difference(String a, String b) {
        return removeLeadingZeros(sum(a, flip(b)));
    }

    public String product(String a, String b) {
        String answer = "0";
        while (a.length() > 0) {
            switch (a.charAt(a.length() - 1)) {
                case '^' : answer = sum(answer, b); break;
                case 'v': answer = sum(answer, flip(b)); break;
            }
            a = a.substring(0, a.length() - 1);
            b = b + "0";
        }
        return answer;
    }

    public String quotient(String a, String b) {
        boolean invert = false;
        if (!isPositive(a)) {
            if (!isPositive(b)) {
                a = flip(a);
                b = flip(b);
            } else {
                invert = true;
                a = flip(a);
            }
        } else if (!isPositive(b)) {
            invert = true;
            b = flip(b);
        }

        String answer = quotientHelper(a, b, "", 0);
        if (answer.length() == 0) {
            answer = "0";
        }

        if (invert) {
            return flip(answer);
        } else {
            return answer;
        }
    }

    private String quotientHelper(String a, String b, String answer, int idx) {
        if (b.length() + idx > a.length()) {
            return answer;
        } else if (a.equals("0")) {
            return answer;
        } else {
            String front = a.substring(0, b.length() + idx);
            if (isGreaterOrEqual(front, b)) {
                a = difference(front, b) + a.substring(b.length());
                answer += "^";
                return quotientHelper(a, b, answer, idx);
            } else if (isGreaterOrEqual(flip(front), b)) {
                a = difference(flip(front), b) + a.substring(b.length());
                answer += "v";
                return quotientHelper(a, b, answer, idx);
            } else {
//                a = a.substring(1);
                answer += "0";
                return quotientHelper(a, b, answer, idx + 1);
            }
        }
    }


    public String flip(String a) {
        String flipped = "";
        for (int i = 0; i < a.length(); i++) {
            switch (a.charAt(i)) {
                case '^' : flipped = flipped + "v"; break;
                case 'v': flipped = flipped + "^"; break;
                default: flipped = flipped + "0";
            }
        }
        return flipped;
    }

    private String removeLeadingZeros(String a) {
        while (a.charAt(0) == '0' && a.length() > 1) {
            a = a.substring(1);
        }
        return a;
    }

    private boolean isPositive(String a) {
        return isGreaterOrEqual(a,"0");
    }

    private boolean isGreaterOrEqual(String a, String b) {
        a = removeLeadingZeros(a);
        b = removeLeadingZeros(b);
        return isGreaterOrEqualHelper(a, b);
    }

    private boolean isGreaterOrEqualHelper(String a, String b) {
        if (a.length() == 0) {
            if (b.length() == 0) {
                return true;
            } else {
                return charToValMap.get(b.charAt(0)) <= 0;
            }
        } else if (b.length() == 0) {
            return charToValMap.get(a.charAt(0)) >= 0;
        }

        int aVal = charToValMap.get(a.charAt(0));
        int bVal = charToValMap.get(b.charAt(0));
        if (aVal == bVal) {
            return isGreaterOrEqual(a.substring(1), b.substring(1));
        } else {
            return aVal > bVal;
        }

    }

}
