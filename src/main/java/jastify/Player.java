package jastify;

import jastify.dto.SpotifyDevice;
import lombok.Setter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

public class Player {

    @Setter
    private String token;

    public Player(String token) {
        this.token = token;
    }

    public void setVolume(int volumePercent, SpotifyDevice device) {

        final String url = JastifyUtils.get("me.player.setVolume");

        JastifyUtils.sendRequest(new Request.Builder()
                .url(HttpUrl.parse(url)
                        .newBuilder()
                        .addEncodedQueryParameter("volume_percent",
                                String.valueOf(volumePercent))
                        .addEncodedQueryParameter("device_id", device.getId())
                        .build())
                .put(RequestBody.create(null, new byte[] {}))
                .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                .build());
    }
}
