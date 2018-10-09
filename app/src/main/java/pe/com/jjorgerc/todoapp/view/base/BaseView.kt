package pe.com.jjorgerc.todoapp.view.base

import android.content.Context

interface BaseView {
    fun init()
    fun getContextActivity(): Context
}