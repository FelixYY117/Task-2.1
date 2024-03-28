package com.example.unitconverterapp;

import static android.content.ContentValues.TAG;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

public class MainActivity extends AppCompatActivity {
    private Spinner fromSpinner;
    private Spinner toSpinner;
    private EditText valueEditText;
    private TextView answerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        Spinner typeSpinner = findViewById(R.id.typeSpinner);
        fromSpinner = findViewById(R.id.fromSpinner);
        toSpinner = findViewById(R.id.toSpinner);
        valueEditText = findViewById(R.id.valueEditText);
        answerTextView = findViewById(R.id.answerTextView);
String[] conversionType = getResources().getStringArray(R.array.conversion_types);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,conversionType
               );


        typeAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        typeSpinner.setAdapter(typeAdapter);

        typeSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener(){

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                String selectedType =(String) parent.getItemAtPosition(position);
                updateUnitSpinners(selectedType);//根据所选单位变换Spinner
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        Button convertButton=findViewById(R.id.convertButton);
        convertButton.setOnClickListener(new View.OnClickListener(){
            @SuppressLint("SetTextI18n")//?
            @Override
            public void onClick(View v) {
                double value=Double.parseDouble(valueEditText.getText().toString());//转换为double类型
                String fromUnit = fromSpinner.getSelectedItem().toString();
              //  Log.d(TAG, "onClick:before unit "+fromUnit);//test1
                String toUnit = toSpinner.getSelectedItem().toString();
              // Log.d(TAG, "onClick:after unit "+toUnit);//test2
                double result = convert(value, fromUnit, toUnit);
                answerTextView.setText("Answer is :"+Double.toString(result));
            }
        });
//        findViewById<Button>(R.id.convertButton).setOnClickListener {
//            val value = valueEditText.text.toString().toDoubleOrNull() ?: 0.0
//            val fromUnit = fromSpinner.selectedItem.toString()
//            val toUnit = toSpinner.selectedItem.toString()
//            val result = convert(value, fromUnit, toUnit)
//            answerTextView.text = result.toString()
//        }



    }

    private void updateUnitSpinners(String conversionType) {
        String[] stringArray;
        switch (conversionType) {//根据选择的单位类型改变后续的spinner
            case "Weight":
                stringArray = getResources().getStringArray(R.array.weight_units);///get wight unit
                break;
            case "Length":
                stringArray = getResources().getStringArray(R.array.length_units);//get length unit
                break;
            case "Temperature":
                stringArray = getResources().getStringArray(R.array.temperature_units);//get tem unit
                break;
            default:
                stringArray = getResources().getStringArray(R.array.null_types);//return null
                break;
        }
        ArrayAdapter<String> fromAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,stringArray);
        fromAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        fromSpinner.setAdapter(fromAdapter);

        ArrayAdapter<String> toAdapter = new ArrayAdapter<>(this,
                android.R.layout.simple_list_item_1,stringArray);
        toAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        toSpinner.setAdapter(fromAdapter);
//toAdapter也用相同的方式改变
    }

    private double convert(Double value , String fromUnit, String toUnit ) {
        // Conversion calculations are performed based on different unit types and units
        if(fromUnit.equals("Inch") || fromUnit.equals("Foot") || fromUnit.equals("Yard")
                || fromUnit.equals("Mile")){
          // Log.d(TAG, "convert: get in "+"VALUE:"+value+"answer:"+convertLength(value,fromUnit,toUnit));
            return  convertLength(value,fromUnit,toUnit);
        } else if (fromUnit.equals("Ton") || fromUnit.equals("Pound") || fromUnit.equals("Ounce")) {
            return  convertWeight(value,fromUnit,toUnit);
        } else if (fromUnit.equals("Celsius") || fromUnit.equals("Fahrenheit") || fromUnit.equals("Kelvin")) {
            return  convertTemperature(value,fromUnit,toUnit);
        } else { Log.d(TAG, "convert: Error ");
            return 0.0;}
    }
    public  double convertLength(double value, String fromUnit, String toUnit) {//用于转换长度
        switch (fromUnit) {
            case "Inch":
                switch (toUnit) {
                    case "Foot":
                        return value*(2.54/30.48);
                    case "Yard":
                            return value*(2.54/91.44);
                    case "Mile":
                            return value*(2.54/160934);
                    default:
                        return value;
                }
            case "Foot":
                switch (toUnit) {
                    case "Inch":
                        return value * (30.48/2.54);
                    case "Yard":
                        return value * (30.48/91.44);
                    case "Mile":
                        return value * (30.48/160934);
                    default:
                        return value;
                }
            case "Yard":
                switch (toUnit) {
                    case "Inch":
                        return value * (91.44/2.54);
                    case "Foot":
                        return value * (91.44/30.48);
                    case "Mile":
                        return value * (91.44/160934);
                    default:
                        return value;
                }
            case "Mile":
                switch (toUnit) {
                    case "Inch":
                        return value * (160934/2.54);
                    case "Foot":
                        return value *  (160934/30.48);
                    case "Yard":
                        return value *  (160934/91.44);
                    default:
                        return value;
                }
            default:
                return 0.0;
        }
    }
    public  double convertWeight(double value, String fromUnit, String toUnit) {//转换重量
        switch (fromUnit) {
            case "Pound":
                switch (toUnit) {
                    case "Ounce":
                        return value * (453.592/28.3495);
                    case "Ton":{
                        return value*(0.453592/907.185);
                    }
                    default:
                        return value;
                }
            case "Ounce":
                switch (toUnit) {
                    case "Pound":
                        return value * (28.3495/453.592);
                    case "Ton":{
                        return value*(28.3495/907195);
                    }
                    default:
                        return value;
                }
            case "Ton":
                switch (toUnit) {
                    case "Pound":
                       return value*(907.185/0.453592);
                    case "Ounce":
                        return value * (907185/28.3495);
                    default:
                        return value;
                }
            default:
                return 0.0;
        }
    }
    public static double convertTemperature(double value, String fromUnit, String toUnit) {//转换温度
        switch (fromUnit) {
            case "Celsius":
                switch (toUnit) {
                    case "Fahrenheit":
                        return (value * 1.8) + 32;
                    case "Kelvin":
                        return value + 273.15;
                    default:
                        return value;
                }
            case "Fahrenheit":
                switch (toUnit) {
                    case "Celsius":
                        return (value - 32) / 1.8;
                    case "Kelvin":
                        return (value + 459.67) * (5.0 / 9.0);
                    default:
                        return value;
                }
            case "Kelvin":
                switch (toUnit) {
                    case "Celsius":
                        return value - 273.15;
                    case "Fahrenheit":
                        return (value * 9.0 / 5.0) - 459.67;
                    default:
                        return value;
                }
            default:
                return 0.0;
        }
    }
}
