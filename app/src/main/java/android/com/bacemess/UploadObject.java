package android.com.bacemess;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by Nilesh on 7/16/2018.
 */

public class UploadObject {
    @SerializedName("status")
    @Expose
    private String status = "";

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
