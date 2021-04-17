package com.example.aimsapp.database.tripDatabase.relations

import androidx.room.Embedded
import androidx.room.Relation
import com.example.aimsapp.database.tripDatabase.Account
import com.example.aimsapp.database.tripDatabase.Driver

data class DriverWithAccounts(
    @Embedded val driver: Driver,

    @Relation(
        parentColumn = "driverCode",
        entityColumn = "driverCode"
    )
    val accounts: List<Account>
)