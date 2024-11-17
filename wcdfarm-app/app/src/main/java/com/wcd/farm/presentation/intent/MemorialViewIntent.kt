package com.wcd.farm.presentation.intent

sealed class MemorialViewIntent {
    object ShowGalleryView : MemorialViewIntent()
    object ShowGrowthView : MemorialViewIntent()
    object ShowAnimalView : MemorialViewIntent()
    object ShowTheftView : MemorialViewIntent()

    object ShowDialog : MemorialViewIntent()
    object HideDialog : MemorialViewIntent()

    object ShowInvasionView : MemorialViewIntent()
    object HideInvasionView : MemorialViewIntent()
}