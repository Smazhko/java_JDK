package sem3_generics_home;
/*
Написать класс Калькулятор (необобщенный), который содержит обобщенные статические методы:
sum(), multiply(), divide(), subtract(). Параметры этих методов – два числа разного типа,
над которыми должна быть произведена операция.
 */

/*
РАБОТА С BigDecimal - самым точным типом
https://javarush.com/groups/posts/2274-kak-ispoljhzovatjh-bigdecimal-v-java
https://devmark.ru/article/java-bigdecimal-tips
*/

import java.math.BigDecimal;
import java.math.RoundingMode;

public class Calculator {

    // приводим любой Number к типу BigDecimal
    public static <N1 extends Number, N2 extends Number> BigDecimal[] adapt(N1 num1, N2 num2){
        return new BigDecimal[]{new BigDecimal(String.valueOf(num1)),
                new BigDecimal(String.valueOf(num2))};
    }


    // СЛОЖЕНИЕ
    public static <N1 extends Number, N2 extends Number> BigDecimal sum(N1 num1, N2 num2){
        BigDecimal[] adapted = adapt(num1,num2);
        return adapted[0].add(adapted[1]);
    }

    // ВЫЧИТАНИЕ
    public static <N1 extends Number, N2 extends Number> BigDecimal subtract(N1 num1, N2 num2){
        BigDecimal[] adapted = adapt(num1,num2);
        return adapted[0].subtract(adapted[1]);
    }

    // УМНОЖЕНИЕ
    public static <N1 extends Number, N2 extends Number> BigDecimal multiply(N1 num1, N2 num2){
        BigDecimal[] adapted = adapt(num1,num2);
        return round(adapted[0].multiply(adapted[1]));
    }

    // ДЕЛЕНИЕ
    public static <N1 extends Number, N2 extends Number> BigDecimal divide(N1 num1, N2 num2, int maxPrecision){
        BigDecimal[] arr = adapt(num1,num2);
        BigDecimal number1 = arr[0];
        BigDecimal number2 = arr[1];

        // делим числа друг на друга с использованием заказанной точности maxPrecision
        // и затем отбрасываем лишние нули, если такие есть в дробной части
        return round(number1.divide(number2, maxPrecision, RoundingMode.HALF_DOWN));
    }


    // корректируем количество знаков после запятой на случай,
    // если число не имеет бесконечную дробную часть
    public static BigDecimal round (BigDecimal num) {
        int precision = num.scale();  // узнаём точность числа - сколько знаков после точки
        if (precision == 0) {
            return num;
        }
        else {
            BigDecimal integerPart = new BigDecimal(num.toBigInteger()); // выделяем целую часть
            BigDecimal fractionalPart = num.subtract(integerPart);       // выделяем дробную часть
            // На случай, когда scale() показало много знаков после запятой,
            // но они всё равно все оказались нулями - проверка.
            // Сравнение BigDecimal друг с другом происходит через метод compareTo,
            // который, как обычно, возвращает -1 0 1.
            // Ноль в BigDecimal - особая переменная BigDecimal.ZERO
            if (fractionalPart.compareTo(BigDecimal.ZERO) == 0){
                precision = 0;
            }
            else {
                String temp = fractionalPart.toString();  // перевод дробной части в строку (нет ограничения по длине)
                // проходим от конца строки до тех пор, пока не встретим неНоль
                while (temp.charAt(precision + 1) == '0') { // precision + 1, т.к. в строке temp в начале есть "0."
                    // корректируем точность
                    precision -= 1;
                }
            }
            // округляем результат до вычисленной точности
            return num.setScale(precision, RoundingMode.HALF_DOWN);
        }
    }


    public static void main(String[] args) {
        int num1 = 10;
        int num2 = 32;

        Float num3 = 9.20000001f; // float обеспечивает 7 десятичных знаков точности
        Float num4 = 20.00002f;

        Double num5 = 1.25e-2;
        Double num6 = 3.141592654;

        Long num7 = 12_345_678_901L;
        Long num8 = 1_000L;

        System.out.printf("Cумма чисел %s + %s = %s%n",         num1 , num6, sum(num1, num6));
        System.out.printf("Cумма чисел %s + %s = %s%n",         num3 , num8, sum(num3, num8));
        System.out.printf("Разница чисел %s - %s = %s%n",       num2 , num4, subtract(num2, num4));
        System.out.printf("Произведение чисел %s * %s = %s%n",  num3 , num5, multiply(num3, num5));
        System.out.printf("Частное чисел %s / %s = %s%n",       num7 , num6, divide(num7, num6, 12));
        System.out.printf("Частное чисел %s / %s = %s%n",       num3 , num5, divide(num3, num5, 10));
        System.out.printf("Частное чисел %s / %s = %s%n",       num7 , num2, divide(num7, num2, 15));
    }
}


/*
ВЫВОД ПРОГРАММЫ

Cумма чисел 10 + 3.141592654 = 13.141592654
Cумма чисел 9.2 + 1000 = 1009.2
Разница чисел 32 - 20.00002 = 11.99998
Произведение чисел 9.2 * 0.0125 = 0.115
Частное чисел 12345678901 / 3.141592654 = 3929751645.32581696061
Частное чисел 9.2 / 0.0125 = 736
Частное чисел 12345678901 / 32 = 385802465.65625

 */