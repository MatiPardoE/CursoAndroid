package com.example.brewmaster.fragments

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
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.example.brewmaster.R
import com.example.brewmaster.database.AppDatabase
import com.example.brewmaster.entities.CoffeeRecipe

class CoffeeRecipeDetailFragment : Fragment() {

    private lateinit var v : View
    private lateinit var txtNameCoffeeRecipe : EditText
    private lateinit var textDescriptionCoffeeRecipe : EditText
    private lateinit var textCoffeeType : EditText
    private lateinit var textGrindLevel : EditText
    private lateinit var seekBarStrength : SeekBar
    lateinit var seekBarRatio : SeekBar
    lateinit var edittextRatio : EditText
    private lateinit var addButton : ImageView
    private lateinit var discardButton : ImageView
    private lateinit var deleteButton : ImageView
    private lateinit var editButton : ImageView
    private var maxRatio : Int = 25
    private var idFromList : Int = 0
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
        editButton = v.findViewById(R.id.editButtonImage)
        discardButton = v.findViewById(R.id.discardButtonImage)
        deleteButton = v.findViewById(R.id.deleteButtonImage)

        return v
    }

    override fun onStart() {
        super.onStart()

        val prefs = PreferenceManager.getDefaultSharedPreferences(requireContext())
        maxRatio = prefs.getString("maxRatio","25")!!.toIntOrNull() ?: 25
        seekBarRatio.max = maxRatio
        idFromList = args.idCoffeeRecipe
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
                        number > maxRatio -> maxRatio
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

            if(idFromList == ADD_NEW){
                val addCoffeeRecipeToDB = CoffeeRecipe(
                    id = 0,
                    name = txtNameCoffeeRecipe.text.toString(),
                    description = textDescriptionCoffeeRecipe.text.toString(),
                    coffeeType = textCoffeeType.text.toString(),
                    grindLevel = textGrindLevel.text.toString(),
                    coffeeToWaterRatio = coffeeWaterRatio,
                    strength = seekBarStrength.progress + 1
                )

                dao?.insertCoffeeRecipe(addCoffeeRecipeToDB)

                findNavController().popBackStack()
            }else{
                val editCoffeeRecipeToDB = CoffeeRecipe(
                    id = idFromList,
                    name = txtNameCoffeeRecipe.text.toString(),
                    description = textDescriptionCoffeeRecipe.text.toString(),
                    coffeeType = textCoffeeType.text.toString(),
                    grindLevel = textGrindLevel.text.toString(),
                    coffeeToWaterRatio = coffeeWaterRatio,
                    strength = seekBarStrength.progress + 1
                )

                dao?.updateCoffeeRecipe(editCoffeeRecipeToDB)
                findNavController().popBackStack()
            }

        }

        discardButton.setOnClickListener{
            findNavController().popBackStack()
        }

        editButton.setOnClickListener{
            unlockEdit()
        }

        deleteButton.setOnClickListener {
            val dao = AppDatabase.getInstance(v.context)?.coffeeRecipeDao()
            val deleteCoffeeRecipeToDB = CoffeeRecipe(
                id = idFromList,
                name = txtNameCoffeeRecipe.text.toString(),
                description = textDescriptionCoffeeRecipe.text.toString(),
                coffeeType = textCoffeeType.text.toString(),
                grindLevel = textGrindLevel.text.toString(),
                coffeeToWaterRatio = 1.0/10,
                strength = seekBarStrength.progress + 1
            )
            dao?.delete(deleteCoffeeRecipeToDB)
            findNavController().popBackStack()
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
                if(maxRatio <= (1.0 / coffeeRecipeSelected.coffeeToWaterRatio).toInt()){
                    edittextRatio.setText("25")
                    seekBarRatio.progress = 25
                }else{
                    edittextRatio.setText((1.0 / coffeeRecipeSelected.coffeeToWaterRatio).toInt().toString())
                    seekBarRatio.progress = (1.0 / coffeeRecipeSelected.coffeeToWaterRatio).toInt()
                }

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
        seekBarRatio.isEnabled = true
        seekBarStrength.isEnabled = true
        addButton.visibility = View.VISIBLE  //#TODO separar cunado edita que hace
        discardButton.visibility = View.VISIBLE
        editButton.visibility = View.INVISIBLE
        deleteButton.visibility = View.INVISIBLE
        if(idFromList != ADD_NEW){
            //Only edit
            deleteButton.visibility = View.VISIBLE
        }
    }

    private fun lockEdit() {
        txtNameCoffeeRecipe.isEnabled = false
        textDescriptionCoffeeRecipe.isEnabled = false
        textCoffeeType.isEnabled = false
        textGrindLevel.isEnabled = false
        seekBarRatio.isEnabled = false
        seekBarStrength.isEnabled = false
        edittextRatio.isEnabled = false
        addButton.visibility = View.INVISIBLE
        discardButton.visibility = View.INVISIBLE
        seekBarStrength.isClickable = false
        seekBarRatio.isClickable = false
        seekBarRatio.isEnabled = false
        seekBarStrength.isEnabled = false
        deleteButton.visibility = View.INVISIBLE
        //Lock siempre se usa con entradas ya existentes
        editButton.visibility = View.VISIBLE
    }

    companion object{
        const val ADD_NEW = -1
    }


}