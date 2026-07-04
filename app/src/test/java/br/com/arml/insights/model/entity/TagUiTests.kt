package br.com.arml.insights.model.entity

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import br.com.arml.insights.utils.exception.TagException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertThrows
import org.junit.Test

class TagUiTests {

    val cleanTagUi = TagUi(
        id = 0,
        name = "",
        color = Color.Black,
        description = ""
    )

    val validTagUi = TagUi(
        id = 1,
        name = "Valid Name",
        color = Color.White,
        description = "valid description"
    )

    val validTag = Tag(
        id = 1,
        name = "Valid Name",
        color = Color.White.toArgb().toLong(),
        description = "valid description"
    )

    @Test
    fun `should return true and null when TagUi is valid`(){
        val result = TagUi.isValid(validTagUi)
        assertEquals(Unit, result)
    }

    @Test
    fun `should return false and TagIsNullException when TagUi is null`(){
        assertThrows(TagException.TagIsNullException::class.java) {
            TagUi.isValid(null)
        }
    }

    @Test
    fun `should return false and TagNameSizeException when name length is not in 3 to 20`(){
        assertThrows(TagException.TagNameSizeException::class.java) {
            TagUi.isValid(validTagUi.copy(name = "a".repeat(MIN_TAG_NAME_LENGTH - 1)))
        }
        assertThrows(TagException.TagNameSizeException::class.java) {
            TagUi.isValid(validTagUi.copy(name = "a".repeat(MAX_TAG_NAME_LENGTH + 1)))
        }
    }

    @Test
    fun `should return Tag from TagUi`(){
        val newTag = validTagUi.toTag()
        assertEquals(validTag, newTag)
    }

    @Test
    fun `should return TagUi from Tag`(){
        val newTagUi = TagUi.fromTag(validTag)
        assertEquals(validTagUi, newTagUi)
    }

    @Test
    fun `should return cleanTagUi when TagUi is null`(){
        val newTagUi = TagUi.fromTag(null)
        assertEquals(cleanTagUi, newTagUi)
    }

}