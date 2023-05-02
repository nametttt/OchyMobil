package com.ochy.ochy.cod;

public class cardsDataList {
    private String cardName;
    private String cardNumber;


    public cardsDataList(String cardName, String cardNumber) {
        this.cardName = cardName;
        this.cardNumber = cardNumber;
    }

    public String getCardName() {
        return cardName;
    }

    public String getCardNumber() {
        return cardNumber;
    }
}
