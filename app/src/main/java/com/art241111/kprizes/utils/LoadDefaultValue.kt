package com.art241111.kprizes.utils

import com.art241111.kcontrolsystem.data.DELAY_SEND_SP
import com.art241111.kcontrolsystem.data.LONG_MOVE_SP
import com.art241111.kcontrolsystem.data.SHORT_MOVE_SP
import com.art241111.kprizes.data.robot.ROBOT_POSITION_ANGLE
import com.art241111.kprizes.data.robot.RobotVM
import com.art241111.kprizes.ui.settingScreen.addPoints.EditPoints
import com.art241111.saveandloadinformation.sharedPreferences.SharedPreferencesHelperForString
import com.github.poluka.kControlLibrary.enity.position.Point
import com.github.poluka.kControlLibrary.enity.position.toPoint

/**
 * @author Created by Artem Gerasimov (gerasimov.av.dev@gmail.com).
 */

object LoadDefaultValue {
    fun load(robotVM: RobotVM, sharedPreferences: SharedPreferencesHelperForString) {
        robotVM.delaySending.value =
            sharedPreferences.load(DELAY_SEND_SP, 70L.toString()).toLong()

        robotVM.defaultButtonDistanceLong =
            sharedPreferences.load(LONG_MOVE_SP, 10.0.toString()).toDouble()

        robotVM.defaultButtonDistanceShort =
            sharedPreferences.load(SHORT_MOVE_SP, 1.0.toString()).toDouble()

//        robotVM.homePoint =
//            sharedPreferences.load(EditPoints.HOME_POINT.name, Point().toString()).toPoint()
        robotVM.setPoint =
            sharedPreferences.load(EditPoints.SET_POINT.name, Point().toString()).toPoint()

        robotVM.firstPoint =
            sharedPreferences.load(EditPoints.FIRST_POINT.name, Point().toString()).toPoint()
        robotVM.secondPoint =
            sharedPreferences.load(EditPoints.SECOND_POINT.name, Point().toString()).toPoint()

        robotVM.robotPositionAngle =
            sharedPreferences.load(ROBOT_POSITION_ANGLE, 0.0.toString()).toDouble()
    }
}
