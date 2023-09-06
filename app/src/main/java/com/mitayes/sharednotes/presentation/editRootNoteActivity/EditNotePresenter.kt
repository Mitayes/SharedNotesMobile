package com.mitayes.sharednotes.presentation.editRootNoteActivity

import com.mitayes.sharednotes.data.api.APISyncAdapter
import com.mitayes.sharednotes.doIf
import com.mitayes.sharednotes.domain.ILocalDB
import com.mitayes.sharednotes.domain.ISyncServerAdapter
import com.mitayes.sharednotes.domain.RootNote
import com.mitayes.sharednotes.domain.sqlite.LocalDBSQLite
import com.mitayes.sharednotes.logE
import com.mitayes.sharednotes.presentation.MyApplication

class EditNotePresenter(
    private val view: IEditNoteActivity,
) : IEditNotePresenter {
    private val localDB: ILocalDB by lazy { LocalDBSQLite() }
    private val syncAdapter: ISyncServerAdapter by lazy { APISyncAdapter() }
    private val bag = MyApplication.bag

    override fun saveNewNote(newNote: RootNote) {
        doIf(newNote.name.isNotBlank() || newNote.description.isNotBlank()) {
            bag.add(
                localDB.addNote(newNote)
                    .andThen(syncAdapter.addNote(newNote))
                    .andThen(localDB.updateSyncFlag(newNote.uuid, 1))
                    .doOnError {
                        logE(it.stackTraceToString())
                    }
                    .subscribe {
                        view.complete()
                    }
            )
        }
    }

    override fun editNote(position: Int, note: RootNote) {
        bag.add(
            localDB.editNote(note)
                .andThen(syncAdapter.editNote(note))
                .andThen(localDB.updateSyncFlag(note.uuid, 1))
                .doOnError {
                    logE(it.stackTraceToString())
                }
                .subscribe {
                    view.complete()
                }
        )
    }

    override fun onDestroy() {
//        bag.clear()
    }
}