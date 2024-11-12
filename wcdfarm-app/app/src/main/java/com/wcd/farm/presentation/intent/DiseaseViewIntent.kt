package com.wcd.farm.presentation.intent

sealed class DiseaseViewIntent {
    object ShowDiseaseView: DiseaseViewIntent()
    object HideDiseaseView: DiseaseViewIntent()
    object ShowCaptureImageView: DiseaseViewIntent()
    object ShowPreviewCaptureView: DiseaseViewIntent()
    object ShowDiseaseDetectionResult: DiseaseViewIntent()
    object ShowOnDiseaseDetection: DiseaseViewIntent()
    object ShowDiseaseDetected: DiseaseViewIntent()
    object ShowDiseaseNotDetected: DiseaseViewIntent()
}