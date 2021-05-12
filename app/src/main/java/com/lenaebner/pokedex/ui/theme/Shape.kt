package com.lenaebner.pokedex.ui.theme

import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Shapes
import androidx.compose.ui.unit.dp

val RoundedCornerShapes = Shapes(
    small = RoundedCornerShape(4.dp),
    medium = RoundedCornerShape(8.dp),
    large = RoundedCornerShape(15.dp)
)

val CutCornerShapes = Shapes(
    small = CutCornerShape(topStart = 8.dp),
    medium = CutCornerShape(topStart = 24.dp),
    large = RoundedCornerShape(8.dp)
)