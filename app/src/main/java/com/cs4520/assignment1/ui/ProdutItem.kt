package com.cs4520.assignment1.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.cs4520.assignment1.model.database.Product
import com.cs4520.assignment5.R
import org.intellij.lang.annotations.JdkConstants.HorizontalAlignment

@Composable
fun ProductItem(
    product: Product
) {
    Card(modifier = Modifier
        .padding(8.dp, 4.dp)
        .fillMaxWidth()
        .height(110.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp)
    {
        Surface( color = getBackgroundColor(product)) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.fillMaxSize().padding(horizontal = 5.dp)) {
                ProductImage(product)
                Column(
                    modifier = Modifier.padding(start = 10.dp).weight(1f)
                ){
                    product.name?.let { Text(text = it, style = MaterialTheme.typography.subtitle1)}
                    product.expiryDate?.let { Text(text = "Expires On: ${product.expiryDate}",
                        style = MaterialTheme.typography.subtitle1)}
                    Text(text = "Price: $${product.price}", style = MaterialTheme.typography.subtitle1)
                }
            }
        }
    }
}
@Composable
fun ProductImage(product: Product){
    Image(painter = painterResource(id = getProductImageId(product)),
        contentDescription = null,
        contentScale = ContentScale.Crop,
        modifier = Modifier
            .size(60.dp)
            .clip(RoundedCornerShape(16.dp)))
}

fun getProductImageId(product: Product): Int{
    return if(product.type == "Food"){
        R.drawable.ic_food
    } else {
        R.drawable.ic_equipment
    }
}
fun getBackgroundColor(product: Product): Color{
    return if(product.type == "Food"){
        Color(255, 255,101)
    } else {
        Color(224, 102, 102)
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewProductItem(){
    val product = Product(12, "Apple_23233232323232332323232323", "2030302-32", "20", "Food")
    Card(modifier = Modifier
        .padding(8.dp, 4.dp)
        .fillMaxWidth()
        .height(110.dp),
        shape = RoundedCornerShape(8.dp),
        elevation = 4.dp)
    {
        Surface( color = Color.Red) {
            Row(
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier.padding(4.dp).fillMaxSize().padding(horizontal = 20.dp)) {
                Image(painter = painterResource(id = getProductImageId(product)),
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .size(70.dp)
                        .clip(RoundedCornerShape(16.dp)))
                Column(
                    modifier = Modifier.padding(horizontal = 20.dp)
                ){
                    product.name?.let { Text(text = it, style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(bottom= 2.dp))}
                    product.expiryDate?.let { Text(text = "Expires On: ${product.expiryDate}",
                        style = MaterialTheme.typography.subtitle1, modifier = Modifier.padding(bottom= 2.dp))}
                    Text(text = "Price: $${product.price}", style = MaterialTheme.typography.subtitle1)
                }
            }
        }
    }
}