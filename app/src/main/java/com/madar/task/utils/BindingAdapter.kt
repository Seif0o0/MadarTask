package com.madar.task.utils

import android.view.View
import androidx.databinding.BindingAdapter
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.google.android.material.textview.MaterialTextView
import com.madar.task.R


@BindingAdapter("input_text")
fun MaterialTextView.setErrorTextVisibility(inputText: String) {
    if (inputText.isNotEmpty()) visibility = View.GONE
}

@BindingAdapter("error_message")
fun MaterialTextView.setErrorMessage(errorMessage: String) {
    if (errorMessage.isNotEmpty()) {
        visibility = View.VISIBLE
        text = errorMessage
    } else {
        visibility = View.GONE

    }
}

@BindingAdapter("loading_status")
fun LottieAnimationView.setLoadingStatus(isLoading: Boolean) {
    if (isLoading) {
        visibility = View.VISIBLE
        setAnimation("progress_bar.json")
        playAnimation()
        repeatCount = LottieDrawable.INFINITE
    } else {
        visibility = View.GONE
        cancelAnimation()
    }
}


@BindingAdapter("age", "gender")
fun MaterialTextView.setAgeAndGender(age: Int, gender: Int) {
    val genderString = if (gender == 0) context.getString(R.string.male)
    else context.getString(R.string.female)

    val ageString = if (age == 1) context.getString(
        R.string.one_year,
        age
    ) else context.getString(R.string.more_than_one_year, age)

    text = "$ageString $genderString"
}