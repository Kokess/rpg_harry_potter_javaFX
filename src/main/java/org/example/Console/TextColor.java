package org.example.Console;

public class TextColor {
    private String colorCode;
    public TextColor(){};

    public String getColorCode(){
        return this.colorCode;
    }
    public void setRed(){
        this.colorCode = "\u001B[31m";
    }

    public void setWhite(){
        this.colorCode = "\u001B[0m";
    }

    public void setGreen(){
        this.colorCode = "\u001B[32m";
    }

    public void setYellow(){
        this.colorCode = "\u001B[33m";
    }
}
