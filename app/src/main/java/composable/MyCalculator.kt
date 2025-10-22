package components

import android.graphics.Color
import android.widget.Space
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import kotlin.text.toInt

@Composable
fun CalculatorFields(
    value: MutableState<String>,
    label: String,
    modifier: Modifier = Modifier
) {
    OutlinedTextField(
        value = value.value,
        label = {
            Text(text = label)
        },
        onValueChange = { value.value = it },
        modifier = modifier
            .fillMaxWidth(0.9f)
            .padding(8.dp)
    )
}

@Composable
fun CalculatorButton(
    label: String,
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    ElevatedButton(
        onClick = onClick,
        shape = RoundedCornerShape(5.dp),
        modifier = modifier.size(150.dp)
    ) {
        Text(text = label, fontSize = 48.sp)
    }
}

@Composable
fun MyCalculator(
    modifier: Modifier = Modifier
) {
    val number1 = remember { mutableStateOf("") }
    val number2 = remember { mutableStateOf("") }
    val result = remember { mutableIntStateOf(0) }
    Column {
        Spacer(modifier = Modifier.padding(8.dp))
        CalculatorFields(
            label = "Number 1",
            value = number1,
            modifier = modifier
        )
        CalculatorFields(
            label = "Number 2",
            value = number2,
            modifier = modifier
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton(
                label = "+",
                onClick = {
                    result.intValue = number1.value.toInt() + number2.value.toInt()
                },
                modifier = modifier
            )
            CalculatorButton(
                label = "-",
                onClick = {
                    result.intValue = number1.value.toInt() - number2.value.toInt()
                },
                modifier = modifier
            )
        }
        Spacer(
            modifier = Modifier.padding(8.dp)
        )
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceEvenly
        ) {
            CalculatorButton(
                label = "*",
                onClick = {
                    result.intValue = number1.value.toInt() * number2.value.toInt()
                },
                modifier = modifier
            )
            CalculatorButton(
                label = "/",
                onClick = {
                    result.intValue = number1.value.toInt() / number2.value.toInt()
                },
                modifier = modifier
            )
        }

        Text(
            text = "Result: ${result.intValue}",
            fontSize = 25.sp
            )
    }
}

@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    MyCalculator()
}