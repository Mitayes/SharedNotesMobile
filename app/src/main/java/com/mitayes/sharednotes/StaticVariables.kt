package com.mitayes.sharednotes

class StaticVariables {
    companion object {
        // Настройки для работы с сервером синхронизации заметок
        const val SYNC_SERVER_URL: String = "http://192.168.2.10:8443"
        const val SYNC_SERVER_TEST_ROUTE: String = "/api/note/detail/"
        const val SYNC_SERVER_USER_ROUTE: String = "/api/user/"
        const val SYNC_SERVER_NOTE_ROUTE: String = "/api/note/"

        const val SYNC_SERVER_USER_UUID: String = "32747ccd-add1-47d0-8a8f-ae864e2ed9f3"

    }
}