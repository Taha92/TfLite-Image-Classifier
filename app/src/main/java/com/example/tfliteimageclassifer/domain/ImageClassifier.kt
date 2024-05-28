package com.example.tfliteimageclassifer.domain

import android.graphics.Bitmap

interface ImageClassifier {
    fun classify(bitmap: Bitmap, rotation: Int): List<Classification>
}