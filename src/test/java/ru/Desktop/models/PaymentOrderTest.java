package ru.Desktop.models;

import org.bson.types.ObjectId;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.Desktop.utils.DOCUMENT_TYPE;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

class PaymentOrderTest {
    private static final String EMPLOYEE = "John Doe";
    private static final String NUMBER = "123456";
    private static final String USER = "user@example.com";
    private static final double AMOUNT = 1000.0;
    private static final String DATE_STRING = "2023-05-01";

    private PaymentOrder paymentOrder;

    @BeforeEach
    void setUp() throws ParseException {
        ObjectId id = new ObjectId();
        Date date = new SimpleDateFormat("yyyy-MM-dd").parse(DATE_STRING);
        paymentOrder = new PaymentOrder(id, DOCUMENT_TYPE.PAYMENT_ORDER, NUMBER, date, USER, AMOUNT, EMPLOYEE);
    }

    @Test
    void testGetEmployee() {
        Assertions.assertEquals(EMPLOYEE, paymentOrder.getEmployee());
    }

    @Test
    void testSetEmployee() {
        String newEmployee = "Jane Doe";
        paymentOrder.setEmployee(newEmployee);
        Assertions.assertEquals(newEmployee, paymentOrder.getEmployee());
    }

}
