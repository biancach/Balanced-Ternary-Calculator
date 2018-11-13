public class Calculator {

    private String operator = "";
    private String firstTerm = "";
    private String secondTerm = "";

    public int convertToBaseTen(String a) {
        int answer = 0;
        int place = 0;
        while (a.length() > 0) {
            int dig;
            switch (a.charAt(a.length() - 1)) {
                case '^' : dig = 1; break;
                case 'v': dig = -1; break;
                default: dig = 0;
            }
            answer += dig * (int) Math.pow(3, place);
            place += 1;
            a = a.substring(0, a.length() - 1);
        }
        return answer;
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

    public void setOperator(String operator) {
        this.operator = operator;
    }

    public void setFirstTerm(String firstTerm) {
        this.firstTerm = firstTerm;
    }

    public void setSecondTerm(String secondTerm) {
        this.secondTerm = secondTerm;
    }

    public String getOperator() {
        return operator;
    }

    public String getFirstTerm() {
        return firstTerm;
    }

    public String getSecondTerm() {
        return secondTerm;
    }

    public String sum() {
        return sum(firstTerm, secondTerm);
    }

    public String product() {
        return product(firstTerm, secondTerm);
    }
}
