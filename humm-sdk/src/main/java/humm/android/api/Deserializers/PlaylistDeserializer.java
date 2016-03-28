package humm.android.api.Deserializers;

import android.util.Log;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.HashMap;
import java.util.List;

import humm.android.api.Model.PlaylistOwnerList;
import humm.android.api.Model.Song;

/**
 * Created by josealonsogarcia on 16/2/16.
 */
public class PlaylistDeserializer implements JsonDeserializer<PlaylistOwnerList> {
    @Override
    public PlaylistOwnerList deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        JsonObject jobject = (JsonObject) json;
        String id = jobject.get("_id") != null ? jobject.get("_id").getAsString() : null;
        String title = jobject.get("title") != null ? jobject.get("title").getAsString() : null;
//        String description = jobject.get("description") != null ? jobject.get("description").getAsString() : null;
        HashMap<String, String> stats = context.deserialize(jobject.get("stats"), new TypeToken<HashMap>(){}.getType());
        List contributors = context.deserialize(jobject.get("contributors"), new TypeToken<List>(){}.getType());

        List<HashMap<String, String>> owner = null;
        try {
             owner = context.deserialize(jobject.get("owner"), new TypeToken<List<HashMap<String, String>>>() {
            }.getType());
        }catch (Exception e)
        {
            //
        }
        return new PlaylistOwnerList(id, title, "", stats, contributors, owner);
    }
}
