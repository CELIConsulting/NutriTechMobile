package com.istea.nutritechmobile.presenter

import com.istea.nutritechmobile.model.interfaces.ICargaDiariaRepository
import com.istea.nutritechmobile.presenter.interfaces.ICargaDiariaPresenter
import com.istea.nutritechmobile.ui.interfaces.ICargaDiariaView
import androidx.core.app.ActivityCompat.startActivityForResult

import android.provider.MediaStore

import android.content.Intent
import androidx.core.app.ActivityCompat


class CargaDiariaPresenterImp(
    private val view: ICargaDiariaView,
    private val repo: ICargaDiariaRepository,
) :
    ICargaDiariaPresenter {
}