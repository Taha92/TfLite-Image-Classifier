package com.example.tfliteimageclassifer.presentation

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.Stroke
import com.example.tfliteimageclassifer.domain.Classification
import androidx.compose.ui.unit.dp

@Composable
fun DrawDetectedObjects(objects: List<Classification>, imageWidth: Int, imageHeight: Int, screenWidth: Int, screenHeight: Int) {
    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .padding(30.dp)
    ) {
        objects.forEach { _ ->
            drawRoundRect(
                color = Color.Red,
                cornerRadius = CornerRadius(60f, 60f),
                //size = Size(screenWidth / 2f, screenHeight / 2f),
                style = Stroke(width = 15f, cap = StrokeCap.Round)
            )
        }
    }
}