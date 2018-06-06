package model;

import com.google.gson.annotations.SerializedName;

import java.util.HashSet;

public class TicketType {

    @SerializedName("name")
    private String type;
    @SerializedName("fields")
    private HashSet<String> fields = new HashSet<>();

    public TicketType(){

    }

    public TicketType(String type, HashSet<String> fields){
        this.setType(type);
        this.setFields(fields);
    }

    public void setType(String type) {
        this.type = type;
    }

    public void setFields(HashSet<String> fields) {
        this.fields = fields;
    }

    public String getType() {
        return type;
    }

}
