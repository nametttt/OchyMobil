package com.ochy.ochy.cod;

public class AirlineTicket {

    private String docFI;

    private String marshr;
    private String dates;
    private String documentType;
    private String documentNumber;
    private String flightNumber;
    private String seatNumber;
    private String qrCodeUrl;

    public AirlineTicket() {
        // Default constructor required for calls to DataSnapshot.getValue(AirlineTicket.class)
    }

    public AirlineTicket(String marshr, String dates, String docFI,String documentType, String documentNumber, String flightNumber, String seatNumber, String qrCodeUrl) {
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.flightNumber = flightNumber;
        this.seatNumber = seatNumber;
        this.qrCodeUrl = qrCodeUrl;
        this.docFI = docFI;
        this.dates = dates;
        this.marshr = marshr;
    }

    public String getMarshr() {
        return marshr;
    }

    public String getDates() {
        return dates;
    }

    public String getDocFI() {
        return docFI;
    }

    public String getDocumentType() {
        return documentType;
    }

    public void setDocumentType(String documentType) {
        this.documentType = documentType;
    }

    public String getDocumentNumber() {
        return documentNumber;
    }

    public void setDocumentNumber(String documentNumber) {
        this.documentNumber = documentNumber;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(String flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getSeatNumber() {
        return seatNumber;
    }

    public void setSeatNumber(String seatNumber) {
        this.seatNumber = seatNumber;
    }

    public String getQrCodeUrl() {
        return qrCodeUrl;
    }

    public void setQrCodeUrl(String qrCodeUrl) {
        this.qrCodeUrl = qrCodeUrl;
    }
}
