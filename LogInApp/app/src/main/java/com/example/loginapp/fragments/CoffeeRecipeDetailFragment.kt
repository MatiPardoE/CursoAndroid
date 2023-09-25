package com.example.loginapp.fragments

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import androidx.core.view.WindowCompat
import androidx.navigation.fragment.navArgs
import com.example.loginapp.R
import com.example.loginapp.database.AppDatabase
import com.example.loginapp.entities.CoffeeRecipe

class CoffeeRecipeDetailFragment : Fragment() {

    lateinit var v : View
    lateinit var txtNameCoffeeRecipe : EditText
    lateinit var textDescriptionCoffeeRecipe : EditText
    lateinit var textCoffeeType : EditText
    lateinit var textGrindLevel : EditText
    lateinit var seekBarStrength : SeekBar
    lateinit var seekBarRatio : SeekBar
    lateinit var edittextRatio : EditText
    lateinit var addButton : ImageView
    lateinit var discardButton : ImageView
    private val args : CoffeeRecipeDetailFragmentArgs by navArgs()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        v = inflater.inflate(R.layout.fragment_coffee_recipe_detail, container, false)

        txtNameCoffeeRecipe = v.findViewById(R.id.txtNameCoffeeRecipe)
        textDescriptionCoffeeRecipe = v.findViewById(R.id.textDescriptionCoffeeRecipe)
        textCoffeeType = v.findViewById(R.id.textCoffeeType)
        textDescriptionCoffeeRecipe = v.findViewById(R.id.textDescriptionCoffeeRecipe)
        textGrindLevel = v.findViewById(R.id.textGrindLevel)
        edittextRatio = v.findViewById(R.id.editTextRatio)
        seekBarStrength = v.findViewById(R.id.seekBarStrength)
        seekBarRatio = v.findViewById(R.id.seekBarRatio)
        addButton = v.findViewById(R.id.addButtonImage)
        discardButton = v.findViewById(R.id.discardButtonImage)

        return v
    }

    override fun onStart() {
        super.onStart()
        val idFromList = args.idCoffeeRecipe

        defineTypeOfDetail(idFromList)
    }

    override fun onResume() {
        super.onResume()

        edittextRatio.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

                // Verifica si el texto es un número válido
                val newText = s.toString()
                val number = newText.toIntOrNull()

                if (number != null) {
                    // Limita el número a estar entre 1 y 50
                    val limitNumber = when {
                        number < 1 -> 1
                        number > 25 -> 25
                        else -> number
                    }

                    // Actualiza el texto del EditText con el número limitado
                    if (number != limitNumber) {
                        edittextRatio.setText(limitNumber.toString())
                        edittextRatio.setSelection(edittextRatio.text.length) // Coloca el cursor al final
                    }
                    seekBarRatio.progress = edittextRatio.text.toString().toInt()
                }

            }

        })

        seekBarRatio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener{
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                //edittextRatio.setText(progress.toString())
            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    edittextRatio.setText(seekBar.progress.toString())
                }
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                if (seekBar != null) {
                    edittextRatio.setText(seekBar.progress.toString())
                }
            }
        })

        addButton.setOnClickListener{
            val dao = AppDatabase.getInstance(v.context)?.coffeeRecipeDao()
            val coffeeWaterRatio : Double

            coffeeWaterRatio = if(!edittextRatio.text.isEmpty()){
                (1 / edittextRatio.text.toString().toDouble())
            }else{
                ((1/10).toDouble())
            }


            val addCoffeeRecipeToDB = CoffeeRecipe(
                id = 0,
                name = txtNameCoffeeRecipe.text.toString(),
                description = textDescriptionCoffeeRecipe.text.toString(),
                coffeeType = textCoffeeType.text.toString(),
                grindLevel = textGrindLevel.text.toString(),
                coffeeToWaterRatio = coffeeWaterRatio,
                strength = seekBarStrength.progress
            )

            dao?.insertCoffeeRecipe(addCoffeeRecipeToDB)
            requireActivity().onBackPressed()
        }

        discardButton.setOnClickListener{
            requireActivity().onBackPressed()
        }
    }

    private fun defineTypeOfDetail(idFromList: Int) {
        val dao = AppDatabase.getInstance(v.context)?.coffeeRecipeDao()

        if (idFromList == ADD_NEW){
            unlockEdit()
        }else{
            lockEdit()
            val coffeeRecipeSelected = dao?.fetchCoffeeRecipeById(args.idCoffeeRecipe)
            if(coffeeRecipeSelected != null){
                txtNameCoffeeRecipe.setText(coffeeRecipeSelected.name)
                textDescriptionCoffeeRecipe.setText(coffeeRecipeSelected.description)
                textCoffeeType.setText(coffeeRecipeSelected.coffeeType)
                textGrindLevel.setText(coffeeRecipeSelected.grindLevel)
                seekBarStrength.progress = coffeeRecipeSelected.strength-1
                edittextRatio.setText((1.0 / coffeeRecipeSelected.coffeeToWaterRatio).toInt().toString())
                seekBarRatio.progress = (1.0 / coffeeRecipeSelected.coffeeToWaterRatio).toInt()
            }else{
                txtNameCoffeeRecipe.setText("404 NotFound")
            }
        }
    }

    private fun unlockEdit() {
        txtNameCoffeeRecipe.isEnabled = true
        textDescriptionCoffeeRecipe.isEnabled = true
        textCoffeeType.isEnabled = true
        textGrindLevel.isEnabled = true
        seekBarRatio.isEnabled = true
        seekBarStrength.isEnabled = true
        edittextRatio.isEnabled = true
        seekBarRatio.isClickable = true
        seekBarStrength.isClickable = true
        addButton.visibility = View.VISIBLE
        discardButton.visibility = View.VISIBLE
        txtNameCoffeeRecipe.hint = "Name"
        seekBarRatio.isEnabled = true
        seekBarStrength.isEnabled = true
    }

    private fun lockEdit() {
        txtNameCoffeeRecipe.isEnabled = false
        textDescriptionCoffeeRecipe.isEnabled = false
        textCoffeeType.isEnabled = false
        textGrindLevel.isEnabled = false
        seekBarRatio.isEnabled = false
        seekBarStrength.isEnabled = false
        edittextRatio.isEnabled = false
        addButton.visibility = View.GONE
        discardButton.visibility = View.GONE
        seekBarStrength.isClickable = false
        seekBarRatio.isClickable = false
        seekBarRatio.isEnabled = false
        seekBarStrength.isEnabled = false
    }

    companion object{
        const val ADD_NEW = -1
    }


}