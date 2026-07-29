package com.example.tvapps.ui.list

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.tvapps.data.repository.TvRepository
import com.example.tvapps.ui.components.ErrorView
import com.example.tvapps.ui.components.LoadingView
import com.example.tvapps.util.Resource
import com.example.tvapps.viewmodel.ShowListViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShowListScreen(
    repository: TvRepository,
    onShowClick: (Int) -> Unit,
    modifier: Modifier = Modifier,
    viewModel: ShowListViewModel = viewModel(factory = ShowListViewModel.factory(repository))
) {
    val state by viewModel.showState.collectAsState(
    initial = Resource.Loading
)

    Scaffold(
        modifier = modifier,
        topBar = { TopAppBar(title = { Text("Browse Shows") }) }
    ) { padding ->
        when (val current = state) {
            is Resource.Loading -> LoadingView(modifier = Modifier.padding(padding))

            is Resource.Error -> ErrorView(
                message = current.message,
                onRetry = { viewModel.loadShows() },
                modifier = Modifier.padding(padding)
            )

            is Resource.Success -> {
                val items = current.data.map { it.toListItemUi() }
                LazyColumn(modifier = Modifier.padding(padding)) {
                    items(items, key = { it.id }) { item ->
                        ShowListItem(item = item, onClick = onShowClick)
                    }
                }
            }
        }
    }
}
