StartStopNotificationService
============================

This application contains two Activities and one service :

1/ ServiceLuncherActivity : 
is the first lunched activity which contains a button for starting and stopping the service.
The activity is catching the state changing of the service (starting/stopping) in order to update the button definition.

2/ NotificationCreatorService : 
is the service managed by the ServiceLuncherActivity.
It shows a notification every starting (just an icon).
It sends an intent every changing state  (starting/stopping) to the ServiceLuncherActivity.

3/ NotifiedActivity : 
is the activity lunched when the shown notification is clicked.
It contains a button to navigate to the ServiceLuncherActivity.
