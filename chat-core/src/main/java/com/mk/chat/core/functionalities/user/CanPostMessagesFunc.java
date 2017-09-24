package com.mk.chat.core.functionalities.user;

import com.mk.chat.core.data.MessageData;

import java.util.function.Consumer;

/**
 * Created by mladen on 9/23/17.
 */

class CanPostMessagesFunc {

    Binding binding;
    MessagesOutStream messagesStream;
    UserEventsSource view;

    protected class Binding {

    }

    public interface MessagesOutStream {
        void put(MessageData messageData, Runnable onComplete, Runnable onFailure);
    }

    public interface UserEventsSource {
        void subscribe_WantsToPost(Consumer<MessageData> subscriber);
    }
}
