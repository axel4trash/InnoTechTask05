package edu.innotech.enums;

public enum EnumProductRegisterStatus {
    CLOSED("0"),
    OPEN("1"),
    RESERVED("2"),
    DELETED("3");

    final String s;

    EnumProductRegisterStatus(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
