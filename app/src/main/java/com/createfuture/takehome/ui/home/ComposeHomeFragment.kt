package com.createfuture.takehome.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.createfuture.takehome.R
import com.createfuture.takehome.data.CharacterRepository
import com.createfuture.takehome.models.ApiCharacter
import com.createfuture.takehome.network.RetrofitClient
import com.createfuture.takehome.utils.Constants
import com.createfuture.takehome.viewmodel.CharacterViewModel
import com.createfuture.takehome.viewmodel.CharacterViewModelFactory

/**
 * Fragment responsible for displaying the Game of Thrones character list.
 * Utilizes Jetpack Compose to build a vertically scrollable UI including:
 * - Top bar
 * - Search input
 * - Cards with character info
 * - Error/loading handling
 *
 * Follows MVVM architecture and fetches data using [CharacterViewModel].
 */
class ComposeHomeFragment : Fragment() {

    private val repository by lazy { CharacterRepository(RetrofitClient.characterService) }

    private val viewModel: CharacterViewModel by viewModels {
        CharacterViewModelFactory(repository)
    }

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ComposeView = ComposeView(requireContext()).apply {
        setContent {
            val characters by viewModel.characters.collectAsState()
            val isLoading by viewModel.loading.collectAsState()
            val hasError by viewModel.error.collectAsState()
            val searchQuery = remember { mutableStateOf("") }
            val showExitDialog = remember { mutableStateOf(false) }

            LaunchedEffect(Unit) {
                viewModel.loadCharacters(Constants.AUTH_TOKEN)
            }

            if (showExitDialog.value) {
                AlertDialog(
                    onDismissRequest = { showExitDialog.value = false },
                    confirmButton = {
                        TextButton(onClick = { requireActivity().finish() }) { Text("Yes") }
                    },
                    dismissButton = {
                        TextButton(onClick = { showExitDialog.value = false }) { Text("No") }
                    },
                    title = { Text("Exit App") },
                    text = { Text("Are you sure you want to exit?") }
                )
            }

            Box(modifier = Modifier.fillMaxSize()) {
                Image(
                    painter = painterResource(id = R.drawable.img_characters),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.matchParentSize()
                )

                Column(
                    modifier = Modifier
                        .verticalScroll(rememberScrollState())
                        .background(Color.Black.copy(alpha = 0.3f))
                        .fillMaxSize()
                ) {
                    CenterAlignedTopAppBar(
                        title = { Text("Game of Thrones", color = Color.White) },
                        colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                            containerColor = Color(0xFF1A1A1A)
                        )
                    )

                    SearchBar(
                        value = searchQuery.value,
                        onValueChange = { searchQuery.value = it },
                        enabled = !isLoading
                    )

                    when {
                        isLoading -> CircularProgressIndicator(
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 16.dp),
                            color = Color.White
                        )

                        hasError -> Text(
                            text = "Unable to load data. Check your internet connection.",
                            color = Color.Red,
                            modifier = Modifier
                                .align(Alignment.CenterHorizontally)
                                .padding(top = 16.dp)
                        )

                        else -> {
                            val filtered = characters.filter {
                                it.name.contains(searchQuery.value, ignoreCase = true)
                            }
                            filtered.forEach { character ->
                                CharacterCard(character)
                            }
                        }
                    }
                }
            }

            BackHandler(enabled = true) {
                showExitDialog.value = true
            }
        }
    }

    /**
     * Search input field for filtering character names.
     */
    @Composable
    fun SearchBar(value: String, onValueChange: (String) -> Unit, enabled: Boolean) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 8.dp, vertical = 8.dp)
                .height(48.dp)
                .background(Color(0xFF2C2C2C), shape = RoundedCornerShape(12.dp)),
            contentAlignment = Alignment.CenterStart //
        ) {
            if (value.isEmpty()) {
                Text("Search", color = Color.LightGray, modifier = Modifier.padding(start = 16.dp))
            }

            BasicTextField(
                value = value,
                onValueChange = onValueChange,
                singleLine = true,
                enabled = enabled,
                textStyle = LocalTextStyle.current.copy(color = Color.White),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 16.dp) // ensure consistent left spacing with placeholder
            )
        }
    }

    /**
     * Composable function that displays an individual character's information.
     */
    @Composable
    fun CharacterCard(character: ApiCharacter) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp, vertical = 6.dp)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.Top
            ) {
                Column(
                    modifier = Modifier
                        .weight(1f)
                ) {
                    Text(
                        text = character.name,
                        color = Color.White,
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        modifier = Modifier
                            .padding(bottom = 2.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))
                    InfoRow("Culture:", character.culture)
                    Spacer(modifier = Modifier.height(2.dp))
                    InfoRow("Born:", character.born)
                    Spacer(modifier = Modifier.height(2.dp))
                    InfoRow("Died:", character.died)
                }

                Column(
                    modifier = Modifier

                ) {
                    Text(
                        "Seasons:",
                        color = Color.White,
                        fontWeight = FontWeight.Medium,
                        fontSize = 14.sp
                    )
                    Text(
                        text = character.tvSeries.map {
                            when (it.trim()) {
                                "Season 1" -> "I"
                                "Season 2" -> "II"
                                "Season 3" -> "III"
                                "Season 4" -> "IV"
                                "Season 5" -> "V"
                                "Season 6" -> "VI"
                                "Season 7" -> "VII"
                                "Season 8" -> "VIII"
                                else -> ""
                            }
                        }.filter { it.isNotBlank() }.joinToString(", "),
                        color = Color.Gray,
                        fontSize = 12.sp,
                        fontWeight = FontWeight.Normal,
                        modifier = Modifier.padding(top = 4.dp)

                    )
                }
            }

            Spacer(modifier = Modifier.height(12.dp))
            Divider(color = Color.White.copy(alpha = 0.5f), thickness = 1.dp)
        }
    }

    /**
     * Displays a single labeled value row (e.g., Born: In 283 AC).
     */
    @Composable
    fun InfoRow(label: String, value: String) {
        Text(
            buildAnnotatedString {
                withStyle(style = SpanStyle(fontWeight = FontWeight.Medium, color = Color.White)) {
                    append(label)
                }
                append(" ")
                withStyle(style = SpanStyle(color = Color.LightGray)) {
                    append(value)
                }
            },
            fontSize = 14.sp
        )
    }
}