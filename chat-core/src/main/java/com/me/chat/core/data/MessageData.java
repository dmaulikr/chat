package com.me.chat.core.data;


import java.util.Collection;

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

	public boolean isOnline(){
		throw new UnsupportedOperationException();
	}

	public Collection<UserData> getUsersThatHaveSeenIt(){ throw new UnsupportedOperationException(); }
}
