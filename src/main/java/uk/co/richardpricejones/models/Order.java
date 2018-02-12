package uk.co.richardpricejones.models;

import java.util.Objects;

public class Order {

    private long id;
    private long orderNumber;
    private long personId;

    public Order(long id, long orderNumber, long personId) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.personId = personId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(long orderNumber) {
        this.orderNumber = orderNumber;
    }

    public long getPersonId() {
        return personId;
    }

    public void setPersonId(long personId) {
        this.personId = personId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Order order = (Order) o;
        return id == order.id &&
                orderNumber == order.orderNumber &&
                personId == order.personId;
    }

    @Override
    public int hashCode() {

        return Objects.hash(id, orderNumber, personId);
    }

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", orderNumber=" + orderNumber +
                ", personID=" + personId +
                '}';
    }
}
