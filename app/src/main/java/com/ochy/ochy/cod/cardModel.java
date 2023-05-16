package com.ochy.ochy.cod;

public class cardModel {
    public String cardNumb;
    public String cardDate;
    public String cardCVC;
    public String cardMan;


    public cardModel() {
    }

    public cardModel( String cardNumb, String cardDate, String cardCVC, String cardMan) {
        this.cardNumb = cardNumb;
        this.cardDate = cardDate;
        this.cardCVC = cardCVC;
        this.cardMan = cardMan;
    }
}
