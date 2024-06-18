package ru.Desktop.models;

import org.bson.types.ObjectId;

import java.text.ParseException;
import java.util.Date;

public class PaymentOrder extends Document {
    private String employee;

    public PaymentOrder(org.bson.Document document) throws ParseException {
        super(document);
        this.employee = document.get("employee").toString();
    }

    public PaymentOrder(ObjectId id, DOCUMENT_TYPE documentType, String number, Date date, String user, double amount, String employee) {
        super(id, documentType, number, date, user, amount);
        this.employee = employee;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    @Override
    public String toString() {
        return "PaymentOrder{ " +
                " employee='" + employee + '\'' +
                '}';
    }
}
