package com.goodluck.event

class PublishEvent(action:Int) :BaseActionEvent(action){

    companion object{
        const val PUBLISH_SUCCESS=100
    }
}