package project.com.eventmaster.ui.dialogs;

import project.com.eventmaster.data.model.Ticket;

public class CheckoutData {
    static final double TAX_RATE = 0.12;
    private Ticket ticket;
    private int quantity;
    private double tax;
    private double subtotal;
    private double total;
    private double price;

    public Ticket getTicket() {
        return ticket;
    }

    public void setTicket(Ticket ticket) {
        this.ticket = ticket;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void calculate() {
        if (ticket != null) {
            price = ticket.getPrice();
            subtotal = price * quantity;
            tax = subtotal * TAX_RATE;
            total = subtotal + tax;
        }
    }

    public double getTax() {
        return tax;
    }

    public double getSubtotal() {
        return subtotal;
    }

    public double getTotal() {
        return total;
    }

    public double getPrice() {
        return price;
    }
}
