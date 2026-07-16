package br.com.arml.insights.model.source.migration

import androidx.room.Room
import androidx.room.testing.MigrationTestHelper
import androidx.sqlite.db.SimpleSQLiteQuery
import androidx.sqlite.db.framework.FrameworkSQLiteOpenHelperFactory
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import br.com.arml.insights.model.source.InsightsRoomDatabase
import com.google.common.truth.Truth.assertThat
import org.junit.After
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

private const val TEST_DB = "migration-test"

@RunWith(AndroidJUnit4::class)
class Migration2to3Test {

    private var db: InsightsRoomDatabase? = null

    @get:Rule
    val helper: MigrationTestHelper = MigrationTestHelper(
        InstrumentationRegistry.getInstrumentation(),
        InsightsRoomDatabase::class.java,
        emptyList(), // Nenhuma migração pré-carregada
        FrameworkSQLiteOpenHelperFactory()
    )

    @Test
    fun migrate2to3_shouldContainAllTablesAndData_whenMigrateFromVersionTwo() {
        // 1. Criar o banco na Versão 2 MANUALMENTE para ignorar a falta do '2.json'
        val context = InstrumentationRegistry
            .getInstrumentation()
            .targetContext

        // Limpa execuções anteriores
        context.deleteDatabase(TEST_DB)

        val dbV2 = context
            .openOrCreateDatabase(
                TEST_DB,
                0,
                null

            )
        dbV2.version = 2

        dbV2.execSQL(
            """
            CREATE TABLE IF NOT EXISTS tags_table (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                name TEXT NOT NULL,
                color INTEGER NOT NULL,
                description TEXT NOT NULL
            )
        """.trimIndent()
        )

        dbV2.execSQL(
            """
            CREATE TABLE IF NOT EXISTS notes_table (
                id INTEGER PRIMARY KEY AUTOINCREMENT NOT NULL,
                title TEXT NOT NULL,
                body TEXT NOT NULL,
                situation TEXT NOT NULL,
                creation_date INTEGER NOT NULL,
                tag_id INTEGER,
                FOREIGN KEY (tag_id) REFERENCES tags_table(id) ON DELETE CASCADE
            )
        """.trimIndent()
        )

        // Inserir dados de teste na V2
        dbV2.execSQL(
            """
            INSERT INTO tags_table (id, name, color, description) VALUES 
            (1, 'Tag Importante', -65536, 'Urgente')
            """.trimIndent()
        )

        dbV2.execSQL(
            """
            INSERT INTO notes_table (id, title, body, situation, creation_date, tag_id) VALUES 
            (50, 'Nota de Teste V2', 'Conteúdo', 'NORMAL', 1672531200000, 1)
            """.trimIndent()
        )

        dbV2.close()

        // 2. Rodar a migração para a Versão 3
        helper.runMigrationsAndValidate(
            TEST_DB,
            3,
            true,
            MIGRATION_2_3
        )

        // 3. Abrir o banco migrado para verificar integridade dos dados
        db = Room
            .databaseBuilder(
                context,
                InsightsRoomDatabase::class.java,
                TEST_DB
            )
            .addMigrations(MIGRATION_2_3)
            .build()

        // Verificação das tabelas migradas
        val tables = getTableNames()
        assertThat(tables).containsAtLeast("notes", "tags", "note_tag_links")

        // Verificação dos dados transformados
        db?.run {
            val query =
                SimpleSQLiteQuery("SELECT * FROM note_tag_links WHERE note_id = 50 AND tag_id = 1")
            query(query).use { cursor ->
                assertThat(cursor.moveToFirst()).isTrue()
            }
        }
    }

    @After
    fun tearDown() {
        db?.close()
    }

    private fun getTableNames(): List<String> {
        val query = SimpleSQLiteQuery("SELECT name FROM sqlite_master WHERE type='table'")
        return db?.run {
            query(query).use { cursor ->
                val names = mutableListOf<String>()
                while (cursor.moveToNext()) {
                    names.add(cursor.getString(0))
                }
                names
            }
        } ?: emptyList()
    }
}
