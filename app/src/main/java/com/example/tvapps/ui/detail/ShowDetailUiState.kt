package com.example.tvapps.ui.detail

import com.example.tvapps.data.model.CastDto
import com.example.tvapps.data.model.ShowDto
import com.example.tvapps.util.fromHtml

data class ShowDetailUi(
    val id: Int,
    val title: String,
    val posterUrl: String?,
    val summary: String,
    val premiered: String?,
    val ratingText: String,
    val cast: List<CastMemberUi>,
    val url: String?
)

data class CastMemberUi(
    val id: Int,
    val name: String,
    val character: String,
    val imageUrl: String?
)

fun ShowDto.toDetailUi(cast: List<CastDto> = emptyList()): ShowDetailUi = ShowDetailUi(
    id = id,
    title = name,
    posterUrl = image?.original ?: image?.medium,
    summary = summary.fromHtml(),
    premiered = premiered,
    ratingText = rating?.average?.let { "★ %.1f".format(it) } ?: "N/A",
    cast = cast.mapNotNull { it.toCastMemberUi() },
    url = url
)

fun ShowDetailUi.toShareText(): String = buildString {
    appendLine(title)
    if (summary.isNotBlank()) {
        appendLine()
        appendLine(summary)
    }
    if (!url.isNullOrBlank()) {
        appendLine()
        append(url)
    }
}

private fun CastDto.toCastMemberUi(): CastMemberUi? {
    val person = person ?: return null
    val personId = person.id ?: return null
    return CastMemberUi(
        id = personId,
        name = person.name ?: "Unknown",
        character = character?.name ?: "",
        imageUrl = person.image?.medium
    )
}