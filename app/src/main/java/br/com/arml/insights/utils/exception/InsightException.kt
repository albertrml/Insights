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

sealed class NoteException(override val message: String): Exception() {
    class NoteIsNullException: NoteException( "Note can't be null" )
    class NoteTagIdException: NoteException( "Note tag id can't be negative" )
    class NoteTitleSizeException: NoteException( "Note title must be between 3 and 20 characters" )
    class NoteBodySizeException: NoteException( "Note body must be between 0 and 1000 characters" )
    class NoteSituationSizeException: NoteException( "Note situation must be between 3 and 20 characters" )
}