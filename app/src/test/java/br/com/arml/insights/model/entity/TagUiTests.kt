package br.com.arml.insights.model.entity

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import br.com.arml.insights.utils.exception.TagException
import org.junit.Assert.assertEquals
import org.junit.Assert.assertFalse
import org.junit.Assert.assertNull
import org.junit.Assert.assertTrue
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
        assertTrue(result.first)
        assertNull(result.second)
    }

    @Test
    fun `should return false and TagIsNullException when TagUi is null`(){
        val result = TagUi.isValid(null)
        assertFalse(result.first)
        assertTrue(result.second is TagException.TagIsNullException)
    }

    @Test
    fun `should return false and TagNameSizeException when name length is not in 3 to 20`(){
        val result = TagUi.isValid(
            validTagUi.copy(
                name = "a".repeat(21)
            )
        )
        assertFalse(result.first)
        assertTrue(result.second is TagException.TagNameSizeException)
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