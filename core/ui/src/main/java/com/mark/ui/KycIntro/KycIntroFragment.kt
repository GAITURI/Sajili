package com.mark.ui.KycIntro

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.compose.ui.platform.ComposeView
import androidx.fragment.app.Fragment

class KycIntroFragment:Fragment (){
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        return ComposeView(requireContext()).apply{
            setContent {
                KycIntroScreen(
                    onBackClick={
                        parentFragment.popBackStack()
                    },
                    onBeginClick={

                    }
                )
            }
        }
    }

}