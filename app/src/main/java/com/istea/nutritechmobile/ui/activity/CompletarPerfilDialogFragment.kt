package com.istea.nutritechmobile.ui.activity

import android.app.AlertDialog
import android.app.Dialog
import android.content.Context
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.DialogFragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import com.istea.nutritechmobile.R

class CompletarPerfilDialogFragment : DialogFragment() {

    private lateinit var listener: ICompletarPerfilListener

    companion object {
        const val FRAGMENT_ID = "CompletarPerfilDialogFragment"
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        context?.let {
            val alertBuilder = MaterialAlertDialogBuilder(it)

            activity?.layoutInflater?.inflate(R.layout.completar_perfil_dialog, null)?.let {

                alertBuilder.apply {
                    setView(it)
                    setTitle(R.string.disclaimer_title)
                    setMessage(R.string.disclaimer_info)
                    setPositiveButton(R.string.disclaimer_btn_later) { _, _ ->
                        listener.onNegativeClick()
                    }
                    setNegativeButton(R.string.disclaimer_btn_ok) { _, _ ->
                        listener.onPositiveClick()
                    }
                        .setCancelable(false)
                }

            }

            return alertBuilder.create()
        }

        return super.onCreateDialog(savedInstanceState)
    }

    override fun onAttach(context: Context) {
        try {
            listener = context as ICompletarPerfilListener
        } catch (e: Exception) {
            Log.d(FRAGMENT_ID, "Must implement ICompletarPerfilListener because ${e.message}")
        }
        super.onAttach(context)
    }
}


interface ICompletarPerfilListener {
    fun onPositiveClick()
    fun onNegativeClick()
}
