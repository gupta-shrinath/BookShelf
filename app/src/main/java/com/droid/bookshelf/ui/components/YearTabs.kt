package com.droid.bookshelf.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.ScrollableTabRow
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun YearTabs(
    years: List<Int>,
    selectedYear: Int,
    onYearSelected: (Int) -> Unit
) {
    ScrollableTabRow(
        selectedTabIndex = years.indexOf(selectedYear),
        modifier = Modifier.fillMaxWidth()
    ) {
        years.forEach { year ->
            Tab(
                selected = year == selectedYear,
                onClick = { onYearSelected(year) },
                text = { Text(year.toString()) }
            )
        }
    }
}
