package com.mohnage7.swvl.presentation.extentions

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.FragmentManager
import androidx.fragment.app.FragmentTransaction

fun AppCompatActivity.replaceFragment(fragment: androidx.fragment.app.Fragment, frameId: Int, addToBackStack: Boolean = true) {

    supportFragmentManager.inTransaction {
        val transaction = replace(frameId, fragment)

        if (addToBackStack) {
            transaction.addToBackStack(fragment::javaClass.name)
        }

        transaction
    }
}

inline fun FragmentManager.inTransaction(func: FragmentTransaction.() -> FragmentTransaction) {
    beginTransaction().func().commit()
}