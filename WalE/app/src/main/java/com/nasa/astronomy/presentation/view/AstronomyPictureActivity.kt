package com.nasa.astronomy.presentation.view

import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.ComponentActivity
import androidx.lifecycle.ViewModelProvider
import com.nasa.astronomy.R
import com.nasa.astronomy.db.database.AppDatabase
import com.nasa.astronomy.network.APIClient
import com.nasa.astronomy.presentation.viewmodel.AstronomyPictureViewModel
import com.nasa.astronomy.presentation.viewmodel.AstronomyPictureViewModelFactory
import com.nasa.astronomy.utils.AppConstants
import com.squareup.picasso.Picasso

class AstronomyPictureActivity : ComponentActivity() {

    private lateinit var viewModel: AstronomyPictureViewModel
    private lateinit var ivPicture: ImageView
    private lateinit var tvTitle: TextView
    private lateinit var tvExplanation: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.astronomy_picture_layout)

        initViews()
        initViewModel()
    }

    private fun initViews() {
        ivPicture = findViewById(R.id.iv_picture)
        tvTitle = findViewById(R.id.tv_title)
        tvExplanation = findViewById(R.id.tv_explanation)
    }

    private fun initViewModel() {
        val pictureDao = AppDatabase.getDatabase(applicationContext).astronomyPictureDao()
        val factory = AstronomyPictureViewModelFactory(APIClient.apiService, pictureDao)
        viewModel = ViewModelProvider(this, factory)[AstronomyPictureViewModel::class.java]
        viewModel.pictureData.observe(this) { pictureData ->
            pictureData?.let {
                tvTitle.text = it.title
                tvExplanation.text = it.explanation
                Picasso.get().load(it.url).into(ivPicture)
            } ?: run {
                tvTitle.text = resources.getString(R.string.error_message)
                tvExplanation.text = ""
                ivPicture.setImageResource(R.drawable.placeholder)
            }
        }

        viewModel.fetchAstronomyPicture(AppConstants.NASA_APOD_API)
    }

}
