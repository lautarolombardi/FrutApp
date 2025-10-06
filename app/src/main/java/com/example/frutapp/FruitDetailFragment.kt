// FruitDetailFragment.kt

package com.example.frutapp

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment


class FruitDetailFragment : Fragment() {


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_fruit_detail, container, false)
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val bundle: Bundle? = arguments
        if (bundle != null) {

            val intentNameValue = bundle.getString("name")
            val intentFamilyValue = bundle.getString("family")
            val intentGenusValue = bundle?.getString("genus")
            val intentOrderValue = bundle?.getString("order")
            val intentCarbohydratesValue = bundle?.getDouble("carbohydrates")
            val intentProteinValue = bundle?.getDouble("protein")
            val intentFatValue = bundle?.getDouble("fat")
            val intentCaloriesValue = bundle?.getDouble("calories")
            val intentSugarValue = bundle?.getDouble("sugar")



            val fruitsIcon: ImageView = view.findViewById(R.id.ivFruitsIcon)
            fruitsIcon.setImageResource(R.drawable.frutas)

            val name: TextView = view.findViewById(R.id.tvNameValue)
            name.text = intentNameValue.toString()

            val family: TextView = view.findViewById(R.id.tvFamilyValue)
            family.text = intentFamilyValue.toString()

            val genus: TextView = view.findViewById(R.id.tvGenusValue)
            genus.text = intentGenusValue.toString()

            val order: TextView = view.findViewById(R.id.tvOrdenValue)
            order.text = intentOrderValue.toString()

            val carbohydrates: TextView = view.findViewById(R.id.tvCarbohydratesValue)
            carbohydrates.text = intentCarbohydratesValue.toString()

            val protein: TextView = view.findViewById(R.id.tvProteinValue)
            protein.text = intentProteinValue.toString()

            val fat: TextView = view.findViewById(R.id.tvFatValue)
            fat.text = intentFatValue.toString()

            val calories: TextView = view.findViewById(R.id.tvCaloriesValue)
            calories.text = intentCaloriesValue.toString()

            val sugar: TextView = view.findViewById(R.id.tvSugarValue)
            sugar.text = intentSugarValue.toString()
        }
    }
}
    