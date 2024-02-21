package sem3_generics;

import java.util.Arrays;

/*
Описать собственную коллекцию – список на основе массива.
Коллекция должна иметь возможность хранить любые типы данных,
иметь методы добавления и удаления элементов.
*/

public class MyCollection <T>{
    private Object[] arr;
    private Integer size;
    //нельзя написать = new T[100];, так как Т - пока что непоймичто


    public MyCollection(Integer size) {
        arr = new Object[size];
    }

    public MyCollection() {
        arr = new Object[10];
    }

    public void add(T newObj){
        if (size == arr.length){
            arr = Arrays.copyOf(arr, 2 * arr.length);
        }
        arr[size++] = newObj;
    }

    public T get(int pos){
        if (pos > 0 && pos < size){
            return (T) arr[pos];
        }
        else return null;
    }

    public void delete(int pos){
        for (int i = 0; i < size; i++) {
            arr[i] = arr[i+1];
        }
        size--;
    }

    @Override
    public String toString() {
        StringBuilder strB = new StringBuilder();
        for(int i = 0; i < size; i++){
            strB.append(arr[i].toString());
            strB.append(", ");
        }
        strB.delete(strB.length()-2,strB.length());
        return "[" + strB.toString() + "]";
    }
}
