package jastify.parameter;

import java.util.HashMap;
import java.util.Map;

import jastify.common.JastifyEnums.Pitch;
import lombok.Data;

@Data
public class TuneableTrack {

    /**
     * The acousticness allows in 0.0 to 1.0
     */
    private float acousticness;

    /**
     * The danceability allows in 0.0 to 1.0
     */
    private float danceability;

    /**
     * The duration of the track in milliseconds.
     */
    private int durationMs;

    /**
     * The energy allows in 0.0 to 1.0
     */
    private float energy;

    /**
     * The instrumentalness allows in 1.0 to 0.0
     */
    private float instrumentalness;

    /**
     * standard Pitch Class notation.
     */
    private Pitch key;

    /**
     * The liveness allows in 1.0 to 0.0
     */
    private float liveness;

    /**
     * The loudness allows in 1.0 to 0.0
     */
    private float loudness;

    /**
     * The mode indicates the modality. Major is representd by 1 and minor is 0.
     */
    private int mode;

    /**
     * The popularity allows in 0 to 100
     */
    private int popularity;

    /**
     * The speechiness allow in 0 to 1.0
     */
    private float speechiness;

    /**
     * The tempo represents a track in beats per minute (BPM)
     */
    private float tempo;

    /**
     * An estimated overall time signature of a track.
     */
    private int timeSignature;

    /**
     * The valence allows in 0 to 1.0
     */
    private float valence;

    public Map<String, String> toMap() {
        Map<String, String> r = new HashMap<>();
        if (acousticness != 0)
            r.put("acousticness", String.valueOf(acousticness));

        if (danceability != 0)
            r.put("danceability", String.valueOf(danceability));

        if (durationMs != 0)
            r.put("duration_ms", String.valueOf(durationMs));

        if (energy != 0)
            r.put("energy", String.valueOf(energy));

        if (instrumentalness != 0)
            r.put("instrumentalness", String.valueOf(instrumentalness));

        if (key != null)
            r.put("key", String.valueOf(key.getCode()));

        if (liveness != 0)
            r.put("liveness", String.valueOf(liveness));

        if (loudness != 0)
            r.put("loudness", String.valueOf(loudness));

        if (mode != 0)
            r.put("mode", String.valueOf(mode));

        if (popularity != 0)
            r.put("popularity", String.valueOf(popularity));

        if (speechiness != 0)
            r.put("speechiness", String.valueOf(speechiness));

        if (tempo != 0)
            r.put("tempo", String.valueOf(tempo));

        if (timeSignature != 0)
            r.put("time_signature", String.valueOf(timeSignature));

        if (valence != 0)
            r.put("valence", String.valueOf(valence));

        return r;
    }
}
