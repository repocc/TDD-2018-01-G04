package model;

import com.google.gson.annotations.SerializedName;

public class Role {
    @SerializedName("id")
    private String id;

    public Role(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }
}
