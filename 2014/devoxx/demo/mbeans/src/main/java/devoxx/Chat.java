/*
 * Copyright 2009-2013 Roland Huss
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *       http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package devoxx;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import javax.management.*;

public class Chat extends NotificationBroadcasterSupport implements ChatMBean  {

    private AtomicInteger seqNumber = new AtomicInteger();

    public Chat() throws Exception {
        super(new MBeanNotificationInfo(new String[] {"devoxx.chat"}, Notification.class.getName(),
                                        "Chat notification"));
    }

    // Broadcast message to all listeners. This method is exposed via JMX
    public void message(String user, String message) {
        Notification notification =
                new Notification("devoxx.chat", this,
                                 seqNumber.getAndIncrement());

        // Payload
        Map<String,String> data = new HashMap<String,String>();
        data.put("user",user);
        data.put("message",message);
        notification.setUserData(data);

        sendNotification(notification);
    }
}
