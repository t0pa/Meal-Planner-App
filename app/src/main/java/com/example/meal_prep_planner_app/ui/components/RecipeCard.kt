// RecipeCard.kt

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.text.font.FontWeight
import com.example.meal_prep_planner_app.model.Recipe
import com.example.meal_prep_planner_app.R

@Composable
fun RecipeCard(recipe: Recipe, onClick: () -> Unit) {

    val imageRes = R.drawable.pasta_sample //placeholder

    Card(
        modifier = Modifier
            .width(150.dp)
            .height(200.dp)
            .clickable { onClick() },
        shape = RoundedCornerShape(12.dp)
    ) {
        Box(modifier = Modifier.fillMaxSize()) {
            Image(
                painter = painterResource(id = R.drawable.pasta_sample), // simple static image
                contentDescription = recipe.title,
                modifier = Modifier.fillMaxSize(),
                contentScale = ContentScale.Crop
            )
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Black.copy(alpha = 0.3f))
            )
            Text(
                text = recipe.title,
                color = Color.White,
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                modifier = Modifier
                    .align(Alignment.BottomStart)
                    .padding(12.dp)
            )
        }
    }
}
