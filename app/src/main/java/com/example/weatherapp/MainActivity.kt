package com.example.weatherapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.SearchView
import com.example.weatherapp.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    //f81ca99f3efdbe3840f26052107b50c8
    private lateinit var binding:ActivityMainBinding
    private val onFailure="Error"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding=ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        fetchWeatherData("raipur")
        searchCity()
    }

    private fun searchCity() {
        val searchView=binding.searchView
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null) {
                    fetchWeatherData(query)
                }
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }

        })
    }

    private fun fetchWeatherData(cityName:String) {
        val retrofit = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl("https://api.openweathermap.org/data/2.5/")
            .build().create(ApiInterface::class.java)
        val response=retrofit.getWeatherData(cityName,"f81ca99f3efdbe3840f26052107b50c8","metric")
        response.enqueue(object : Callback<WeatherApp>{
            override fun onResponse(call: Call<WeatherApp>, response: Response<WeatherApp>) {
                val responseBody=response.body()
                if(response.isSuccessful && responseBody!=null){
                    val temperature= responseBody.main.temp
                    val minTemp=responseBody.main.temp_min
                    val maxTemp=responseBody.main.temp_max
                    val condition=responseBody.weather.firstOrNull()?.main?:"unknown"
                    binding.temperature.text="$temperature °C"
                    binding.maxtemp.text="Max Temp: $maxTemp °C"
                    binding.mintemp.text="Min Temp: $minTemp °C"
                    binding.condition.text=condition
                    binding.cityname.text=cityName.replaceFirstChar { cityName[0].uppercase() }
                    changeImage(condition)
                }
            }

            override fun onFailure(call: Call<WeatherApp>, t: Throwable) {
                binding.cityname.text=onFailure
            }

        })
    }

    private fun changeImage(conditions:String) {
        when(conditions){
            "Partly Clouds","Clouds","Overcast","Mist","Foggy"->{
                binding.lottieAnimationView.setAnimation(R.raw.cloudy)
            }
            "Clear Sky","Sunny","Clear"->{
                binding.lottieAnimationView.setAnimation(R.raw.sunny)
            }
            "Light Rain","Drizzle","Moderate Rain","Showers","Heavy Rain"->{
                binding.lottieAnimationView.setAnimation(R.raw.rain)
            }
            "Light Snow","Moderate Snow","Heavy Snow","Blizzard"->{
                binding.lottieAnimationView.setAnimation(R.raw.snow)
            }
            else->{
                binding.lottieAnimationView.setAnimation(R.raw.sunny)
            }
        }
        binding.lottieAnimationView.playAnimation()
    }
}