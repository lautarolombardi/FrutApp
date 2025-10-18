package com.example.frutapp

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment

class FruitListFragment : Fragment() {

    interface OnUpdateClickListener {
        fun onUpdateClicked()
    }

    private var listener: OnUpdateClickListener? = null

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnUpdateClickListener) {
            listener = context
        }
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fruit_list_fragment, container, false)
        val btnActualizar = view.findViewById<Button>(R.id.btnActualizar)

        btnActualizar.setOnClickListener {
            listener?.onUpdateClicked()
        }

        return view
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }
}