package com.createfuture.takehome.ui.home

import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.paint
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.ComposeView
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.fragment.app.Fragment
import androidx.lifecycle.lifecycleScope
import com.google.gson.GsonBuilder
import com.createfuture.takehome.R
import com.createfuture.takehome.models.ApiCharacter
import kotlinx.coroutines.launch
import okhttp3.OkHttpClient
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.GET
import retrofit2.http.Header
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.Alignment
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.font.FontWeight


class ComposeHomeFragment : Fragment() {
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): ComposeView = ComposeView(requireContext()).apply {
        var retrofit = Retrofit.Builder().baseUrl("https://yj8ke8qonl.execute-api.eu-west-1.amazonaws.com").addConverterFactory(GsonConverterFactory.create(GsonBuilder().create())).client(OkHttpClient.Builder().build()).build()
        var service = retrofit.create(Service::class.java)
        var charactersBody = mutableStateOf<List<ApiCharacter>?>(null)
        viewLifecycleOwner.lifecycleScope.launch {
            var _characters = service.getCharacters("Bearer 754t!si@glcE2qmOFEcN")
            charactersBody.value = _characters.body()!!
        }
        setContent {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .paint(
                        painterResource(id = R.drawable.img_characters),
                        contentScale = ContentScale.FillBounds
                    )
            ){
                if (charactersBody.value != null) {
                    for (character in charactersBody.value!!) {
                        Column(   // Qandil 2: character will appear in a block, more readabile text,
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 16.dp, vertical = 8.dp)
                                .shadow(elevation = 4.dp, shape = RoundedCornerShape(12.dp))
                                .background(Color(0xF01A1A1A), shape = RoundedCornerShape(12.dp))
                                .padding(12.dp)
                        ) {
                            Row(
                                modifier = Modifier
                                    .fillMaxWidth()
                                    .padding(start = 16.dp), //Qandil 2: fix rows height issue
                                verticalAlignment = Alignment.Top
                            ) {
                                Column(modifier = Modifier.weight(1f)) { //Qandil 2: fix rows height issue
                                    Text(
                                        text = character.name,
                                        color = Color.White,
                                        fontSize = 18.sp, // Qandil 2: prominent character name
                                        fontWeight = FontWeight.Bold // Qandil 2: prominent character name

                                    )
                                    Row {
                                        Text(
                                            text = "Culture: ",
                                            fontSize = 14.sp,
                                            color = Color.White,
                                            fontWeight = FontWeight.Medium,
                                            modifier = Modifier.alignByBaseline()
                                        )
                                        Text(
                                            text = character.culture,
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            modifier = Modifier.alignByBaseline()
                                        )
                                    }
                                    Row {
                                        Text("Born: ",  color = Color.White,
                                            fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.alignByBaseline())
                                        Text(
                                            text = character.born,
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            modifier = Modifier.alignByBaseline()
                                        )
                                    }
                                    Row {
                                        Text(text = "Died: ",  color = Color.White,
                                            fontWeight = FontWeight.Medium, fontSize = 14.sp, modifier = Modifier.alignByBaseline())
                                        Text(
                                            text = character.died,
                                            color = Color.White,
                                            fontSize = 14.sp,
                                            modifier = Modifier.alignByBaseline()
                                        )
                                    }
                                }
                                Column(modifier = Modifier.align(Alignment.CenterVertically)) { // Qandil 2: align seasons right
                                    Text("Seasons: ", color = Color.White,
                                        fontWeight = FontWeight.Medium, fontSize = 14.sp)
                                    val seasons =
                                        character.tvSeries.map {  // Qandil 2 : change it to map, it will remove extra commas and spaces.
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
                                        }.filter { it.isNotBlank() }.joinToString(", ")

                                    Text(seasons, color = Color.White, fontSize = 14.sp)
                                }
                            }
                            Spacer(modifier = Modifier.size(16.dp))
                        }
                    }
                }
            }
        }
    }
}

interface Service {
    @GET("/characters")
    suspend fun getCharacters(@Header("Authorization") token: String): Response<List<ApiCharacter>>
}