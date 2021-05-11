package you.village.ui.main.home

import android.content.Intent
import android.os.Bundle
import androidx.activity.compose.setContent
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Button
import androidx.compose.material.Icon
import androidx.compose.material.OutlinedButton
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.material.TopAppBar
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AddAPhoto
import androidx.compose.material.icons.rounded.ArrowBack
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import com.nguyenhoanglam.imagepicker.model.Config.CREATOR.ROOT_DIR_DCIM
import com.nguyenhoanglam.imagepicker.model.Image
import com.nguyenhoanglam.imagepicker.ui.imagepicker.ImagePicker
import java.util.ArrayList
import java.util.UUID
import you.village.model.Item
import you.village.theme.MaterialBind
import you.village.theme.SystemUiController
import you.village.theme.colors
import you.village.theme.typography
import you.village.ui.BaseActivity
import you.village.ui.widget.GlideImage
import you.village.ui.widget.RoundedTextField
import you.village.util.DataUtil
import you.village.util.Util
import you.village.util.toast

/**
 * Created by Ji Sungbin on 2021/05/03.
 */

class ItemAddActivity : BaseActivity() {

    private val selectedImages = mutableStateListOf<Image?>(null)

    private val imagePicker by lazy {
        ImagePicker.with(this)
            .setFolderMode(true)
            .setFolderTitle("앨범")
            .setRootDirectoryName(ROOT_DIR_DCIM)
            .setDirectoryName("사진 선택")
            .setMultipleMode(true)
            .setShowNumberIndicator(true)
            .setMaxSize(7)
            .setLimitMessage("최대 7장까지 가능합니다.")
            .setRequestCode(100)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Util.requestStoragePermission(this)
        SystemUiController(window).setStatusBarColor(colors.primary)

        setContent {
            MaterialBind {
                ItemAddBind()
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
                    title = { Text("대여 상품 등록") },
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
                    Text("상품명")
                    RoundedTextField(
                        value = itemNameField,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 8.dp)
                    )
                    Text(text = "상품 상세 설명", modifier = Modifier.padding(top = 15.dp))
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
                        Text("대여 비용")
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
                        Text("대여 기한")
                        RoundedTextField(
                            value = itemRentLengthField,
                            keyboardType = KeyboardType.Number,
                            modifier = Modifier
                                .width(100.dp)
                                .padding(start = 10.dp)
                        )
                    }
                    Text(text = "사진 등록", modifier = Modifier.padding(top = 15.dp))
                    LazyRow(
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(150.dp)
                            .padding(top = 8.dp, bottom = 55.dp),
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
                                text = "임시저장",
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
                                    val id = UUID.randomUUID().toString().replace("-", "")
                                        .substring(0..10)

                                    selectedImages.forEach { selectedImage ->
                                        selectedImage?.run {
                                            storage.reference.child("items/$id/$name").putFile(uri)
                                        }
                                    }

                                    val item = Item(
                                        id = id,
                                        name = itemName,
                                        description = itemDescription,
                                        likeCount = 0,
                                        price = itemPrice.toLong(),
                                        rentLength = itemRentLength.toInt(),
                                        discountPercentage = 0,
                                        itemScope = 0,
                                        owner = vm.me,
                                        uploadDate = 0
                                    )
                                    firestore.collection("items").document(id).set(item)
                                    val newOwnItems = mutableListOf<String>().apply {
                                        addAll(vm.me.wrotePost)
                                        add(id)
                                    }
                                    vm.me = vm.me.copy(wrotePost = newOwnItems)
                                    DataUtil.userReUpload(vm.me, firestore)
                                    toast("등록 되었습니다.")
                                    finish()
                                } else {
                                    toast("모두 입력해 주세요.")
                                }
                            },
                            shape = RoundedCornerShape(15.dp),
                            modifier = Modifier
                                .weight(1f)
                                .height(50.dp)
                                .padding(start = 15.dp)
                        ) {
                            Text(
                                text = "등록하기",
                                style = typography.button,
                                color = Color.White
                            )
                        }
                    }
                }
            }
        }
    }
}
