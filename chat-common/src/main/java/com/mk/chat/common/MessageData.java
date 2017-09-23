package com.mk.chat.common;

import java.util.function.Consumer;

/**
 * Created by mladen on 9/20/17.
 */

public class MessageData<T> {

	public UserData getUser() {
		throw new UnsupportedOperationException();
	}

	public T getContent(){
		throw new UnsupportedOperationException();
	}

	public boolean shouldShowToUser() {
		throw new UnsupportedOperationException();
	}

	public void shouldShowToUser(boolean parameter) {
		throw new UnsupportedOperationException();
	}

	public boolean isInDatabase() {
		throw new UnsupportedOperationException();
	}

	public void isInDatabase(boolean param) {
		throw new UnsupportedOperationException();
	}
	
	public void subscribe(java.util.function.Consumer<MessageData> subscriber) {
		throw new UnsupportedOperationException();
	}
}
