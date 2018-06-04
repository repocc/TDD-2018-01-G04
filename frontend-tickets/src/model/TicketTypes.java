package model;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;
import java.util.Vector;

public class TicketTypes {

    @SerializedName("name")
    private String type;
    @SerializedName("fields")
    private HashSet<String> fields = new HashSet<>();

    public void setType(String type) {
        this.type = type;
    }

    public void setFields(HashSet<String> fields) {
        this.fields = fields;
    }

    public String getType() {
        return type;
    }

    public HashSet<String> getFields() {
        return fields;
    }

}
