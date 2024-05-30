package com.example.tfliteimageclassifer.domain

import android.graphics.Bitmap

interface BirdsClassifier {
    fun classify(bitmap: Bitmap, rotation: Int): List<Classification>
}