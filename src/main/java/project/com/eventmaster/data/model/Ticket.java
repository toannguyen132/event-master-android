package project.com.eventmaster.data.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Ticket {

    @SerializedName("price")
    @Expose
    private Double price;

    @SerializedName("number")
    @Expose
    private String number;
    @SerializedName("available")
    @Expose
    private String available;
    @SerializedName("id")
    @Expose
    private String id;
    @SerializedName("name")
    @Expose
    private String name;

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getAvailable() {
        return available;
    }

    public void setAvailable(String available) {
        this.available = available;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public static String getPriceString(List<Ticket> tickets) {
        if ( tickets.size() == 1 ) {
            return "$" + tickets.get(0);
        }
        double min = 999999999;
        double max = 0;
        for (Ticket ticket : tickets) {
            if (min > ticket.price) min = ticket.price;
            if (max < ticket.price) max = ticket.price;
        }

        return "$" + min + " - " + "$" + max;
    }
}
