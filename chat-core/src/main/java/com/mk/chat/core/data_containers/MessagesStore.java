package com.mk.chat.core.data_containers;

import com.mk.chat.core.data.MessageData;

import java.util.Collection;
import java.util.HashSet;

/**
 * Created by mladen on 9/23/17.
 */

public class MessagesStore {

    Collection<MessageData> messages = new HashSet<>();
    MessagesCloudStore cloudStore;
}


