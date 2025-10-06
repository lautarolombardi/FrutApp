// Archivo: FruitAdapter.kt

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frutapp.OnFruitClickListener
import com.example.frutapp.R
import com.example.frutapp.data.model.FruitDTO

// El constructor ahora pide la lista y un "listener" (el notificador)
class FruitAdapter(
  private var fruitList: MutableList<FruitDTO>,
  private val listener: OnFruitClickListener
) : RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val fruitsIcon: ImageView = view.findViewById(R.id.ivFruitsIcon)
    val name: TextView = view.findViewById(R.id.tvName)
  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.activity_fruit_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val fruit = fruitList[position]
    holder.fruitsIcon.setImageResource(R.drawable.frutas)
    holder.name.text = fruit.name

    // ¡Este es el gran cambio!
    // En lugar de crear un Intent, ahora crea un Bundle y llama al listener.
    holder.itemView.setOnClickListener {
      val bundle = Bundle().apply {
        putString("name", fruit.name)
        putString("family", fruit.family)
        putString("order", fruit.order)
        putString("genus", fruit.genus)
        putDouble("calories", fruit.nutritions.calories.toDouble()) // Convertimos a Double por seguridad
        putDouble("fat", fruit.nutritions.fat)
        putDouble("sugar", fruit.nutritions.sugar)
        putDouble("carbohydrates", fruit.nutritions.carbohydrates)
        putDouble("protein", fruit.nutritions.protein)
      }
      // Avisa a la Activity que se hizo clic y le pasa los datos
      listener.onFruitClick(bundle)
    }
  }

  override fun getItemCount() = fruitList.size

  fun updateData(newFruits: List<FruitDTO>) {
    fruitList.clear()
    fruitList.addAll(newFruits)
    notifyDataSetChanged()
  }
}
