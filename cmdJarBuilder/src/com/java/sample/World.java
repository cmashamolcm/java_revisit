package com.java.sample;

import com.java.sample.others.Parent;
import com.java.sample.others.Child;

public class World{

  private Parent parent;
  private Parent child;

  private World(){
    parent = new Parent();
    child = new Child();
  }

  public static void main(String[] args){
    System.out.println("Hello World from cmd builded Jar");
    World world = new World();
    world.sayHello();
  }

  public void sayHello(){
    parent.sayHello();
    child.sayHello();
  }
}
