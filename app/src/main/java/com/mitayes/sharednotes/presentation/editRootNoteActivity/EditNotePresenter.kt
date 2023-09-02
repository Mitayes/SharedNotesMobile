package com.mitayes.sharednotes.presentation.editRootNoteActivity

import com.mitayes.sharednotes.data.sqlite.LocalDBSQLite
import com.mitayes.sharednotes.doIf
import com.mitayes.sharednotes.domain.ILocalDB
import com.mitayes.sharednotes.domain.RootNote
import com.mitayes.sharednotes.logE
import io.reactivex.disposables.CompositeDisposable

class EditNotePresenter(
    private val view: IEditNoteActivity,
) : IEditNotePresenter {
    private val localDB: ILocalDB by lazy { LocalDBSQLite() }
    private val bag = CompositeDisposable()

    override fun saveNewNote(newNote: RootNote) {
        doIf(newNote.name.isNotBlank() || newNote.description.isNotBlank()) {
            bag.add(localDB.addNote(newNote)
                .subscribe(
                    {
                        view.complete()
                    },
                    {
                        logE(it.stackTraceToString())
                    }
                )
            )
        }
    }

    override fun editNote(position: Int, note: RootNote) {
        bag.add(localDB.editNote(note)
            .subscribe(
                {
                    view.complete()
                },
                {
                    logE(it.stackTraceToString())
                }
            )
        )
    }
}