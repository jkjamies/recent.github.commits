package com.example.android.recentgithubcommits.util

import com.example.android.recentgithubcommits.models.Author
import com.example.android.recentgithubcommits.models.Commit
import com.example.android.recentgithubcommits.models.CommitObject
import java.util.*

internal val commit1 = CommitObject(
    "url1",
    "sha1",
    Commit(
        Author(
            "name1",
            "email1@email1.com",
            Date(1302796849000)
        ),
        "message1"
    )
)
internal val commit2 = CommitObject(
    "url2",
    "sha2",
    Commit(
        Author(
            "name2",
            "email2@email2.com",
            Date(1302796849000)
        ),
        "message2"
    )
)
internal val commit3 = CommitObject(
    "url3",
    "sha3",
    Commit(
        Author(
            "name3",
            "email3@email3.com",
            Date(1302796849000)
        ),
        "message3"
    )
)