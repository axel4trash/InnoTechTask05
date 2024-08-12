package edu.innotech.enums;

public enum EnumAccountTypes {
    CL("Клиентский"),
    VB("Внутрибанковский");
    final String s;

    EnumAccountTypes(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
