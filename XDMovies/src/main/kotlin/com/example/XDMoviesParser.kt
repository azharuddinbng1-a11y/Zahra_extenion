package com.example

import com.fasterxml.jackson.annotation.JsonProperty
import com.lagradost.cloudstream3.SearchQuality
import java.text.Normalizer

class SearchData : ArrayList<SearchData.SearchDataItem>(){
    data class SearchDataItem(
        val audio_languages: String,
        val exact_match: Int,
        val id: Int,
        val path: String,
        val poster: String,
        val qualities: List<String>,
        val release_year: String,
        val title: String,
        val tmdb_id: Int,
        val type: String
    )
}

data class IMDB(
    @JsonProperty("imdb_id")
    val imdbId: String? = null
)

data class Meta(
    val id: String?,
    val imdb_id: String?,
    val type: String?,
    val poster: String?,
    val logo: String?,
    val background: String?,
    val moviedb_id: Int?,
    val name: String?,
    val description: String?,
    val genre: List<String>?,
    val releaseInfo: String?,
    val status: String?,
    val runtime: String?,
    val cast: List<String>?,
    val language: String?,
    val country: String?,
    val imdbRating: String?,
    val slug: String?,
    val year: String?,
    val videos: List<EpisodeDetails>?
)

data class EpisodeDetails(
    val id: String?,
    val name: String?,
    val title: String?,
    val season: Int?,
    val episode: Int?,
    val released: String?,
    val overview: String?,
    val thumbnail: String?,
    val moviedb_id: Int?
)

data class ResponseData(
    val meta: Meta?
)

data class TMDBRes(
    @JsonProperty("_id")
    val id: String? = null,

    @JsonProperty("air_date")
    val airDate: String? = null,

    val episodes: List<TMDBEpisode>? = null,

    val name: String? = null,

    val networks: List<Network>? = null,

    val overview: String? = null,

    @JsonProperty("id")
    val id2: Long? = null,

    @JsonProperty("poster_path")
    val posterPath: String? = null,

    @JsonProperty("season_number")
    val seasonNumber: Int? = null,

    @JsonProperty("vote_average")
    val voteAverage: Double? = null
)

data class TMDBEpisode(
    @JsonProperty("air_date")
    val airDate: String? = null,

    @JsonProperty("episode_number")
    val episodeNumber: Int? = null,

    @JsonProperty("episode_type")
    val episodeType: String? = null,

    val id: Long? = null,

    val name: String? = null,

    val overview: String? = null,

    @JsonProperty("production_code")
    val productionCode: String? = null,

    val runtime: Int? = null,

    @JsonProperty("season_number")
    val seasonNumber: Int? = null,

    @JsonProperty("show_id")
    val showId: Long? = null,

    @JsonProperty("still_path")
    val stillPath: String? = null,

    @JsonProperty("vote_average")
    val voteAverage: Double? = null,

    @JsonProperty("vote_count")
    val voteCount: Int? = null,

    val crew: List<Crew>? = null,

    @JsonProperty("guest_stars")
    val guestStars: List<GuestStar>? = null
)

data class Network(
    val id: Long? = null,
    @JsonProperty("logo_path")
    val logoPath: String? = null,
    val name: String? = null,
    @JsonProperty("origin_country")
    val originCountry: String? = null
)

data class Crew(
    val id: Long? = null,
    val name: String? = null,
    val job: String? = null
)

data class GuestStar(
    val id: Long? = null,
    val name: String? = null,
    val character: String? = null,
    val order: Int? = null
)

fun getSearchQuality(check: String?): SearchQuality? {
    val s = check ?: return null
    val u = Normalizer.normalize(s, Normalizer.Form.NFKC).lowercase()
    val patterns = listOf(
        Regex("\\b(4k|ds4k|uhd|2160p)\\b", RegexOption.IGNORE_CASE) to SearchQuality.FourK,
        Regex("\\b(hdts|hdcam|hdtc)\\b", RegexOption.IGNORE_CASE) to SearchQuality.HdCam,
        Regex("\\b(camrip|cam[- ]?rip)\\b", RegexOption.IGNORE_CASE) to SearchQuality.CamRip,
        Regex("\\b(cam)\\b", RegexOption.IGNORE_CASE) to SearchQuality.Cam,
        Regex("\\b(web[- ]?dl|webrip|webdl)\\b", RegexOption.IGNORE_CASE) to SearchQuality.WebRip,
        Regex("\\b(bluray|bdrip|blu[- ]?ray)\\b", RegexOption.IGNORE_CASE) to SearchQuality.BlueRay,
        Regex("\\b(1440p|qhd)\\b", RegexOption.IGNORE_CASE) to SearchQuality.BlueRay,
        Regex("\\b(1080p|fullhd)\\b", RegexOption.IGNORE_CASE) to SearchQuality.HD,
        Regex("\\b(720p)\\b", RegexOption.IGNORE_CASE) to SearchQuality.SD,
        Regex("\\b(hdrip|hdtv)\\b", RegexOption.IGNORE_CASE) to SearchQuality.HD,
        Regex("\\b(dvd)\\b", RegexOption.IGNORE_CASE) to SearchQuality.DVD,
        Regex("\\b(hq)\\b", RegexOption.IGNORE_CASE) to SearchQuality.HQ,
        Regex("\\b(rip)\\b", RegexOption.IGNORE_CASE) to SearchQuality.CamRip
    )

    for ((regex, quality) in patterns) if (regex.containsMatchIn(u)) return quality
    return null
}
