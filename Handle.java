public class Handle<T> {
	
	int key;
	T value;
	int queuePos;	//for this i will be adding +1 to the its index value in the ArrayList (b/c the ArrayList starts at 0 not 1)
	//shoudl i put in vertex instead??
	public Handle(int inputKey, T inputValue, int inputPos) {
		key = inputKey;
		value = inputValue;
		queuePos = inputPos;
	}
	
	public void updatePos(int inputPos) {
		queuePos = inputPos;
	}

}

//how can i get this to work with T for value???