package edu.innotech.enums;

/*instanceArrangement  .coefficientAction
*                      .minimumInterestRateCoefficientAction
*                      .maximalnterestRateCoefficientAction
* */
public enum EnumCoefficientAction {
    PLUS("+"),
    MINUS("-");

    final String s;

    EnumCoefficientAction(String s) {
        this.s = s;
    }

    @Override
    public String toString() {
        return s;
    }
}
