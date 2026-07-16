package br.com.arml.insights.model.source.migration

import androidx.room.migration.Migration
import androidx.sqlite.db.SupportSQLiteDatabase

val MIGRATION_2_3 = object : Migration(2, 3) {
    override fun migrate(db: SupportSQLiteDatabase) {
        // 1. Criar as novas tabelas
        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS notes (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                body TEXT NOT NULL,
                situation TEXT NOT NULL,
                creation_date INTEGER NOT NULL
            )
        """.trimIndent())

        db.execSQL("CREATE INDEX IF NOT EXISTS index_notes_id ON notes (id)")

        db.execSQL("CREATE INDEX IF NOT EXISTS index_notes_creation_date ON notes (creation_date)")

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS tags (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL,
                color INTEGER NOT NULL,
                description TEXT NOT NULL
            )
        """.trimIndent())

        db.execSQL("CREATE INDEX IF NOT EXISTS index_tags_name ON tags (name)")

        db.execSQL(
            """
            CREATE TABLE IF NOT EXISTS note_tag_links (
                note_id INTEGER NOT NULL,
                tag_id INTEGER NOT NULL,
                PRIMARY KEY(note_id, tag_id),
                FOREIGN KEY(note_id) REFERENCES notes(id) ON UPDATE NO ACTION ON DELETE CASCADE,
                FOREIGN KEY(tag_id) REFERENCES tags(id) ON UPDATE NO ACTION ON DELETE CASCADE
            )
        """.trimIndent())

        db.execSQL(
            """
            CREATE INDEX IF NOT EXISTS index_note_tag_links_note_id ON note_tag_links (note_id)
        """.trimIndent())

        db.execSQL(
            """
            CREATE INDEX IF NOT EXISTS index_note_tag_links_tag_id ON note_tag_links (tag_id)
        """.trimMargin())

        // 2. Migrar os dados de forma eficiente (SQL-to-SQL)
        // O Room já inicia uma transação automaticamente antes de chamar migrate()

        // Migrar Notas
        db.execSQL(
            """
            INSERT INTO notes (id, title, body, situation, creation_date)
            SELECT id, title, body, situation, creation_date FROM notes_table
        """.trimIndent()
        )

        // Migrar Tags (evitando duplicatas caso existam na tabela antiga)
        db.execSQL(
            """
            INSERT INTO tags (id, name, color, description)
            SELECT DISTINCT id, name, color, description FROM tags_table
        """.trimIndent()
        )

        // Migrar os vínculos N:N (Note -> Tag)
        // Assume-se que na Versão 1, notes_table tinha uma coluna 'tag_id'
        db.execSQL(
            """
            INSERT INTO note_tag_links (note_id, tag_id)
            SELECT id, tag_id FROM notes_table 
            WHERE tag_id IS NOT NULL AND tag_id IN (SELECT id FROM tags)
        """.trimIndent()
        )

        // 3. Remover tabelas antigas
        db.execSQL("DROP TABLE IF EXISTS notes_table")
        db.execSQL("DROP TABLE IF EXISTS tags_table")
    }
}