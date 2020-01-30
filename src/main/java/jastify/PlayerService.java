package jastify;

import jastify.common.Const;
import jastify.dto.Device;
import lombok.Setter;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PlayerService {

    @Setter
    private String token;

    public PlayerService(String token) {
        this.token = token;
    }

    public void setVolume(int volumePercent, Device device) {

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
