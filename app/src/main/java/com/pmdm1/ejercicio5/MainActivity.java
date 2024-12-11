package com.pmdm1.ejercicio5;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {
    private EditText etTemperature;
    private RadioGroup rgFrom, rgTo;
    private TextView tvResult;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Referenciar vistas
        etTemperature = findViewById(R.id.etTemperature);
        rgFrom = findViewById(R.id.rgFrom);
        rgTo = findViewById(R.id.rgTo);
        tvResult = findViewById(R.id.tvResult);
        Button btnConvert = findViewById(R.id.btnConvert);

        // Configurar botón de conversión
        btnConvert.setOnClickListener(v -> convertTemperature());
    }

    private void convertTemperature() {
        // Obtener el valor ingresado
        String tempInput = etTemperature.getText().toString().trim();
        if (tempInput.isEmpty()) {
            Toast.makeText(this, "Por favor ingresa un valor", Toast.LENGTH_SHORT).show();
            return;
        }

        double temperature = Double.parseDouble(tempInput);

        // Identificar escala origen y destino
        int fromId = rgFrom.getCheckedRadioButtonId();
        int toId = rgTo.getCheckedRadioButtonId();

        if (fromId == -1 || toId == -1) {
            Toast.makeText(this, "Selecciona ambas escalas", Toast.LENGTH_SHORT).show();
            return;
        }

        String fromScale = ((RadioButton) findViewById(fromId)).getText().toString();
        String toScale = ((RadioButton) findViewById(toId)).getText().toString();

        // Realizar la conversión
        double result = convertTemperatureValue(temperature, fromScale, toScale);

        // Mostrar el resultado
        tvResult.setText(String.format("Resultado: %.2f %s", result, toScale));
    }

    private double convertTemperatureValue(double temperature, String fromScale, String toScale) {
        if (fromScale.equals(toScale)) {
            return temperature; // Mismo valor si la escala no cambia
        }

        switch (fromScale) {
            case "Celsius":
                if (toScale.equals("Kelvin")) return temperature + 273.15;
                if (toScale.equals("Fahrenheit")) return (temperature * 9 / 5) + 32;
                break;

            case "Kelvin":
                if (toScale.equals("Celsius")) return temperature - 273.15;
                if (toScale.equals("Fahrenheit")) return (temperature - 273.15) * 9 / 5 + 32;
                break;

            case "Fahrenheit":
                if (toScale.equals("Celsius")) return (temperature - 32) * 5 / 9;
                if (toScale.equals("Kelvin")) return (temperature - 32) * 5 / 9 + 273.15;
                break;
        }

        return 0; // Valor por defecto si no se encuentra la conversión
    }
}