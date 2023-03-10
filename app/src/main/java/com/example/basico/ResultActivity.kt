package com.example.basico

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import com.example.basico.databinding.ActivityResultBinding

class ResultActivity : AppCompatActivity() {
    private lateinit var binding: ActivityResultBinding

    private lateinit var tvResult: TextView
    private lateinit var tvIMC: TextView
    private lateinit var tvDescription: TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityResultBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val result: Double = intent.extras?.getDouble(MainActivity.IMC_KEY) ?: -1.0

        initComponents()
        initUI(result)

    }

    private fun initComponents() {
        tvResult = binding.tvResult
        tvDescription = binding.tvDescription
        tvIMC = binding.tvImc
    }

    private fun initUI(result: Double) {
        tvIMC.text = result.toString()

        when(result) {
            // Bajo peso
            in 0.00..18.50-> {
                tvResult.text = getString(R.string.title_peso_bajo)
                tvDescription.text = getString(R.string.description_peso_bajo)
            }
            // Peso normal
            in 18.51..24.99 -> {
                tvResult.text = getString(R.string.title_peso_normal)
                tvDescription.text = getString(R.string.description_peso_normal)
            }
            // Sobrepeso
            in 25.00..29.99 -> {
                tvResult.text = getString(R.string.title_peso_sobrepeso)
                tvDescription.text = getString(R.string.description_peso_sobrepeso)
            }
            // Obesidad
            in 30.00..99.99 -> {
                tvResult.text = getString(R.string.title_peso_obesidad)
                tvDescription.text = getString(R.string.description_peso_obesidad)
            }
            else -> {
                //Error
                tvResult.text = getString(R.string.error)
                tvDescription.text = getString(R.string.error)
                tvIMC.text = getString(R.string.error)
            }
        }
    }
}