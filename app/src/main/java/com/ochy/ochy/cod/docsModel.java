package com.ochy.ochy.cod;

public class docsModel {
    public String docSurname;
    public String docName;
    public String docPatronymic;
    public String docBirth;
    public String docCitizen;
    public String docNumber;
    public String sex;

    public  String docType;

    public docsModel() {
    }

    public docsModel(String docSurname, String docName, String docPatronymic,
                     String docBirth, String docCitizen, String docNumber, String sex, String docType) {
        this.docSurname = docSurname;
        this.docName = docName;
        this.docPatronymic = docPatronymic;
        this.docBirth = docBirth;
        this.docCitizen = docCitizen;
        this.docNumber = docNumber;
        this.sex = sex;
        this.docType = docType;
    }

    public String getDocSurname() {
        return docSurname;
    }

    public void setDocSurname(String docSurname) {
        this.docSurname = docSurname;
    }

    public String getDocName() {
        return docName;
    }

    public void setDocName(String docName) {
        this.docName = docName;
    }

    public String getDocPatronymic() {
        return docPatronymic;
    }

    public void setDocPatronymic(String docPatronymic) {
        this.docPatronymic = docPatronymic;
    }

    public String getDocBirth() {
        return docBirth;
    }

    public void setDocBirth(String docBirth) {
        this.docBirth = docBirth;
    }

    public String getDocCitizen() {
        return docCitizen;
    }

    public void setDocCitizen(String docCitizen) {
        this.docCitizen = docCitizen;
    }

    public String getDocNumber() {
        return docNumber;
    }

    public void setDocNumber(String docNumber) {
        this.docNumber = docNumber;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }
}
