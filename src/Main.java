import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

public class Main {
    public static void main(String[] args) {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(System.in))) {
            while (true) {
                System.out.println("Введите ваш пример или \"exit\", если хотите выйти:");
                String inputLine = reader.readLine();
                if (inputLine.equals("exit")) {
                    System.out.println("До встречи!");
                    break;
                } else {
                    System.out.println(calc(inputLine));
                }
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (IllegalDataException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static String calc(String input) throws IllegalDataException {
        String[] inputDataArray = input.trim().split(" ");
        if (inputDataArray.length != 3) {
            throw new IllegalDataException("Необходимо ввести 2 операнда и один оператор");
        }
        String result = null;

        if (isDigit(inputDataArray[0]) && isDigit(inputDataArray[2])) {
            result = calcForDigit(Integer.parseInt(inputDataArray[0]), inputDataArray[1], Integer.parseInt(inputDataArray[2]));
        } else {
            if (!isDigit(inputDataArray[0]) && !isDigit(inputDataArray[2])) {
                result = calcForRoman(inputDataArray);
            } else {
                throw new IllegalDataException("Разные системы счисления");
            }
        }

        return result;
    }

    private static boolean isDigit(String line) throws IllegalDataException {
        try {
            if (line.contains(".")) {
                throw new IllegalDataException("Калькулятор только для целых чисел");
            }
            Integer.parseInt(line);
            return true;
        } catch (NumberFormatException ex) {
            return false;
        }

    }

    private static String calcForDigit(int first, String operation, int second) throws IllegalDataException {
        if (first > 0 && first < 11 && second > 0 && second < 11) {
            switch (operation) {
                case "+":
                    return String.valueOf((first + second));
                case "-":
                    return String.valueOf((first - second));
                case "/":
                    return String.valueOf((first / second));
                case "*":
                    return String.valueOf((first * second));
                default:
                    throw new IllegalDataException("Неправильная арифметическая операция");
            }
        } else {
            throw new IllegalDataException("Недопустимое число");
        }
    }

    private static String calcForRoman(String[] array) throws IllegalDataException {
        String resultOperation = calcForDigit(parserRomanToDigit(array[0]), array[1], parserRomanToDigit(array[2]));
        int resultInt = Integer.parseInt(resultOperation);
        if (resultInt < 1) {
            throw new IllegalDataException("Римские цифры не имеют цифр меньше 1");
        } else {
            return parserDigitToRoman(resultInt);
        }
    }

    private static int parserRomanToDigit(String input) {
        return input.replace("IX", "VIV").replace("X", "VV").replace("IV", "IIII").replace("V", "IIIII").length();
    }

    private static String parserDigitToRoman(int num) {
        String[] keys = new String[]{"C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] vals = new int[]{100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder ret = new StringBuilder();
        int ind = 0;

        while (ind < keys.length) {
            while (num >= vals[ind]) {
                var d = num / vals[ind];
                num = num % vals[ind];
                for (int i = 0; i < d; i++)
                    ret.append(keys[ind]);
            }
            ind++;
        }

        return ret.toString();
    }


}