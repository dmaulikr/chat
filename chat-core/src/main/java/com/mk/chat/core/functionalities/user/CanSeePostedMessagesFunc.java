package com.mk.chat.core.functionalities.user;

import com.mk.chat.core.data.MessageData;

import java.util.function.Consumer;

/**
 * Created by mladen on 9/23/17.
 */

public class CanSeePostedMessagesFunc {

    Binding binding;
    MessagesStream messagesStream;

    protected class Binding {

    }

    public interface MessagesStream {
        void subscribe_messagePosted(Consumer<MessageData> subscriber);
        void subscribe_messageEdited(Consumer<MessageData> subscriber);
    }

    public interface View {
        void setMessage(MessageData message);
    }
}
