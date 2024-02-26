package sem3_generics_home;
/*
Напишите обобщенный метод compareArrays(), который принимает два массива и возвращает
- true, если они одинаковые, и
- false в противном случае.
Массивы могут быть любого типа данных, но должны иметь одинаковую длину и содержать элементы одного типа
попарно по индексам. То есть тип элемента в первом массиве под нулевым индексом такой же как тип элемента
во втором массиве под нулевым индексом (и под всеми остальными индексами аналогично)
 */

public class ArraysComparator {

    // внутри методов и классов нельзя создавать массивы из дженериков
    // зато их можно передать извне
    public static <T1, T2> boolean compareArrays(T1[] arr1, T2[] arr2){
        if (arr1.length == arr2.length){
            for (int i = 0; i < arr1.length; i++) {
                // Object имеет метод equals - им и воспользуемся
                if (!(arr1[i].getClass().equals(arr2[i].getClass()) && arr1[i].equals(arr2[i]))) {
                    System.out.printf("[%s]: классы %s <> %s = %s, значения %s <> %s = %s.%n",
                            i,
                            arr1[i].getClass().getSimpleName(),
                            arr2[i].getClass().getSimpleName(),
                            arr1[i].getClass().equals(arr2[i].getClass()),
                            arr1[i],
                            arr2[i],
                            arr1[i].equals(arr2[i]));
                    return false;
                }
            }
            return true;
        }
        else System.out.println("Размерность массивов неодинаковая.");
        return false;
    }


    public static <T> void subscribeArray (T[] arr){
        for (int i = 0; i < arr.length; i++) {
            System.out.printf("Элемент [%s]: класс %-7s, значение %s.%n", i, arr[i].getClass().getSimpleName(), arr[i]);
        }
    }



    public static void main(String[] args) {
        Number[] arr1 = new Number[7];
        arr1[0] = 2_147_483_647;
        arr1[1] = 2.3f;
        arr1[2] = 1_001L;
        arr1[3] = 2.5e2d;
        arr1[4] = (byte) 10;
        arr1[5] = (short) 56;
        arr1[6] = 5.078345f;

        Number[] arr2 = new Number[7];
        arr2[0] = 2_147_483_647;
        arr2[1] = 2.3f;
        arr2[2] = 1_001L;
        arr2[3] = 2.5e2d;
        arr2[4] = (byte) 10;
        arr2[5] = (short) 56;
        arr2[6] = 5.078345f;

        subscribeArray(arr1);
        System.out.println(compareArrays(arr1, arr2) + "\n"); // true


        arr2[6] = 5.078346f;
        System.out.println(compareArrays(arr1, arr2) + "\n"); // false

        arr2[1] = 2.3d;
        System.out.println(compareArrays(arr1, arr2) + "\n"); // false

        arr1[1] = 2.3;
        arr2[6] = 5.078345f;
        System.out.println(compareArrays(arr1, arr2) + "\n"); // true

        arr2[4] = 10;
        System.out.println(compareArrays(arr1, arr2) + "\n"); // false

        Number[] arr3 = new Number[8];
        System.out.println(compareArrays(arr1, arr3) + "\n"); // false

    }
}

/* РЕЗУЛЬТАТ РАБОТЫ МЕТОДА
Элемент [0]: класс Integer, значение 2147483647.
Элемент [1]: класс Float  , значение 2.3.
Элемент [2]: класс Long   , значение 1001.
Элемент [3]: класс Double , значение 250.0.
Элемент [4]: класс Byte   , значение 10.
Элемент [5]: класс Short  , значение 56.
Элемент [6]: класс Float  , значение 5.078345.
true

[6]: классы Float <> Float = true, значения 5.078345 <> 5.078346 = false.
false

[1]: классы Float <> Double = false, значения 2.3 <> 2.3 = false.
false

true

[4]: классы Byte <> Integer = false, значения 10 <> 10 = false.
false

Размерность массивов неодинаковая.
false
 */
