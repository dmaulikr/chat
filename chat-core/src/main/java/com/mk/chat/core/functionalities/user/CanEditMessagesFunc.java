package com.mk.chat.core.functionalities.user;

import com.mk.chat.core.data.MessageData;

import java.util.function.Consumer;

/**
 * Created by mladen on 9/24/17.
 */

public class CanEditMessagesFunc {

    Binding binding;
    MessagesStream messagesStream;
    UserEventsSource view;

    protected class Binding {

    }

    public interface MessagesStream {
        void put(MessageData messageData, Runnable onComplete, Runnable onFailure);
    }

    public interface UserEventsSource {
        void subscribe_WantsToEdit(Consumer<MessageData> subscriber);
    }
}
