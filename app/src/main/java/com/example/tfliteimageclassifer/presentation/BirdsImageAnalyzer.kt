package com.example.tfliteimageclassifer.presentation

import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import com.example.tfliteimageclassifer.domain.BirdsClassifier
import com.example.tfliteimageclassifer.domain.Classification

class BirdsImageAnalyzer(
    private val classifier: BirdsClassifier,
    private val onResults: (List<Classification>) -> Unit
): ImageAnalysis.Analyzer {

    private var frameSkipCounter = 0
    override fun analyze(image: ImageProxy) {
        if (frameSkipCounter % 60 == 0) {
            val rotationDegrees = image.imageInfo.rotationDegrees
            val bitmap = image
                .toBitmap()
                .centercrop(321, 321)

            val result = classifier.classify(bitmap, rotationDegrees)
            onResults(result)
        }
        frameSkipCounter++

        image.close()
    }
}