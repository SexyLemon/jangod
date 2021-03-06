/**********************************************************************
Copyright (c) 2010 Asfun Net.

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
package net.asfun.jangod.tree;

import static net.asfun.jangod.util.logging.JangodLogger;

import java.util.List;

import net.asfun.jangod.interpret.InterpretException;
import net.asfun.jangod.interpret.JangodInterpreter;
import net.asfun.jangod.lib.Filter;
import net.asfun.jangod.lib.FilterLibrary;
import net.asfun.jangod.parse.EchoToken;
import net.asfun.jangod.util.ObjectValue;

public class VariableNode extends Node{

	private static final long serialVersionUID = 341642231109911346L;
	private EchoToken master;
	static final String name = "Variable_Node";
	
	public VariableNode(EchoToken token) {
		super();
		master = token;
	}

	@Override
	public String render(JangodInterpreter interpreter)
			throws InterpretException {
		interpreter.setLevel(level);
		Object var = interpreter.retraceVariable(master.getVariable());
		//filters
		List<String> filters = master.getFilters();
		if ( filters.isEmpty() ) {
			return ObjectValue.printable(var);
		}
		List<String[]> argss = master.getArgss();
		String[] args;
		Filter filter;
		for(int i=0; i<filters.size(); i++) {
			filter = FilterLibrary.getFilter(filters.get(i));
			if ( filter == null ) {
				JangodLogger.warning("skipping an unregistered filter >>> " + filters.get(i));
				continue;
			}
			args = argss.get(i);
			if ( args == null ) {
				var = filter.filter(var, interpreter);
			} else {
				var = filter.filter(var, interpreter, args);
			}
		}
		return ObjectValue.printable(var);
	}

	@Override
	public String toString() {
		return master.toString();
	}
	
	@Override
	public String getName() {
		return name;
	}
	
	@Override
	public Node clone() {
		Node clone = new VariableNode(master);
		clone.children = this.children.clone(clone);
		return clone;
	}
}
