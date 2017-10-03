package com.example.franck.homework;

/**
 * Created by Franck on 01.10.2017.
 */

public class Test {
    public static void main(String[] args) {
        int i = 0;

        try {
            for(i = 1; i < 1000; i++) {
                System.out.println(i);
                System.out.println(stringCount(i));
            }
        } catch (ArrayIndexOutOfBoundsException e) {
            System.out.println(i + "ошипка");
            e.printStackTrace();
        }

    }
    public static String stringCount(int second) throws ArrayIndexOutOfBoundsException{
        String[] stringSecond = {"один","два","три","четыре","пять","шесть","семь","восемь",
                "девять","десять","одиннадцать","двенадцать","тринадцать","четырнадцать","пятьнадцать","шестнадцать","семнадцать",
                "восемнадцать","девятнадцать"};
        String[] stringSecondDozen = {"двадцать","тридцать","сорок","пятьдесят","шестьдесят",
                "семьдесят","восемьдесят","девяносто"};
        String[] stringSecondHundred = {"сто","двести","триста","четыресто",
                "пятьсот","шестьсот","семьсот","восемьсот","девятьсот"};
        StringBuilder answer = new StringBuilder();

        int hundred = second / 100;
        int dozen = second - hundred * 100;

        if (hundred != 0) {
            answer.append(stringSecondHundred[hundred - 1] + " ");
        }

        if (dozen <= 19 & dozen > 0) {
            answer.append(stringSecond[dozen - 1]);
        } else if (dozen % 10 == 0 & dozen != 0){
            answer.append(stringSecondDozen[dozen / 10 - 2]);
        } else if (dozen != 0) {
            answer.append(stringSecondDozen[dozen / 10 - 2] + " " + stringSecond[dozen % 10 - 1]);
        }

        return new String(answer);
    }
}
