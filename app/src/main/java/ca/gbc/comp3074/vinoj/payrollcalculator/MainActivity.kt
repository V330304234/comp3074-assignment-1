
package ca.gbc.comp3074.vinoj.payrollcalculator

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.gbc.comp3074.vinoj.payrollcalculator.ui.theme.PayrollCalculatorTheme

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            PayrollCalculatorTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize()
                ) { innerPadding ->
                    PayrollScreen(
                        modifier = Modifier.padding(innerPadding),
                        onAboutClick = {
                            val intent = Intent(
                                this,
                                AboutActivity::class.java
                            )
                            startActivity(intent)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun PayrollScreen(
    modifier: Modifier = Modifier,
    onAboutClick: () -> Unit
) {
    var hours by remember { mutableStateOf("") }
    var rate by remember { mutableStateOf("") }
    var taxRate by remember { mutableStateOf("") }

    var errorMessage by remember { mutableStateOf("") }

    var pay by remember { mutableStateOf<Double?>(null) }
    var overtimePay by remember { mutableStateOf<Double?>(null) }
    var totalPay by remember { mutableStateOf<Double?>(null) }
    var tax by remember { mutableStateOf<Double?>(null) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Text("Payroll Calculator")

        OutlinedTextField(
            value = hours,
            onValueChange = { hours = it },
            label = { Text("Hours Worked") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = rate,
            onValueChange = { rate = it },
            label = { Text("Hourly Rate") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        OutlinedTextField(
            value = taxRate,
            onValueChange = { taxRate = it },
            label = { Text("Tax Rate (e.g. 0.20 for 20%)") },
            modifier = Modifier.fillMaxWidth(),
            singleLine = true
        )

        Button(
            onClick = {
                val hoursValue = hours.toDoubleOrNull()
                val rateValue = rate.toDoubleOrNull()
                val taxValue = taxRate.toDoubleOrNull()

                if (hoursValue == null || rateValue == null || taxValue == null)
                {
                    errorMessage = "Please enter valid numbers."
                    pay = null
                    overtimePay = null
                    totalPay = null
                    tax = null
                }
                else if (hoursValue < 0 || rateValue < 0 || taxValue < 0 || taxValue > 1)
                {
                    errorMessage = "Please enter valid values."
                    pay = null
                    overtimePay = null
                    totalPay = null
                    tax = null
                } else {
                    errorMessage = ""

                    val regularPay: Double
                    val extraPay: Double

                    if (hoursValue <= 40) {
                        regularPay = hoursValue * rateValue
                        extraPay = 0.0
                    } else {
                        regularPay = 40 * rateValue
                        extraPay = (hoursValue - 40) * rateValue * 1.5
                    }

                    pay = regularPay
                    overtimePay = extraPay
                    totalPay = regularPay + extraPay
                    tax = regularPay * taxValue
                }
            },
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("Calculate Pay")
        }

        if (errorMessage.isNotEmpty()) {
            Text(errorMessage)
        }

        Spacer(modifier = Modifier.height(8.dp))

        Text("Results")

        Text(
            "Regular Pay: " + if (pay == null) { "--" }
            else {
                "%.2f".format(pay)
            }
        )

        Text(
            "Overtime Pay: " + if (overtimePay == null) {
                "--"
            } else {
                "%.2f".format(overtimePay)
            }
        )

        Text(
            "Total Pay: " + if (totalPay == null) {
                "--"
            } else {
                "%.2f".format(totalPay)
            }
        )

        Text(
            "Tax: " + if (tax == null) {
                "--"
            } else {
                "%.2f".format(tax)
            }
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = onAboutClick,
            modifier = Modifier.fillMaxWidth()
        ) {
            Text("About")
        }
    }
}
