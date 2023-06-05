package com.ochy.ochy.cod;

import android.os.Parcel;
import android.os.Parcelable;

public class docsModel implements Parcelable {
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(docSurname);
        dest.writeString(docName);
        dest.writeString(docPatronymic);
        dest.writeString(docBirth);
        dest.writeString(docCitizen);
        dest.writeString(docNumber);
        dest.writeString(sex);
        dest.writeString(docType);
    }

    public static final Parcelable.Creator<docsModel> CREATOR = new Parcelable.Creator<docsModel>() {
        @Override
        public docsModel createFromParcel(Parcel source) {
            return new docsModel(source);
        }

        @Override
        public docsModel[] newArray(int size) {
            return new docsModel[size];
        }
    };

    private docsModel(Parcel source) {
        docSurname = source.readString();
        docName = source.readString();
        docPatronymic = source.readString();
        docBirth = source.readString();
        docCitizen = source.readString();
        docNumber = source.readString();
        sex = source.readString();
        docType = source.readString();
    }
}
