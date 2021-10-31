package com.java.main;

public class OverrideMain {
    public static int main1(String[] args) {
        System.out.println("Int main");
        return 0;
    }

    public static void main(String[] args) {
        System.out.println("In OverrideMain");
    }

    public static void main(String[] args1, String args2) {//over loading possible
        System.out.println("In OverrideMain overload");
    }
}

class Child extends OverrideMain{
    public static void main(String[] args) {//overridinf is not there. This is method hiding. Now,if we run Child,
        // it prints "In main of child". If runs, OverrideMain, it prints "In OverrideMain"
        System.out.println("In main of child");
    }
}
