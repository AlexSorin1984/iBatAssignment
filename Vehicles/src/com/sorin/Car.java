package com.sorin;

public class Car extends Vehicle {

    private String brand;
    private int horsePower;

    public Car() {
    }

    public Car(int value, int age, String name, String brand, int horsePower) {
        super(1, value, age, name);
        this.brand = brand;
        this.horsePower = horsePower;
        System.out.println("Value="+value+" Age="+age+" Name="+name+" Brand="+brand+" HorsePower="+horsePower);
    }

    public void changeGear(){
        System.out.println("Car changing gear..");
    }


}

