package com.mitayes.sharednotes.domain

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import com.mitayes.sharednotes.data.sqlite.ROOT_NOTES_TABLE_NAME
import com.mitayes.sharednotes.data.sqlite.RootNoteTuple
import com.mitayes.sharednotes.data.sqlite.RootNotesDBEntity
import io.reactivex.Completable
import io.reactivex.Single

@Dao
interface RootNotesDao {
    @Query(
        "SELECT " +
            "uuid " +
            ", name" +
            ", description" +
            ", shared " +
            ", is_owner " +
            ", update_date " +
        "FROM $ROOT_NOTES_TABLE_NAME" +
        ";")
    fun getAllRootNotes(): Single<MutableList<RootNoteTuple>>

    @Query(
        "SELECT " +
                "uuid " +
                ", name" +
                ", description" +
                ", shared " +
                ", is_owner " +
                ", update_date " +
                "FROM $ROOT_NOTES_TABLE_NAME " +
                "WHERE uuid = :uuid ;")
    fun getRootNote(uuid: String): Single<MutableList<RootNoteTuple>>

    @Insert(entity = RootNotesDBEntity::class, onConflict = OnConflictStrategy.REPLACE)
    fun insertRootNote(note: RootNotesDBEntity) : Completable

    @Update(entity = RootNotesDBEntity::class)
    fun updateRootNote(note: RootNotesDBEntity) : Completable

    @Delete(entity = RootNotesDBEntity::class)
    fun deleteRootNote(note: RootNotesDBEntity) : Completable
}