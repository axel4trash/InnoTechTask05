package edu.innotech.enums;

public enum EnumArrangementTypes {
    NSO("НСО"),
    EZHO("ЕЖО"),
    SMO("СМО"),
    DBDS("ДБДС");

    final String s;

    EnumArrangementTypes(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
