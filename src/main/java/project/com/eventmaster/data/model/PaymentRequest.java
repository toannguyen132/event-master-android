package project.com.eventmaster.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PaymentRequest {
    @SerializedName("nonce")
    @Expose
    String nonce;

    @SerializedName("eventId")
    @Expose
    String eventId;

    @SerializedName("ticketId")
    @Expose
    String ticketId;

    @SerializedName("quantity")
    @Expose
    int quantity;

    public String getNonce() {
        return nonce;
    }

    public void setNonce(String nonce) {
        this.nonce = nonce;
    }

    public String getEventId() {
        return eventId;
    }

    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public PaymentRequest(String nonce, String eventId, String ticketId, int quantity) {
        this.nonce = nonce;
        this.eventId = eventId;
        this.ticketId = ticketId;
        this.quantity = quantity;
    }

    static public PaymentRequest create(String nonce, String eventId, String ticketId, int quantity) {
        return new PaymentRequest(nonce, eventId, ticketId, quantity);
    }
}
