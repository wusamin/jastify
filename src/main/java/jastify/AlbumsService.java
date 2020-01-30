package jastify;

import java.util.Arrays;
import java.util.stream.Collectors;

import jastify.common.Const;
import jastify.dto.AlbumList;
import lombok.Setter;
import okhttp3.HttpUrl;
import okhttp3.Request;

public class AlbumsService {

    @Setter
    private String token;

    public AlbumsService(String token) {
        this.token = token;
    }

    protected AlbumList getAlbums(String[] ids, String market) {
        final String url = JastifyUtils.get("album.getSeveralAlbums");
        return JastifyUtils
                .setResult(JastifyUtils.sendRequest(new Request.Builder()
                        .url(HttpUrl.parse(url)
                                .newBuilder()
                                .addEncodedQueryParameter("ids",
                                        Arrays.stream(ids)
                                                .collect(Collectors
                                                        .joining(",")))
                                .addEncodedQueryParameter("market", market)
                                .build())
                        .addHeader(Const.TOKEN_KEY, Const.TOKEN_PREFIX + token)
                        .build()), AlbumList.class);
    }

}
