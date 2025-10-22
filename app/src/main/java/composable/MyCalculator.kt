package components

import android.graphics.Color
import android.os.Build
import android.widget.Button
import android.widget.Space
import androidx.annotation.RequiresApi
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
        modifier = modifier.size(100.dp)
    ) {
        Text(text = label, fontSize = 48.sp)
    }
}

@Composable
fun Numbers(
    onNumberClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Row {
        for (i in 0..2) {
            CalculatorButton(
                label = i.toString(),
                onClick = {
                    onNumberClick(i.toString())
                },
                modifier = modifier
            )
        }
    }
    Row {
        for (i in 3..5) {
            CalculatorButton(
                label = i.toString(),
                onClick = {
                    onNumberClick(i.toString())
                },
                modifier = modifier
            )
        }
    }
    Row {
        for (i in 6..8) {
            CalculatorButton(
                label = i.toString(),
                onClick = {
                    onNumberClick(i.toString())
                },
                modifier = modifier
            )
        }
    }
    Row {
        CalculatorButton(
            label = "9",
            onClick = {
                onNumberClick("9")
            },
        )
        CalculatorButton(
            label = "+",
            onClick = {
                onNumberClick("+")
            },
            modifier = modifier
        )
        CalculatorButton(
            label = "-",
            onClick = {
                onNumberClick("-")
            },
            modifier = modifier
        )

    }
    Row {
        CalculatorButton(
            label = "*",
            onClick = {
                onNumberClick("*")
            },
        )
        CalculatorButton(
            label = "/",
            onClick = {
                onNumberClick("/")
            },
            modifier = modifier
        )
        CalculatorButton(
            label = "C",
            onClick = {
                onNumberClick("C")
            },
            modifier = modifier
        )

    }


}


@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Composable
fun MyCalculator(
    modifier: Modifier = Modifier
) {


    val result = remember { mutableIntStateOf(0) }
    val numbers = remember { mutableStateOf(listOf<String>()) }
    val expr = sanitizeExpression(numbers.value.joinToString(""))
    val total = remember { mutableStateOf<String>("") }


    Column {
        Spacer(modifier = Modifier.padding(8.dp))
        Text(
            text = numbers.value.joinToString(""),
            fontSize = 25.sp
        )

        Spacer(
            modifier = Modifier.padding(8.dp)
        )

        Numbers(
            onNumberClick = { clicked ->
                if (clicked == "C") {
                    numbers.value = listOf()
                    total.value = ""
                }else{
                    numbers.value = numbers.value + clicked
                }

            }
        )
        ElevatedButton(
            onClick = {
                val expr = sanitizeExpression(numbers.value.joinToString(""))
                total.value = calculateTotal(expr)
            },
            modifier = Modifier.padding(8.dp)
        ) {
            Text(text = "=")
        }

        Text(
            text = total.value,
            fontSize = 25.sp
        )

    }
}
fun splitChar(expr: String): List<String> {
    return Regex("(?<=[+\\-*/])|(?=[+\\-*/])")
        .split(expr)
        .filter { it.isNotBlank() }
}
fun sanitizeExpression(expr: String): String {
    if (expr.isEmpty()) return "0"
    var clean = expr
    while (clean.isNotEmpty() && clean.last() in "+-*/") {
        clean = clean.dropLast(1)
    }
    return if (clean.isEmpty()) "0" else clean
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
fun calculateMultAndDiv(numbers: List<String>): List<String> {
    val result = mutableListOf<String>()
    var i = 0
    while (i < numbers.size) {
        val token = numbers[i]
        if (token == "*" || token == "/") {
            val left = result.removeLast().toDouble()
            val right = numbers[i + 1].toDouble()
            val res = if (token == "*") left * right else left / right
            result.add(res.toString())
            i += 2
        } else {
            result.add(token)
            i++
        }
    }
    return result
}
fun calculateAddAndSub(numbers: List<String>): Double {
    if (numbers.isEmpty()) return 0.0
    var result = numbers[0].toDoubleOrNull() ?: 0.0
    var i = 1
    while (i < numbers.size) {
        val op = numbers[i]
        val next = numbers.getOrNull(i + 1)?.toDoubleOrNull() ?: 0.0
        result = when (op) {
            "+" -> result + next
            "-" -> result - next
            else -> result
        }
        i += 2
    }
    return result
}

@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
fun calculateTotal(expr: String): String {
    if (expr.isBlank()) return "0"
    val tokens = splitChar(expr)
    if (tokens.isEmpty()) return "0"
    val afterMulDiv = calculateMultAndDiv(tokens)
    if (afterMulDiv.isEmpty()) return "0"
    val result = calculateAddAndSub(afterMulDiv)
    return result.toString()
}


@RequiresApi(Build.VERSION_CODES.VANILLA_ICE_CREAM)
@Preview(showBackground = true)
@Composable
fun CalculatorPreview() {
    MyCalculator()

}