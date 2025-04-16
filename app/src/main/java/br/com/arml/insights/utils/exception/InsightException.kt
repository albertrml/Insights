package br.com.arml.insights.utils.exception

sealed class InsightException(override val message: String): Exception() {
    class TagAlreadyExistsException: InsightException( "Tag Name already exists" )
    class TagNotFoundException: InsightException( "Tag not found" )
    class NoteNotFoundException: InsightException( "Note not found" )
}

sealed class TagException(override val message: String): Exception() {
    class TagIsNullException: TagException( "Tag can't be null" )
    class TagNameSizeException: TagException( "Tag name must be between 3 and 20 characters" )
}