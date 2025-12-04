// ui/goals/GoalsScreen.kt
package com.example.nutritiontracker.ui.goals

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowUpward
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.example.nutritiontracker.ui.components.HeaderSection
import com.example.nutritiontracker.ui.theme.GrayBackground
import com.example.nutritiontracker.ui.theme.GreenPrimary
import com.example.nutritiontracker.ui.theme.TextPrimary
import com.example.nutritiontracker.ui.theme.TextSecondary

/* TABS MODEL */

private enum class GoalsTab(val label: String) {
    Daily("Daily Goals"),
    Weekly("Weekly Goals"),
    Monthly("Monthly Goals")
}

/* ROOT SCREEN */

@Composable
fun GoalsScreen(
    modifier: Modifier = Modifier,
    onSettingsClick: () -> Unit = {}
) {
    var selectedTab by remember { mutableStateOf(GoalsTab.Daily) }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(GrayBackground)
    ) {
        // Green header
        HeaderSection(
            title = "Goals",
            onSettingsClick = onSettingsClick
        )

        Spacer(Modifier.height(16.dp))

        // white bar with “Daily / Weekly / Monthly”
        GoalsTabSwitcher(
            selectedTab = selectedTab,
            onTabSelected = { selectedTab = it }
        )

        Spacer(Modifier.height(16.dp))

        // Scrollable content under the tab bar
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
        ) {
            when (selectedTab) {
                GoalsTab.Daily   -> DailyGoalsContent()
                GoalsTab.Weekly  -> WeeklyGoalsContent()
                GoalsTab.Monthly -> MonthlyGoalsContent()
            }

            Spacer(Modifier.height(32.dp))
        }
    }
}

/* TAB SWITCHER */

@Composable
private fun GoalsTabSwitcher(
    selectedTab: GoalsTab,
    onTabSelected: (GoalsTab) -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(32.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(4.dp),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            GoalsTab.values().forEach { tab ->
                val isSelected = tab == selectedTab
                Box(
                    modifier = Modifier
                        .weight(1f)
                        .height(44.dp)
                        .padding(horizontal = 4.dp)
                        .background(
                            color = if (isSelected) GreenPrimary else Color.Transparent,
                            shape = RoundedCornerShape(24.dp)
                        )
                        .clickable { onTabSelected(tab) },
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        text = tab.label.substringBefore(" "),
                        style = MaterialTheme.typography.bodyMedium,
                        color = if (isSelected) Color.White else TextPrimary,
                        fontWeight = if (isSelected) FontWeight.SemiBold else FontWeight.Normal
                    )
                }
            }
        }
    }
}

/* DAILY GOALS */

@Composable
private fun DailyGoalsContent() {
    // Main white card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 6.dp)
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp)
        ) {
            Text(
                text = "Daily Goals",
                style = MaterialTheme.typography.titleMedium,
                color = TextPrimary
            )

            Spacer(Modifier.height(16.dp))

            // Top three big rings
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                BigRingStat(
                    value = "1850",
                    unit = "kcal",
                    label = "Calories",
                    subtitle = "1850/2500 kcal"
                )
                BigRingStat(
                    value = "3",
                    unit = "",
                    label = "Fruits",
                    subtitle = "3/4\nservings"
                )
                BigRingStat(
                    value = "4",
                    unit = "",
                    label = "Veggies",
                    subtitle = "4/5\nservings"
                )
            }

            Spacer(Modifier.height(20.dp))

            Text(
                text = "Macronutrients",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )

            Spacer(Modifier.height(12.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                SmallRingStat(
                    label = "Protein",
                    value = "78/120",
                    unit = "g",
                    ringColor = Color(0xFF5C9DFF)
                )
                SmallRingStat(
                    label = "Carbs",
                    value = "195/280",
                    unit = "g",
                    ringColor = Color(0xFFFFB74D)
                )
                SmallRingStat(
                    label = "Fat",
                    value = "52/70",
                    unit = "g",
                    ringColor = Color(0xFFBA68C8)
                )
            }

            Spacer(Modifier.height(20.dp))

            Divider(color = Color(0xFFE5E7F0))

            Spacer(Modifier.height(16.dp))

            // Today’s Intake vs RDI
            Text(
                text = "Today’s Intake vs RDI",
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )

            Spacer(Modifier.height(16.dp))

            RdiProgressRow("Fiber",     "18/30",      18f / 30f,      GreenPrimary)
            Spacer(Modifier.height(10.dp))
            RdiProgressRow("Vitamin C", "65/90",      65f / 90f,      Color(0xFFFFA726))
            Spacer(Modifier.height(10.dp))
            RdiProgressRow("Vitamin D", "12/20",      12f / 20f,      Color(0xFFAB47BC))
            Spacer(Modifier.height(10.dp))
            RdiProgressRow("Calcium",   "750/1000 mg",750f / 1000f,   Color(0xFF42A5F5))
            Spacer(Modifier.height(10.dp))
            RdiProgressRow("Iron",      "11/18 mg",   11f / 18f,      Color(0xFFE53935))
        }
    }

    Spacer(Modifier.height(16.dp))

    // “Great Progress!” card
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFF1FAF5)),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 20.dp, vertical = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(40.dp)
                    .background(Color.White, CircleShape),
                contentAlignment = Alignment.Center
            ) {
                Icon(
                    imageVector = Icons.Filled.ArrowUpward,
                    contentDescription = null,
                    tint = GreenPrimary
                )
            }

            Spacer(Modifier.width(16.dp))

            Column {
                Text(
                    text = "Great Progress!",
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary
                )
                Text(
                    text = "You’re 74% to your calorie goal",
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
    }
}



@Composable
private fun BigRingStat(
    value: String,
    unit: String,
    label: String,
    subtitle: String
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(96.dp)
                .border(10.dp, Color(0xFFE0F5EA), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.titleMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                if (unit.isNotBlank()) {
                    Text(
                        text = unit,
                        style = MaterialTheme.typography.bodySmall,
                        color = TextSecondary
                    )
                }
            }
        }
        Spacer(Modifier.height(8.dp))
        Text(text = label, style = MaterialTheme.typography.bodyMedium, color = TextPrimary)
        Text(text = subtitle, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
    }
}

@Composable
private fun SmallRingStat(
    label: String,
    value: String,
    unit: String,
    ringColor: Color
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Box(
            modifier = Modifier
                .size(72.dp)
                .border(6.dp, ringColor.copy(alpha = 0.45f), CircleShape),
            contentAlignment = Alignment.Center
        ) {
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = value,
                    style = MaterialTheme.typography.bodyMedium,
                    color = TextPrimary,
                    fontWeight = FontWeight.SemiBold
                )
                Text(
                    text = unit,
                    style = MaterialTheme.typography.bodySmall,
                    color = TextSecondary
                )
            }
        }
        Spacer(Modifier.height(6.dp))
        Text(text = label, style = MaterialTheme.typography.bodySmall, color = TextPrimary)
    }
}

@Composable
private fun RdiProgressRow(
    label: String,
    current: String,
    progress: Float,
    barColor: Color
) {
    Column {
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(text = label, style = MaterialTheme.typography.bodySmall, color = TextPrimary)
            Text(text = current, style = MaterialTheme.typography.bodySmall, color = TextSecondary)
        }
        Spacer(Modifier.height(4.dp))
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(Color(0xFFEFF1F5), RoundedCornerShape(4.dp))
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth(progress.coerceIn(0f, 1f))
                    .fillMaxHeight()
                    .background(barColor, RoundedCornerShape(4.dp))
            )
        }
    }
}


@Composable
private fun WeeklyGoalsContent() {
    PlaceholderCard(title = "Weekly Goals Coming Soon")
}

@Composable
private fun MonthlyGoalsContent() {
    PlaceholderCard(title = "Monthly Goals Coming Soon")
}

@Composable
private fun PlaceholderCard(title: String) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        shape = RoundedCornerShape(24.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(32.dp),
            contentAlignment = Alignment.Center
        ) {
            Text(
                text = title,
                style = MaterialTheme.typography.bodyMedium,
                color = TextPrimary
            )
        }
    }
}