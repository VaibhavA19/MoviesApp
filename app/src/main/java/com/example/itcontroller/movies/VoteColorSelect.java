package com.example.itcontroller.movies;

import android.graphics.Color;

/**
 * Created by ITCONTROLLER on 7/7/2017.
 */

public class VoteColorSelect {
    public static int getColor(Number votes) {
        int voteCount = votes.intValue();

        if (voteCount >= 8) return Color.GREEN;
        if (voteCount >= 6) return Color.YELLOW;
        return Color.RED;
    }
}
