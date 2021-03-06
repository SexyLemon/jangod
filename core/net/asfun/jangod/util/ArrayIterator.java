/**********************************************************************
Copyright (c) 2009 Asfun Net.

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
**********************************************************************/
package net.asfun.jangod.util;

import java.lang.reflect.Array;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class ArrayIterator implements Iterator<Object>{
	
	private Object array;
	private int length;
	private int cursor;

	public ArrayIterator(Object obj) {
		array = obj;
		length = Array.getLength(obj);
		cursor = 0;
	}

	@Override
	public boolean hasNext() {
		return cursor < length;
	}

	@Override
	public Object next() {
		if ( cursor >= length ) {
			throw new NoSuchElementException();
		}
		return Array.get(array, cursor++);
	}

	@Override
	public void remove() {
		throw new UnsupportedOperationException();
	}

}
