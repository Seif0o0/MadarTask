package com.madar.task.utils

import android.content.Context
import com.afollestad.materialdialogs.DialogBehavior
import com.afollestad.materialdialogs.MaterialDialog
import com.afollestad.materialdialogs.ModalDialog
import com.afollestad.materialdialogs.customview.customView
import com.afollestad.materialdialogs.customview.getCustomView
import com.airbnb.lottie.LottieDrawable
import com.madar.task.R
import com.madar.task.databinding.ErrorDialogLayoutBinding
import com.madar.task.databinding.SuccessDialogLayoutBinding

object CustomDialog {
    fun showErrorDialog(
        dialogBehavior: DialogBehavior = ModalDialog, context: Context, errorMessage: String
    ) {
        val dialog = MaterialDialog(context, dialogBehavior).show {
            cornerRadius(res = com.intuit.sdp.R.dimen._8sdp)
            cancelable(false)
            customView(
                R.layout.error_dialog_layout, noVerticalPadding = true, dialogWrapContent = true
            )
        }

        val binding = ErrorDialogLayoutBinding.bind(dialog.getCustomView())
        binding.errorMessage.text = errorMessage

        binding.errorAnimation.setAnimation("error_dialog_animation.json")
        binding.errorAnimation.playAnimation()
        binding.errorAnimation.repeatCount = LottieDrawable.INFINITE

        binding.dismissText.setOnClickListener {
            binding.errorAnimation.cancelAnimation()
            dialog.dismiss()
        }

    }

    fun showSuccessDialog(
        dialogBehavior: DialogBehavior = ModalDialog,
        context: Context,
        successMessage: String,
        btnText: String? = null,
        onBtnClicked: (() -> Unit) = {}
    ) {

        val dialog = MaterialDialog(context, dialogBehavior).show {
            cornerRadius(res = com.intuit.sdp.R.dimen._8sdp)
            cancelable(false)
            customView(
                R.layout.success_dialog_layout, noVerticalPadding = true, dialogWrapContent = true
            )
        }

        val binding = SuccessDialogLayoutBinding.bind(dialog.getCustomView())
        binding.successMessage.text = successMessage

        binding.errorAnimation.setAnimation("success.json")
        binding.errorAnimation.playAnimation()
        binding.errorAnimation.repeatCount = LottieDrawable.INFINITE

        btnText?.let {
            binding.okButton.text = it
        }

        binding.okButton.setOnClickListener {
            onBtnClicked().also {
                binding.errorAnimation.cancelAnimation()
                dialog.dismiss()
            }
        }
    }
}