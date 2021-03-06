package change.village.activity.main.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.nguyenhoanglam.imagepicker.model.Config.CREATOR.ROOT_DIR_DCIM
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import change.village.activity.main.category.Category
import change.village.activity.main.home.model.Item
import change.village.theme.MaterialBind
import change.village.theme.SystemUiController
import change.village.theme.colors
import change.village.theme.typography
import change.village.ui.GlideImage
import change.village.ui.RoundedTextField
import change.village.util.Database
import change.village.util.Util
import change.village.util.toast
import change.village.viewmodel.MainViewModel
import java.util.*

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

class ItemAddActivity : ComponentActivity() {

    private val vm = MainViewModel.instance
    private val selectedImages = mutableStateListOf<Image?>(null)
    private var categoryMenuExpend by mutableStateOf(false)
    private var selectedCategory by mutableStateOf(Category.None)
    private val categories = listOf(
        Category.None,
        Category.Electronics,
        Category.Computer,
        Category.Book,
        Category.Appliance
    )

    private val imagePicker by lazy {
        ImagePicker.with(this)
            .setFolderMode(true)
            .setFolderTitle("??????")
            .setRootDirectoryName(ROOT_DIR_DCIM)
            .setDirectoryName("?????? ??????")
            .setMultipleMode(true)
            .setShowNumberIndicator(true)
            .setMaxSize(7)
            .setLimitMessage("?????? 7????????? ???????????????.")
            .setRequestCode(100)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Util.requestStoragePermission(this)
        SystemUiController(window).setStatusBarColor(colors.primary)

        setContent {
            MaterialBind {
                ItemAddBind()
                BindCategoryMenu()
            }
        }
    }

    @Suppress("DEPRECATION")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (ImagePicker.shouldHandleResult(requestCode, resultCode, data, 100)) {
            val images: ArrayList<Image> = ImagePicker.getImages(data)
            selectedImages.addAll(0, images.toMutableList())
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    @Composable
    private fun ItemAddBind() {
        Scaffold(
            topBar = {
                TopAppBar(
                    elevation = 0.dp,
                    backgroundColor = colors.primary,
                    title = {
                        Row(
                            modifier = Modifier.fillMaxSize(),
                            horizontalArrangement = Arrangement.SpaceBetween
                        ) {
                            Text(text = "?????? ?????? ??????")
                            Row(
                                modifier = Modifier
                                    .fillMaxHeight()
                                    .wrapContentWidth()
                                    .clickable {
                                        categoryMenuExpend = true
                                    },
                                verticalAlignment = Alignment.CenterVertically
                            ) {
                                Text(selectedCategory)
                                Icon(
                                    imageVector = Icons.Default.ArrowDropDown,
                                    contentDescription = null
                                )
                            }
                        }
                    },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.Rounded.ArrowBack,
                            contentDescription = null,
                            modifier = Modifier.clickable { finish() }
                        )
                    }
                )
            }
        ) {
            val itemNameField = remember { mutableStateOf(TextFieldValue()) }
            val itemDescriptionField = remember { mutableStateOf(TextFieldValue()) }
            val itemPriceField = remember { mutableStateOf(TextFieldValue()) }
            val itemRentLengthField = remember { mutableStateOf(TextFieldValue()) }

            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(PaddingValues(16.dp))
            ) {
                Column(modifier = Modifier.fillMaxSize()) {
                    Text("?????????")
                    RoundedTextField(
                        value = itemNameField,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                    Text(text = "?????? ?????? ??????", modifier = Modifier.padding(top = 15.dp))
                    RoundedTextField(
                        value = itemDescriptionField,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp),
                        isSingleLine = false
                    )
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("?????? ??????")
                        RoundedTextField(
                            value = itemPriceField,
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier
                                .width(150.dp)
                                .padding(start = 10.dp)
                        )
                    }
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 15.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("?????? ??????")
                        RoundedTextField(
                            value = itemRentLengthField,
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier
                                .width(100.dp)
                                .padding(start = 10.dp)
                        )
                    }
                    Text(text = "?????? ??????", modifier = Modifier.padding(top = 15.dp))
                    LazyRow(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(top = 8.dp, bottom = 100.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.Center
                    ) {
                        items(
                            items = selectedImages,
                            itemContent = { image ->
                                if (image == null) {
                                    Icon(
                                        imageVector = Icons.Rounded.AddAPhoto,
                                        contentDescription = null,
                                        modifier = Modifier.clickable {
                                            imagePicker.start()
                                        }
                                    )
                                } else {
                                    GlideImage(modifier = Modifier.size(150.dp), src = image.uri)
                                }
                            }
                        )
                    }
                }
                Row(
                    modifier = Modifier.fillMaxSize(),
                    verticalAlignment = Alignment.Bottom
                ) {
                    OutlinedButton(
                        onClick = { /*TODO*/ },
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .padding(end = 15.dp)
                    ) {
                        Text(
                            text = "????????????",
                            style = typography.button,
                            color = Color.Black
                        )
                    }
                    Button(
                        onClick = {
                            val itemName = itemNameField.value.text
                            val itemDescription = itemDescriptionField.value.text
                            val itemPrice = itemPriceField.value.text
                            val itemRentLength = itemRentLengthField.value.text

                            if (
                                itemName.isNotBlank() && itemDescription.isNotBlank() &&
                                itemPrice.isNotBlank() && itemRentLength.isNotBlank()
                            ) {
                                if (selectedImages.size > 1) {
                                    val uuid = Util.createUuid()

                                    selectedImages.forEach { selectedImage ->
                                        if (selectedImage != null) {
                                            vm.storage.reference
                                                .child("items/$uuid/${selectedImage.name}")
                                                .putFile(selectedImage.uri)
                                                .addOnCompleteListener {
                                                    Database.requestImageUrls(uuid)
                                                }
                                        }
                                    }

                                    val item = Item(
                                        uuid = uuid,
                                        name = itemName,
                                        description = itemDescription,
                                        likeCount = 0,
                                        price = itemPrice.toLong(),
                                        rentLength = itemRentLength.toInt(),
                                        discountPercentage = 0,
                                        imageNames = selectedImages.filterNotNull().map { it.name },
                                        ownerUuid = vm.me.uuid,
                                        uploadDate = Date().time,
                                        category = selectedCategory
                                    )

                                    val newOwnItems = mutableListOf<String>()
                                    newOwnItems.addAll(vm.me.wrotePost)
                                    newOwnItems.add(uuid)
                                    vm.me = vm.me.copy(wrotePost = newOwnItems)
                                    Database.upload(item)
                                    toast("?????? ???????????????.")
                                    finish()
                                } else {
                                    toast("?????? ???????????? 1??? ?????? ????????? ?????????.")
                                }
                            } else {
                                toast("?????? ????????? ?????????.")
                            }
                        },
                        shape = RoundedCornerShape(15.dp),
                        modifier = Modifier
                            .weight(1f)
                            .height(50.dp)
                            .padding(start = 15.dp)
                    ) {
                        Text(
                            text = "????????????",
                            style = typography.button,
                            color = Color.White
                        )
                    }
                }
            }
        }
    }

    @Composable
    private fun BindCategoryMenu() {
        DropdownMenu(
            expanded = categoryMenuExpend,
            onDismissRequest = { categoryMenuExpend = false },
        ) {
            categories.forEach { category ->
                DropdownMenuItem(
                    onClick = {
                        selectedCategory = category
                        categoryMenuExpend = false
                    }
                ) {
                    Text(category)
                }
            }
        }
    }
}
