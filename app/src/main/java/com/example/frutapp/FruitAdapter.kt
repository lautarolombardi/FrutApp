import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.frutapp.Fruit
import com.example.frutapp.FruitDetail
import com.example.frutapp.R

class FruitAdapter(private val list: List<Fruit>) :
  RecyclerView.Adapter<FruitAdapter.ViewHolder>() {

  class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
    val name: TextView = view.findViewById(R.id.tvName)
    val emoji: TextView = view.findViewById(R.id.tvEmoji)
    val id: TextView = view.findViewById(R.id.tvId)
    val family: TextView = view.findViewById(R.id.tvFamily)
    val order: TextView = view.findViewById(R.id.tvOrder)
    val genus: TextView = view.findViewById(R.id.tvGenus)

  }

  override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
    val view = LayoutInflater.from(parent.context)
      .inflate(R.layout.activity_fruit_item, parent, false)
    return ViewHolder(view)
  }

  override fun onBindViewHolder(holder: ViewHolder, position: Int) {
    val fruit = list[position]
    holder.emoji.text = fruit.emoji
    holder.name.text = fruit.name
    holder.id.text = fruit.id.toString()
    holder.family.text = fruit.family
    holder.order.text = fruit.order
    holder.genus.text = fruit.genus

    holder.itemView.setOnClickListener {
      val context = holder.itemView.context
      val intent = Intent(context, FruitDetail::class.java)
      intent.putExtra("id", fruit.id)
      intent.putExtra("name", fruit.name)
      intent.putExtra("family", fruit.family)
      intent.putExtra("order", fruit.order)
      intent.putExtra("genus", fruit.genus)
      intent.putExtra("emoji", fruit.emoji)
      intent.putExtra("sugar", fruit.nutritions.sugar)
      intent.putExtra("protein", fruit.nutritions.protein)
      intent.putExtra("carbohydrates", fruit.nutritions.carbohydrates)
      intent.putExtra("calories", fruit.nutritions.fat)
      context.startActivity(intent)
    }

  }

  override fun getItemCount() = list.size
}