package space.botlist.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import okhttp3.*;
import space.botlist.api.bot.Bot;

import java.io.IOException;
import java.util.List;

/**
 * @author Biosphere, GregTCLTK
 * @date 28.04.18
 */

public class BotlistSpaceClient {

    private final String token, botid;

    public BotlistSpaceClient(String token, String botid) {
        this.token = token;
        this.botid = botid;
    }

    public void postStats(int servercount) throws IOException {
        JsonObject jsonObject = new JsonObject();
        jsonObject.addProperty("server_count", servercount);
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), jsonObject.toString());

        Request request = new Request.Builder()
                .url("https://botlist.space/api/bots/" + botid)
                .post(body)
                .addHeader("Authorization", token)
                .build();


        new OkHttpClient().newCall(request).execute().close();
    }

    public void postStats(List<Integer> shards) throws IOException {
        RequestBody body = RequestBody.create(MediaType.parse("application/json"), new Gson().toJson(shards));
        Request request = new Request.Builder()
                .url("https://botlist.space/api/bots/" + botid)
                .post(body)
                .addHeader("Authorization", token)
                .build();


        new OkHttpClient().newCall(request).execute().close();
    }

    public List<Bot> getBots() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://botlist.space/api/bots")
                .build();
        Response response = client.newCall(request).execute();
        return new Gson().fromJson(response.body().string(), new TypeToken<List<Bot>>(){}.getType());
    }

    public Bot getBot(String id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://botlist.space/api/bots/" + id)
                .build();
        Response response = client.newCall(request).execute();
        return new Gson().fromJson(response.body().string(), Bot.class);
    }

    public Bot getStats() throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://botlist.space/api/stats")
                .build();
        Response response = client.newCall(request).execute();
        return new Gson().fromJson(response.body().string(), Bot.class);
    }

    public Bot getUser(String id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://botlist.space/api/users/" + id)
                .build();
        Response response = client.newCall(request).execute();
        return new Gson().fromJson(response.body().string(), Bot.class);
    }

    public List<Bot> getUpvoter(String id) throws IOException {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://botlist.space/api/bots/" + id + "/upvotes?ids=true")
                .addHeader("Authorization", token)
                .build();
        Response response = client.newCall(request).execute();
        return new Gson().fromJson(response.body().string(), new TypeToken<List<Bot>>(){}.getType());
    }
}
