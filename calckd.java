import java.util.Scanner;
import java.util.HashMap;

class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        
        while (true) {
            System.out.print("Введите выражение (или 'exit' для выхода): ");
            String input = scanner.nextLine();
            
            if (input.equalsIgnoreCase("exit")) {
                System.out.println("Выход из калькулятора.");
                break;
            }
            
            try {
                String result = calc(input);
                System.out.println("Результат: " + result);
            } catch (IllegalArgumentException e) {
                System.out.println("Ошибка: " + e.getMessage());
            }
        }
        
        scanner.close();
    }
    
    static String calc(String input) {
        String[] tokens = input.split(" ");
        if (tokens.length != 3) {
            throw new IllegalArgumentException("Неправильный формат выражения.");
        }
        
        int operand1, operand2;
        try {
            operand1 = parseOperand(tokens[0]);
            operand2 = parseOperand(tokens[2]);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException("Ошибка при парсинге операндов: " + e.getMessage());
        }
        
        if (operand1 < 1 || operand1 > 10 || operand2 < 1 || operand2 > 10) {
            throw new IllegalArgumentException("Числа должны быть от 1 до 10.");
        }
        
        int result;
        switch (tokens[1]) {
            case "+":
                result = operand1 + operand2;
                break;
            case "-":
                result = operand1 - operand2;
                break;
            case "*":
                result = operand1 * operand2;
                break;
            case "/":
                if (operand2 == 0) {
                    throw new IllegalArgumentException("Деление на ноль недопустимо.");
                }
                result = operand1 / operand2;
                break;
            default:
                throw new IllegalArgumentException("Неверная операция.");
        }
        
        if (input.matches(".*[IVX].*")) {
            if (result <= 0) {
                throw new IllegalArgumentException("Римские числа не могут быть отрицательными или нулевыми.");
            }
            return toRoman(result);
        } else {
            return Integer.toString(result);
        }
    }
    
    static int parseOperand(String operandStr) {
        try {
            return Integer.parseInt(operandStr);
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Неверный формат числа.");
        }
    }
    
    static String toRoman(int num) {
        if (num < 1 || num > 3999) {
            throw new IllegalArgumentException("Недопустимое число для представления в римской системе.");
        }

        String[] romanSymbols = {"M", "CM", "D", "CD", "C", "XC", "L", "XL", "X", "IX", "V", "IV", "I"};
        int[] values = {1000, 900, 500, 400, 100, 90, 50, 40, 10, 9, 5, 4, 1};

        StringBuilder romanNumeral = new StringBuilder();

        int i = 0;
        while (num > 0) {
            while (num >= values[i]) {
                num -= values[i];
                romanNumeral.append(romanSymbols[i]);
            }
            i++;
        }

        return romanNumeral.toString();
    }
}
