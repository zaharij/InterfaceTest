
package com.company;

import java.lang.annotation.*;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class Main {

    public static void main(String[] args) {
        TestInvoked testInvoked = new TestInvoked();
        TestNotInvoked testNotInvoked = new TestNotInvoked();
        Analizator.analizator(testInvoked, testNotInvoked);
    }
}

@InvokeClass
class TestInvoked{

    @InvokeMethod
    void invoked(){
        System.out.println("invoked method");
    }

    void notInvoked(){
        System.out.println("not invoked method");
    }
}

class TestNotInvoked{

    void invoked(){
        System.out.println("invoked method");
    }

    void notInvoked(){
        System.out.println("not invoked method");
    }
}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Inherited
@interface InvokeClass{}

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@interface InvokeMethod{}

class Analizator{
    static void analizator(Object... object){
        for (Object obj: object){
            Class clas = obj.getClass();
            if (clas.isAnnotationPresent(InvokeClass.class)){
                Method[] methods = clas.getDeclaredMethods();
                for (Method method: methods){
                    if (method.isAnnotationPresent(InvokeMethod.class)){
                        try {
                            method.invoke(obj);
                        } catch (IllegalAccessException e) {
                            e.printStackTrace();
                        } catch (InvocationTargetException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }
    }
}

