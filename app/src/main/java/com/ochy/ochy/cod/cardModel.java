package com.ochy.ochy.cod;

public class cardModel {
    public String cardName;
    public String cardNumb;
    public String cardDate;
    public String cardCVC;
    public String cardMan;


    public cardModel() {
    }

    public cardModel(String cardName, String cardNumb, String cardDate, String cardCVC, String cardMan) {
        this.cardName = cardName;
        this.cardNumb = cardNumb;
        this.cardDate = cardDate;
        this.cardCVC = cardCVC;
        this.cardMan = cardMan;
    }
}
