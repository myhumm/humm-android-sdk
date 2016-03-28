package humm.android.api.Deserializers;

import android.util.Log;

import com.google.gson.JsonArray;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import humm.android.api.Model.Song;

/**
 * Created by josealonsogarcia on 19/1/16.
 */
public class SongDeserializer implements JsonDeserializer<Song> {
    @Override
    public Song deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {

        JsonObject jobject = (JsonObject) json;

        String id = jobject.get("_id") != null ? jobject.get("_id").getAsString() : null;
        String title = jobject.get("title") != null ? jobject.get("title").getAsString() : null;
        String description = jobject.get("description") != null ? jobject.get("description").getAsString() : null;
        String type = jobject.get("type") != null ? jobject.get("type").getAsString() : null;
        String date = jobject.get("date") != null ? jobject.get("date").getAsString() : null;
        HashMap urls = context.deserialize(jobject.get("urls"), new TypeToken<HashMap>(){}.getType());
        List<HashMap<String, String>> artists = context.deserialize(jobject.get("artists"), new TypeToken<List<HashMap<String, String>>>(){}.getType());
        List<HashMap> playlists = context.deserialize(jobject.get("playlists"), new TypeToken<List<HashMap>>(){}.getType());

        if (playlists != null && playlists.size() > 10) {
            playlists = playlists.subList(0, 10);
        }
        HashMap<String, String> foreign_ids = context.deserialize(jobject.get("foreign_ids"), new TypeToken<HashMap>(){}.getType());
        HashMap<String, String> stats = context.deserialize(jobject.get("stats"), new TypeToken<HashMap>(){}.getType());
        List contributors = context.deserialize(jobject.get("contributors"), new TypeToken<List>(){}.getType());
        List stories = context.deserialize(jobject.get("stories"), new TypeToken<List>(){}.getType());
        List genres = context.deserialize(jobject.get("genres"), new TypeToken<List>(){}.getType());

        return new Song(id, title, description, type, date, urls, artists, playlists, foreign_ids, stats, contributors, stories, genres);
    }
}
