package com.example.loginapp.adapters

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.loginapp.entities.CoffeeRecipe
import com.example.loginapp.R


class CoffeeRecipeAdapter(
    private val list: List<CoffeeRecipe>,
    private val onItemClick: (CoffeeRecipe) -> Unit
) :
    RecyclerView.Adapter<CoffeeRecipeAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        //Inflo a la vista, el layout item.
        val view = LayoutInflater.from(parent.context).inflate(R.layout.coffee_recipe_item,parent,false)
        return ViewHolder(view) //Retorno el viewHolder de esa lista
    }

    override fun getItemCount(): Int {
        return list.size //Aca le digo al adapter de que tamaño es la lista
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        //Uno la vista con la lista por eso llamo la funcion holder.bind
        val coffeeRecipe = list[position]
        holder.bind(coffeeRecipe)
        holder.setOnclickListener{
            onItemClick(coffeeRecipe)
        }
    }

    inner class ViewHolder(private val v: View) : RecyclerView.ViewHolder(v){
        fun bind(coffeeRecipe: CoffeeRecipe){
            //Aca es donde asocio a la vista las variables de mi objeto
            val txtName: TextView = v.findViewById(R.id.txtName)
            txtName.text = coffeeRecipe.name

            val imgStrength1 : ImageView = v.findViewById(R.id.imageViewGrano1)
            val imgStrength2 : ImageView = v.findViewById(R.id.imageViewGrano2)
            val imgStrength3 : ImageView = v.findViewById(R.id.imageViewGrano3)
            val imgStrength4 : ImageView = v.findViewById(R.id.imageViewGrano4)
            val imgStrength5 : ImageView = v.findViewById(R.id.imageViewGrano5)

            val imgStrengthlist = listOf(
                imgStrength1,
                imgStrength2,
                imgStrength3,
                imgStrength4,
                imgStrength5)

            if(coffeeRecipe.strength < 5) imgStrength5.setImageResource(R.drawable.granovacio)
            if (coffeeRecipe.strength < 4) imgStrength4.setImageResource(R.drawable.granovacio)
            if (coffeeRecipe.strength < 3) imgStrength3.setImageResource(R.drawable.granovacio)
            if (coffeeRecipe.strength < 2) imgStrength2.setImageResource(R.drawable.granovacio)
            if (coffeeRecipe.strength < 1) imgStrength1.setImageResource(R.drawable.granovacio)
        }

        fun setOnclickListener(onClick: ()->Unit){
            v.setOnClickListener{
                onClick()
            }
        }
    }
}