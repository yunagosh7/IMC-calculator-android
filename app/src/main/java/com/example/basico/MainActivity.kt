package com.example.basico

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.cardview.widget.CardView
import com.example.basico.databinding.ActivityMainBinding
import com.google.android.material.floatingactionbutton.FloatingActionButton
import com.google.android.material.slider.RangeSlider
import java.math.RoundingMode
import java.text.DecimalFormat

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    private var isMaleSelected: Boolean = true
    private var currentWeight: Int = 70
    private var currentgAge: Int = 25
    private var currentHeight: Int = 120

    // Creas las referencias a las vistas
    private lateinit var cardMale: CardView
    private lateinit var cardFemale: CardView
    private lateinit var tvHeight: TextView
    private lateinit var rsHeight: RangeSlider
    private lateinit var tvWeight: TextView
    private lateinit var fabWeightAdd: FloatingActionButton
    private lateinit var fabWeightSubtract: FloatingActionButton
    private lateinit var tvAge: TextView
    private lateinit var fabAgeAdd: FloatingActionButton
    private lateinit var fabAgeSubtract: FloatingActionButton
    private lateinit var btnCalculate: Button


    companion object {
        // Propeidad estatica que se usa para pasar un dato en el intent
        const val IMC_KEY: String = "IMC_RESULT"
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        initComponents()
        initListeners()
        initUI()

    }

    // Asigna la referencia al elemento de la vista una vez inflado el layout
    private fun initComponents() {
        cardMale = binding.cardMale
        cardFemale = binding.cardFemale
        tvHeight = binding.tvHeight
        rsHeight = binding.rsHeight
        tvWeight = binding.tvWeight
        fabWeightAdd = binding.fabWeightAdd
        fabWeightSubtract = binding.fabWeightSubtract
        tvAge = binding.tvAge
        fabAgeAdd = binding.fabAgeAdd
        fabAgeSubtract = binding.fabAgeSubtract
        btnCalculate = binding.btnCalculate
    }

    // Inicia los listeners de eventos
    private fun initListeners() {
        // Eventos de las cards
        cardFemale.setOnClickListener {
            selectGender(false)
        }
        cardMale.setOnClickListener {
            selectGender(true)
        }

        // Evento del Slider
        rsHeight.addOnChangeListener { _, value, _ ->
            currentHeight = value.toInt()

            setHeight(currentHeight)
        }

        // Eventos de los botones del peso
        fabWeightAdd.setOnClickListener {
            currentWeight += 1
            setWeight()
        }
        fabWeightSubtract.setOnClickListener {
            currentWeight -= 1
            setWeight()
        }

        // Eventos de los botones de la edad
        fabAgeAdd.setOnClickListener {
            currentgAge += 1
            setAge()
        }
        fabAgeSubtract.setOnClickListener {
            currentgAge -= 1
            setAge()
        }

        btnCalculate.setOnClickListener {
            val result = calculateIMC()
            navigateToResult(result)
        }
    }

    private fun initUI() {
        setWeight()
        setHeight(rsHeight.values[0].toInt())
        setAge()
    }


    /**
     * Funciones de manipulación de la vista, las separo de las funciones inicializadoras para mas orden
     */

    private fun selectGender(maleSelected: Boolean) {
        if(maleSelected) {
            cardMale.setCardBackgroundColor(getColor(R.color.background_component_selected))
            cardFemale.setCardBackgroundColor(getColor(R.color.background_component))
            isMaleSelected = true
        } else {
            cardFemale.setCardBackgroundColor(getColor(R.color.background_component_selected))
            cardMale.setCardBackgroundColor(getColor(R.color.background_component))
            isMaleSelected = false
        }
    }

    private fun setWeight() {
        tvWeight.text = currentWeight.toString()
    }

    private fun setHeight(heightVal: Int) {
        /**
         * La función getString() se utiliza cuando se usa un recurso string del XML con algún parametro.
         * El primer parametro que recibe la función es la referencia al recurso XML, los otros parametros
         * son los que hayamos indicado en el recurso en sí, en este caso el parametro del XML fue un %d,
         * esto quiere decir que es un número entero, por ende, el segundo parametro fue un Int
         */
        tvHeight.text = getString(R.string.height_value, heightVal)
    }

    private fun setAge() {
        tvAge.text = currentgAge.toString()
    }

    private fun calculateIMC() : Double {
        val df = DecimalFormat("#,##")

        val imc = currentWeight / (currentHeight.toDouble()/100 * currentHeight.toDouble()/100)
        Log.d("MainActivity","Imc antes de dser formateado $imc")

        val result = df.format(imc).toDouble()
        Log.d("MainActivity","imc despues de ser formateado $result")

        // df.roundingMode = RoundingMode.CEILING
        // Log.d("MainActivity", "imc despues de la funcion que no se que hace $result")

        return result
    }

    private fun navigateToResult(result: Double) {
        /**
         * Los [Intent] son objetos que se utilizan para solicitar una acción dentro de otro componente
         * de la app(ver Aspectos Fundamentales de la aplicación, sección de Componentes de la Aplicación).
         * Los [Intent] pueden iniciar una actividad, iniciar un servicio o transmitir una emisión.
         * En este caso se envia un dato extra en formato clave-valor a través de la función putExtra(),
         * los parametros de esta función son la clave, que es el IMC_KEY, declarado como propiedad estatica,
         * y el valor, el resultado del calculo del IMC
         */
        val intent = Intent(this, ResultActivity::class.java)
        intent.putExtra(IMC_KEY, result)
        // Inicia la actividad
        startActivity(intent)
    }


}