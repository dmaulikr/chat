package com.mk.chat.core.data_containers;

import com.mk.chat.core.data.MessageData;

import java.util.function.Consumer;

public interface MessagesCloudStore {
    void subscribe_NewMessage(Consumer<MessageData> subscriber);
    void subscribe_MessageEdited(Consumer<MessageData> subscriber);
    void subscribe_MessageDeleted(Consumer<MessageData> subscriber);
    void persist(MessageData messageData, Runnable onComplete, Runnable onFailure);
    void getAllMessages(Consumer<MessageData> onComplete, Consumer<MessageData> onFailure);
}
