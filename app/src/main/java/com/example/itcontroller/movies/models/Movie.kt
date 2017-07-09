package com.example.itcontroller.movies.models

class Movie(
        val poster_path: String,
        val adult: Boolean,
        val overview: String,
        val release_date: String,
        val genre_ids: IntArray,
        val id: Int,
        val original_title: String,
        val title: String,
        val original_language: String,
        val popularity: Number,
        val vote_average: Number,
        val genres: Array<Genre>,
        val runtime: Int,
        val status: String,
        val tagline: String,
        val vote_count: Int,
        val spoken_languages: Array<SpokenLanguages>
) {
    inner class Genre(
            val id: Int,
            val name: String
    )

    inner class SpokenLanguages(
            val name: String
    )
}