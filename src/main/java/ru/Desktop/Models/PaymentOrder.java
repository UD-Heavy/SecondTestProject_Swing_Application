package ru.Desktop.Models;

public class PaymentOrder extends Document{
    private String employee;

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }
}
