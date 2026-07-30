package com.example.tvapps.ui.detail

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import android.content.Context
import android.content.Intent
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Share
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.compose.AsyncImage
import com.example.tvapps.data.repository.TvRepository
import com.example.tvapps.ui.components.ErrorView
import com.example.tvapps.ui.components.LoadingView
import com.example.tvapps.util.Resource
import com.example.tvapps.viewmodel.ShowDetailViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowDetailScreen(
    showId: Int,
    repository: TvRepository,
    onBackClick: () -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShowDetailViewModel = viewModel(
        factory = ShowDetailViewModel.factory(repository, showId)
    )
) {
    val state by viewModel.detailState.collectAsState()
    val context = LocalContext.current

    Scaffold(
        modifier = modifier,
        topBar = {
            TopAppBar(
                title = { Text("Show Detail") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Back")
                    }
                },
                actions = {
                    val current = state
                    if (current is Resource.Success) {
                        IconButton(onClick = { shareShow(context, current.data) }) {
                            Icon(Icons.Default.Share, contentDescription = "Share")
                        }
                    }
                }
            )
        }
    ) { padding ->
        when (val current = state) {
            is Resource.Loading -> LoadingView(modifier = Modifier.padding(padding))

            is Resource.Error -> ErrorView(
                message = current.message,
                onRetry = { viewModel.loadDetail() },
                modifier = Modifier.padding(padding)
            )

            is Resource.Success -> ShowDetailContent(
                detail = current.data,
                modifier = Modifier.padding(padding)
            )
        }
    }
}

@Composable
private fun ShowDetailContent(
    detail: ShowDetailUi,
    modifier: Modifier = Modifier
) {
    LazyColumn(
        modifier = modifier.fillMaxWidth(),
        contentPadding = androidx.compose.foundation.layout.PaddingValues(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        item {
            AsyncImage(
                model = detail.posterUrl,
                contentDescription = detail.title,
                modifier = Modifier
                    .fillMaxWidth()
                    .height(320.dp)
                    .clip(RoundedCornerShape(12.dp))
            )
        }

        item {
            Text(text = detail.title, style = MaterialTheme.typography.headlineSmall)
        }

        item {
            Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                Text(text = detail.ratingText, style = MaterialTheme.typography.bodyMedium)
                detail.premiered?.let {
                    Text(text = "Premiered: $it", style = MaterialTheme.typography.bodyMedium)
                }
            }
        }

        if (detail.summary.isNotBlank()) {
            item {
                Text(text = detail.summary, style = MaterialTheme.typography.bodyMedium)
            }
        }

        if (detail.cast.isNotEmpty()) {
            item {
                Text(text = "Cast", style = MaterialTheme.typography.titleMedium)
            }
            item {
                LazyRow(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
                    items(detail.cast, key = { it.id }) { member ->
                        CastMemberItem(member)
                    }
                }
            }
        }
    }
}

private fun shareShow(context: Context, detail: ShowDetailUi) {
    val shareIntent = Intent(Intent.ACTION_SEND).apply {
        type = "text/plain"
        putExtra(Intent.EXTRA_SUBJECT, detail.title)
        putExtra(Intent.EXTRA_TEXT, detail.toShareText())
    }
    context.startActivity(Intent.createChooser(shareIntent, "Share ${detail.title}"))
}

@Composable
private fun CastMemberItem(member: CastMemberUi) {
    Column(
        horizontalAlignment = androidx.compose.ui.Alignment.CenterHorizontally,
        modifier = Modifier.size(width = 88.dp, height = 140.dp)
    ) {
        AsyncImage(
            model = member.imageUrl,
            contentDescription = member.name,
            modifier = Modifier
                .size(width = 72.dp, height = 96.dp)
                .clip(RoundedCornerShape(8.dp))
        )
        Text(
            text = member.name,
            style = MaterialTheme.typography.labelSmall,
            maxLines = 1
        )
        Text(
            text = member.character,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1
        )
    }
}