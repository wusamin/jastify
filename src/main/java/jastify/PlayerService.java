package jastify;

import jastify.common.Const;
import jastify.dto.Device;
import jastify.dto.Devices;
import lombok.Setter;
import okhttp3.FormBody;
import okhttp3.HttpUrl;
import okhttp3.Request;
import okhttp3.RequestBody;

public class PlayerService {

    @Setter
    private String token;

    public PlayerService(String token) {
        this.token = token;
    }

    /**
     * PUT https://api.spotify.com/v1/me/player/volume
     * 
     * @param volumePercent
     * @param device
     */
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

    /**
     * GET https://api.spotify.com/v1/me/player
     * 
     * @param deviceId
     */
    protected void startMusic(String deviceId) {
        final String url = JastifyUtils.get("me.player.startPlayback");

        JastifyUtils
                .sendRequest(
                        new Request.Builder()
                                .url(HttpUrl.parse(url)
                                        .newBuilder()
                                        .addEncodedQueryParameter("device_id",
                                                deviceId)
                                        .build())
                                .put(new FormBody.Builder().build())
                                .addHeader(Const.TOKEN_KEY,
                                        Const.TOKEN_PREFIX + token)
                                .build());
    }

    /**
     * GET https://api.spotify.com/v1/me/player/devices
     * 
     * @return
     */
    protected Devices devices() {
        final String url = JastifyUtils.get("me.player.devices");

        return JastifyUtils.setResult(
                JastifyUtils.sendRequest(new Request.Builder().url(url)
                        .addHeader("Accept", "application/json")
                        .addHeader("Content-Type", "application/json")
                        .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                        .build()),
                Devices.class);
    }
}
