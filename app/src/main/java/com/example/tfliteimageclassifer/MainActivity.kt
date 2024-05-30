package com.example.tfliteimageclassifer

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.camera.view.CameraController
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.example.tfliteimageclassifer.data.TfLiteBirdsClassifier
import com.example.tfliteimageclassifer.domain.Classification
import com.example.tfliteimageclassifer.presentation.BirdsImageAnalyzer
import com.example.tfliteimageclassifer.presentation.CameraPreview
import com.example.tfliteimageclassifer.presentation.DrawDetectedObjects
import com.example.tfliteimageclassifer.ui.theme.TfLiteImageClassiferTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        if (!hasCameraPermission()) {
            ActivityCompat.requestPermissions(
                this, arrayOf(Manifest.permission.CAMERA), 0
            )
        }
        setContent {
            Scaffold(
                content = { padding ->
                    Column(
                        modifier = Modifier
                            .padding(padding)
                    ) {
                        TfLiteImageClassiferTheme {
                            val screenWidth by remember {
                                mutableStateOf(applicationContext.resources.displayMetrics.widthPixels)
                            }

                            val screenHeight by remember {
                                mutableStateOf(applicationContext.resources.displayMetrics.heightPixels)
                            }

                            var classifications by remember {
                                mutableStateOf(emptyList<Classification>())
                            }

                            val analyzer = remember {
                                BirdsImageAnalyzer(
                                    classifier = TfLiteBirdsClassifier(applicationContext),
                                    onResults = {
                                        classifications = it
                                    }
                                )
                            }

                            val controller = remember {
                                LifecycleCameraController(applicationContext).apply {
                                    setEnabledUseCases(CameraController.IMAGE_ANALYSIS)
                                    setImageAnalysisAnalyzer(
                                        ContextCompat.getMainExecutor(applicationContext),
                                        analyzer
                                    )
                                }
                            }

                            Box(
                                modifier = Modifier
                                    .fillMaxSize()
                            ) {
                                CameraPreview(
                                    controller = controller,
                                    modifier = Modifier
                                        .fillMaxSize()
                                )
                                Column(
                                    modifier = Modifier
                                        .fillMaxWidth()
                                        .align(Alignment.TopCenter)
                                ) {
                                    classifications.forEach{
                                        Text(
                                            text = it.name,
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .background(MaterialTheme.colorScheme.primaryContainer)
                                                .padding(8.dp),
                                            textAlign = TextAlign.Center,
                                            fontSize = 20.sp,
                                            color = MaterialTheme.colorScheme.primary
                                        )

                                        DrawDetectedObjects(
                                            objects = classifications,
                                            imageWidth = 321,
                                            imageHeight = 321,
                                            screenWidth = screenWidth,
                                            screenHeight = screenHeight
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            )
        }
    }

    private fun hasCameraPermission() = ContextCompat.checkSelfPermission(
        this, Manifest.permission.CAMERA
    ) == PackageManager.PERMISSION_GRANTED
}