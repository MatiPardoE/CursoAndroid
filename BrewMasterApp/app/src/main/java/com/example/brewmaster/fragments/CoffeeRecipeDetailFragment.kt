package com.example.brewmaster.fragments

import android.Manifest
import android.app.AlertDialog
import android.content.ContentValues.TAG
import android.app.Activity
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.Spinner
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.NavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.preference.PreferenceManager
import com.bumptech.glide.Glide
import com.example.brewmaster.R
import com.example.brewmaster.entities.CoffeeBean
import com.example.brewmaster.entities.CoffeeRecipe
import com.google.firebase.storage.FirebaseStorage
import com.google.firebase.storage.StorageReference
import com.google.mlkit.vision.barcode.common.Barcode
import com.google.mlkit.vision.codescanner.GmsBarcodeScannerOptions
import com.google.mlkit.vision.codescanner.GmsBarcodeScanning
import pl.droidsonroids.gif.GifImageView
import java.io.ByteArrayOutputStream

class CoffeeRecipeDetailFragment : Fragment() {

    private lateinit var v: View

    private val REQUEST_PERMISSIONS = 1
    private val REQUEST_IMAGE_CAPTURE = 2
    private val storage = FirebaseStorage.getInstance()
    private val storageReference: StorageReference = storage.reference

    private lateinit var spinnerBean: Spinner
    private lateinit var textBrand: TextView
    private lateinit var textOrigin: TextView
    private lateinit var textVariety: TextView
    private lateinit var textScore: TextView
    private lateinit var imageBeans: ImageView
    private lateinit var btnBarCode: ImageView
    private lateinit var txtNameCoffeeRecipe: EditText
    private lateinit var textDescriptionCoffeeRecipe: EditText
    private lateinit var textCoffeeType: EditText
    private lateinit var textGrindLevel: EditText
    private lateinit var seekBarStrength: SeekBar
    lateinit var seekBarRatio: SeekBar
    lateinit var edittextRatio: EditText
    private lateinit var addButton: ImageView
    private lateinit var discardButton: ImageView
    private lateinit var deleteButton: ImageView
    private lateinit var editButton: ImageView
    private lateinit var loadingGIF: GifImageView

    private var coffeeBeans: MutableList<CoffeeBean> = mutableListOf()
    private var maxRatio: Int = 25

    private var idFromList: String = ""

    private val args: CoffeeRecipeDetailFragmentArgs by navArgs()
    private lateinit var viewModel: CoffeeRecipeDetailViewModel
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
        loadingGIF = v.findViewById(R.id.loadingGIF)
        spinnerBean = v.findViewById(R.id.spinnerProducts)
        textBrand = v.findViewById(R.id.brand)
        imageBeans = v.findViewById(R.id.imageBeans)
        textScore = v.findViewById(R.id.score)
        textVariety = v.findViewById(R.id.variety)
        textOrigin = v.findViewById(R.id.origin)
        btnBarCode = v.findViewById(R.id.barcodebtn)

        idFromList = args.idCoffeeRecipe

        viewModel = ViewModelProvider(requireActivity())[CoffeeRecipeDetailViewModel::class.java]

        viewModel.progressView.observe(viewLifecycleOwner, Observer { progress ->
            if (progress) {
                loadingGIF.visibility = View.VISIBLE
            } else {
                loadingGIF.visibility = View.GONE
            }
        })

        viewModel.coffeeRecipe.observe(viewLifecycleOwner, Observer { coffeeRecipeSelected ->
            if (idFromList == coffeeRecipeSelected.id) {
                fillTextView(coffeeRecipeSelected)
            }
        })

        viewModel.coffeeBeans.observe(viewLifecycleOwner, Observer { coffeeBeans ->
            fillCoffeeBeans(coffeeBeans)
        })

        viewModel.newCoffeeRecipeFlag.observe(viewLifecycleOwner, Observer { newOk ->
            if (newOk) {
                Toast.makeText(requireContext(), "Recipe save", Toast.LENGTH_SHORT).show()
                findNavController().popBackStack()
            }
        })

        viewModel.updateCoffeeRecipeFlag.observe(viewLifecycleOwner, Observer { updateOk ->
            if (updateOk) {
                Toast.makeText(requireContext(), "Recipe updated successfully", Toast.LENGTH_SHORT)
                    .show()
                findNavController().popBackStack()
            }
        })

        viewModel.updateCoffeeBeanImageFlag.observe(viewLifecycleOwner, Observer { updateOk ->
            if (updateOk) {
                Toast.makeText(requireContext(), "Image update Success", Toast.LENGTH_SHORT)
                    .show()
            }
        })

        viewModel.deletedCoffeeRecipeFlag.observe(viewLifecycleOwner, Observer { deleteOk ->
            if (deleteOk) {
                Toast.makeText(requireContext(), "Recipe deleted successfully", Toast.LENGTH_SHORT)
                    .show()
                findNavController().popBackStack()
            }
        })

        if (idFromList == ADD_NEW) {
            unlockEdit()
            viewModel.getCoffeeBeans()
        } else {
            lockEdit()
            viewModel.getCoffeeRecipeByID(idFromList)
        }

        return v
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onResume() {
        super.onResume()
        edittextRatio.addTextChangedListener(object : TextWatcher {
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

        seekBarRatio.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
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

        addButton.setOnClickListener {

            val coffeeWaterRatio: Double = if (!edittextRatio.text.isEmpty()) {
                (1 / edittextRatio.text.toString().toDouble())
            } else {
                ((1 / 10).toDouble())
            }

            if (idFromList == ADD_NEW) {
                val addCoffeeRecipeToDB = CoffeeRecipe(
                    name = txtNameCoffeeRecipe.text.toString(),
                    description = textDescriptionCoffeeRecipe.text.toString(),
                    coffeeType = textCoffeeType.text.toString(),
                    grindLevel = textGrindLevel.text.toString(),
                    coffeeToWaterRatio = coffeeWaterRatio,
                    strength = seekBarStrength.progress + 1,
                    barcodeCoffeeBean = coffeeBeans[spinnerBean.selectedItemPosition].EAN13
                )

                viewModel.addCoffeeRecipe(addCoffeeRecipeToDB)
            } else {
                val editCoffeeRecipeToDB = CoffeeRecipe(
                    id = idFromList,
                    name = txtNameCoffeeRecipe.text.toString(),
                    description = textDescriptionCoffeeRecipe.text.toString(),
                    coffeeType = textCoffeeType.text.toString(),
                    grindLevel = textGrindLevel.text.toString(),
                    coffeeToWaterRatio = coffeeWaterRatio,
                    strength = seekBarStrength.progress + 1,
                    barcodeCoffeeBean = coffeeBeans[spinnerBean.selectedItemPosition].EAN13
                )

                viewModel.updateCoffeeRecipe(editCoffeeRecipeToDB)
            }

        }

        discardButton.setOnClickListener {
            findNavController().popBackStack()
        }

        editButton.setOnClickListener {
            unlockEdit()
        }

        deleteButton.setOnClickListener {
            showDeleteConfirmationDialog()
        }

        btnBarCode.setOnClickListener {
            val options = GmsBarcodeScannerOptions.Builder()
                .setBarcodeFormats(Barcode.FORMAT_EAN_13)
                .enableAutoZoom() // available on 16.1.0 and higher
                .build()

            val scanner = GmsBarcodeScanning.getClient(requireContext())

            scanner.startScan()
                .addOnSuccessListener { barcode ->
                    matchCoffeeBean(barcode.rawValue)
                    // Task completed successfully
                }
                .addOnCanceledListener {
                    // Task canceled
                }
                .addOnFailureListener { e ->
                    // Task failed with an exception
                    Log.d(TAG, "Error Barcode: $e")
                }
        }

        imageBeans.setOnClickListener {
            if (ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.CAMERA
                ) != PackageManager.PERMISSION_GRANTED
            ) {
                // El permiso no está concedido, solicitarlo
                ActivityCompat.requestPermissions(
                    requireActivity(),
                    arrayOf(Manifest.permission.CAMERA),
                    REQUEST_PERMISSIONS
                )
            } else {
                // El permiso está concedido, abrir la cámara
                dispatchTakePictureIntent()
            }
        }


    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == Activity.RESULT_OK) {
            viewModel.enableLoading()
            val imageBitmap = data?.extras?.get("data") as Bitmap
            // Aquí puedes hacer algo con la imagen capturada (por ejemplo, mostrarla en una ImageView)
            imageBeans.setImageBitmap(imageBitmap)
            uploadImageToFirebase(imageBitmap)
        }
    }

    private fun uploadImageToFirebase(bitmap: Bitmap) {
        val baos = ByteArrayOutputStream()
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos)
        val data = baos.toByteArray()

        // Genera un nombre único para la imagen (puedes personalizar esto según tus necesidades)
        val imageName = "${coffeeBeans[spinnerBean.selectedItemPosition].EAN13}.jpg"

        val imagesRef: StorageReference = storageReference.child("images/$imageName")

        // Sube la imagen a Firebase Storage
        val uploadTask = imagesRef.putBytes(data)
        uploadTask.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                // La imagen se subió exitosamente
                // Puedes obtener la URL de la imagen si es necesario
                imagesRef.downloadUrl.addOnSuccessListener { uri ->
                    val imageUrl = uri.toString()
                    viewModel.updateCoffeeBeanImage(coffeeBeans[spinnerBean.selectedItemPosition].EAN13,imageUrl)
                    // Aquí puedes hacer algo con la URL de la imagen
                }
            } else {
                // Error al subir la imagen
                // Puedes manejar el error según tus necesidades
            }
        }
    }

    private fun dispatchTakePictureIntent() {


        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(requireActivity().packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        }
    }

    private fun hasCameraPermission(): Boolean {
        return true
    }

    private fun matchCoffeeBean(value: String?) {
        val matchingIndex = coffeeBeans.indexOfFirst {
            it.EAN13 == value
        }

        if (matchingIndex != -1){
            spinnerBean.setSelection(matchingIndex)
        }

    }

    private fun showDeleteConfirmationDialog() {
        val builder = AlertDialog.Builder(requireContext())

        builder.setTitle("Confirm Deletion")
        builder.setMessage("Are you sure you want to delete this recipe?")

        // Confirm button
        builder.setPositiveButton("Yes") { dialog, which ->
            // Get the ID and call the delete function
            viewModel.deleteCoffeeRecipe(idFromList)

        }

        // Cancel button
        builder.setNegativeButton("No") { dialog, which ->
            // Do nothing or handle as needed
        }

        // Show the dialog
        builder.show()
    }

    private fun fillTextView(coffeeRecipeSelected: CoffeeRecipe) {
        if (coffeeRecipeSelected != null) {
            txtNameCoffeeRecipe.setText(coffeeRecipeSelected.name)
            textDescriptionCoffeeRecipe.setText(coffeeRecipeSelected.description)
            textCoffeeType.setText(coffeeRecipeSelected.coffeeType)
            textGrindLevel.setText(coffeeRecipeSelected.grindLevel)
            seekBarStrength.progress = coffeeRecipeSelected.strength - 1


            if (maxRatio <= (1.0 / coffeeRecipeSelected.coffeeToWaterRatio).toInt()) {
                edittextRatio.setText("25")
                seekBarRatio.progress = 25
            } else {
                edittextRatio.setText(
                    (1.0 / coffeeRecipeSelected.coffeeToWaterRatio).toInt().toString()
                )
                seekBarRatio.progress = (1.0 / coffeeRecipeSelected.coffeeToWaterRatio).toInt()
            }

            if (coffeeBeans.isNotEmpty()) {
                // Encontrar el índice del CoffeeBean asociado al CoffeeRecipe
                val matchingIndex = coffeeBeans.indexOfFirst {
                    it.EAN13 == coffeeRecipeSelected.barcodeCoffeeBean
                }
                // Seleccionar el índice correspondiente en el Spinner
                if (matchingIndex != -1) {
                    spinnerBean.setSelection(matchingIndex)
                }
                textBrand.text = coffeeBeans[matchingIndex].Brand
                textOrigin.text = coffeeBeans[matchingIndex].Origin
                textVariety.text = coffeeBeans[matchingIndex].Variety
                textScore.text = coffeeBeans[matchingIndex].Score

                val imageUrl = coffeeBeans[matchingIndex].Picture

                // Cargar la imagen en el ImageView usando Glide
                Glide.with(requireContext())
                    .load(imageUrl)
                    .centerCrop()
                    .into(imageBeans)
            }

        } else {
            txtNameCoffeeRecipe.setText("404 NotFound")
        }
    }

    private fun fillCoffeeBeans(coffeeBeansDB: MutableList<CoffeeBean>) {
        coffeeBeans = coffeeBeansDB

        // Crear un ArrayAdapter utilizando la lista de CoffeeBean y el atributo name
        val adapter = ArrayAdapter(
            requireContext(),
            android.R.layout.simple_spinner_item,
            coffeeBeans.map { it.Name })

        // Establecer el estilo del drop-down
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)

        // Establecer el adaptador en el Spinner
        spinnerBean.adapter = adapter

        spinnerBean.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(
                parentView: AdapterView<*>?,
                selectedItemView: View?,
                position: Int,
                id: Long
            ) {
                // Aquí manejas la selección del ítem
                val selectedCoffeeBean = coffeeBeans[position]

                // Actualiza los TextView con la información deseada
                textBrand.text = selectedCoffeeBean.Brand
                textOrigin.text = selectedCoffeeBean.Origin
                textScore.text = selectedCoffeeBean.Score
                textVariety.text = selectedCoffeeBean.Variety

                val imageUrl = coffeeBeans[position].Picture

                // Cargar la imagen en el ImageView usando Glide
                Glide.with(requireContext())
                    .load(imageUrl)
                    .centerCrop()
                    .into(imageBeans)

            }
            override fun onNothingSelected(parentView: AdapterView<*>?) {
                // Maneja la situación en la que no se selecciona ningún ítem (opcional)
            }
        }
    }

    private fun unlockEdit() {
        viewModel.disableLoading()
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
        spinnerBean.isEnabled = true // Deshabilitar el Spinner
        spinnerBean.isClickable = true
        imageBeans.isClickable = true
        btnBarCode.visibility = View.VISIBLE
        if (idFromList != ADD_NEW) {
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
        spinnerBean.isEnabled = false // Deshabilitar el Spinner
        spinnerBean.isClickable = false
        imageBeans.isClickable = false
        //Lock siempre se usa con entradas ya existentes
        btnBarCode.visibility = View.INVISIBLE
        editButton.visibility = View.VISIBLE
    }



    companion object {
        const val ADD_NEW = "NEW"
    }


}