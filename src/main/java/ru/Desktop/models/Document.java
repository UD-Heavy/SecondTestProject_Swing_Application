package ru.Desktop.models;

import org.bson.types.ObjectId;
import ru.Desktop.utils.DOCUMENT_TYPE;
import ru.Desktop.utils.DateFieldDescription;
import ru.Desktop.utils.FieldDescription;

import java.text.ParseException;
import java.util.Date;

import static java.lang.Double.parseDouble;

public abstract class Document {

    private ObjectId _id;
    private DOCUMENT_TYPE documentType;
    @FieldDescription("Номер")
    private String number;
    @FieldDescription("Дата")
    @DateFieldDescription
    private Date date;
    @FieldDescription("Пользователь")
    private String user;
    @FieldDescription("Сумма")
    private double amount;

    public Document(org.bson.Document document) throws ParseException {
        this._id = new ObjectId(document.get("_id").toString());
        this.documentType = DOCUMENT_TYPE.valueOf(document.get("documentType").toString());
        this.number = document.get("number").toString();
        this.date = new Date(Long.parseLong(document.get("date").toString()));
        this.user = document.get("user").toString();
        this.amount = parseDouble(document.get("amount").toString());
    }

    public Document(ObjectId id, DOCUMENT_TYPE documentType, String number, Date date, String user, double amount) {
        this._id = id;
        this.documentType = documentType;
        this.number = number;
        this.date = date;
        this.user = user;
        this.amount = amount;
    }

    public ObjectId get_id() {
        return _id;
    }

    public void set_id(ObjectId _id) {
        this._id = _id;
    }

    public DOCUMENT_TYPE getDocumentType() {
        return documentType;
    }

    public void setDocumentType(DOCUMENT_TYPE documentType) {
        this.documentType = documentType;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

}
