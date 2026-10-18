
package ca.gbc.comp3074.vinoj.payrollcalculator

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import ca.gbc.comp3074.vinoj.payrollcalculator.ui.theme.PayrollCalculatorTheme

class AboutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        supportActionBar?.title = "About"
        supportActionBar?.setDisplayHomeAsUpEnabled(true)

        setContent {
            PayrollCalculatorTheme {
                AboutScreen()
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        finish()
        return true
    }
}

@Composable
fun AboutScreen(modifier: Modifier = Modifier)
{
    Column(
        modifier = modifier.fillMaxSize().padding(24.dp),
        verticalArrangement = Arrangement.spacedBy(16.dp)
    )
    {
        Text(text = "About", style = MaterialTheme.typography.headlineMedium)

        Text(text = "Payroll Calculator", style = MaterialTheme.typography.titleLarge)

        Text(text = "Created by Vinoj Rajendran for COMP3074.")

        Text(text = "Student ID: 101515911")
    }
}
