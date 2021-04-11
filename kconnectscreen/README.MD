# Экран для подключения к роботу
##Настройка и подключения библеотеки
1. Требуется чтоб также был подключен модуль KControlLibrary
3. Добавть permissions
   <uses-permission android:name="android.permission.INTERNET"/>
   <uses-permission android:name="android.permission.ACCESS_WIFI_STATE"/>
   <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
   <uses-permission android:name="android.permission.VIBRATE" />
2. Рекомендуется взять по ссылке два класса. Они являются оберткой класса KRobot, 
   оптимизированные под жизенный цикл.
2. Рассмотрим параметры: 
    * onBack - навигация того, что произойдет при отмене подключения
    * onConnect - то, что произойдет при подключении 
    * connectStatus - статус подключения к роботу
    * onIpChange - что произойдет при изменении Ip требуется, чтобы сохранить новый Ip
    * defaultIp - ip, который будет изначально, при запуске экрана
