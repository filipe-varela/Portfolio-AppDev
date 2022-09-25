package com.vilp.bin2decapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.*
import kotlin.math.pow

class MainActivity : AppCompatActivity() {
    private lateinit var editTextBin: EditText
    private lateinit var editTextBase: EditText
    private lateinit var spinnerBase: Spinner
    private var baseIndex: Int = 0

    private lateinit var mapConverter: Map<String, (String) -> String>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        resources.getStringArray(R.array.base_array).also {
            mapConverter = mutableMapOf(
                it[0] to ::convertBin2Dec,
                it[1] to ::convertBin2Hex
            )
        }

        editTextBin = findViewById(R.id.edittxt_bin)
        editTextBase = findViewById(R.id.edittxt_base)

        editTextBin.onTextChangedListener { charSequence, _, _, _ ->
            changeBaseText(charSequence)
        }

        spinnerBase = findViewById(R.id.spinner_base)
        ArrayAdapter.createFromResource(
            this,
            R.array.base_array,
            android.R.layout.simple_spinner_item
        ).also {
            it.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinnerBase.adapter = it
        }
        spinnerBase.onItemSelectedListener = object: AdapterView.OnItemSelectedListener {
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {
                baseIndex = p2
                changeBaseText(editTextBin.text)
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {}

        }

        findViewById<Button>(R.id.btn_clear).setOnClickListener {
            editTextBin.text.clear()
        }

        findViewById<Button>(R.id.btn_one).setOnClickListener {
            editTextBin.text.append("1")
        }

        findViewById<Button>(R.id.btn_zero).setOnClickListener {
            editTextBin.text.append("0")
        }
    }

    private fun changeBaseText(charSequence: CharSequence?) {
        editTextBase.setText(
            mapConverter[spinnerBase.selectedItem.toString()]!!.invoke(
                charSequence.toString()
            ).uppercase()
        )
    }
}

fun EditText.onTextChangedListener(
    onTextChangeCallback: (
        charSequence: CharSequence?, start: Int, before: Int, count: Int
    ) -> Unit
) {
    this.addTextChangedListener(object : TextWatcher {
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            onTextChangeCallback(s, start, before, count)
        }

        override fun afterTextChanged(editable: Editable?) {
        }
    })
}

fun convertBin2Dec(binString: String): String = convertBin2DecUInt(binString).toString()

private fun convertBin2DecUInt(binString: String): UInt {
    var decResult: UInt = 0u
    for (index: Int in binString.indices.reversed())
        decResult += (binString[index].digitToInt() % 2).toUInt() *
                2.0.pow(binString.length - 1 - index).toUInt()
    return decResult
}

fun convertBin2Hex(binString: String) : String = Integer.toHexString(
    convertBin2DecUInt(binString).toInt()
)

// https://www.programiz.com/kotlin-programming/examples/binary-octal-convert
// Since the goal for this project was to convert binary to decimal, it was added this
// function so it could give more out-of-scope functionality into the project and the
// source of it was above link.
fun convertBin2Octal(binString: String): String {
    if (binString.isEmpty())
        return ""

    var binaryNumber: UInt = binString.toUInt()
    var octalNumber: UInt = 0u
    var decimalNumber: UInt = 0u
    var i: UInt = 0u

    while (binaryNumber != 0u) {
        decimalNumber += binaryNumber % 10u * 2.0.pow(i.toDouble()).toUInt()
        i++
        binaryNumber /= 10u
    }

    i = 1u

    while (decimalNumber != 0u) {
        octalNumber += decimalNumber % 8u * i
        decimalNumber /= 8u
        i *= 10u
    }

    return octalNumber.toString()
}