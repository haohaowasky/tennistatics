package io.tennis.statistic.dataStore;
import com.google.gson.Gson;

public abstract class AbstractMessage {
    public String stringify() {
        Gson gson = new Gson();
        return gson.toJson(this);
    }
}
