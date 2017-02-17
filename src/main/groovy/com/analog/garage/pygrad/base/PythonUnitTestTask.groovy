/**************************************************************************
* Copyright 2017 Analog Devices Inc.
*
*   Licensed under the Apache License, Version 2.0 (the "License");
*   you may not use this file except in compliance with the License.
*   You may obtain a copy of the License at
*
*       http://www.apache.org/licenses/LICENSE-2.0
*
*   Unless required by applicable law or agreed to in writing, software
*   distributed under the License is distributed on an "AS IS" BASIS,
*   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
*   See the License for the specific language governing permissions and
*   limitations under the License.
***************************************************************************/

package com.analog.garage.pygrad.base

import static com.analog.garage.pygrad.base.LazyPropertyUtils.*

import org.gradle.api.*
import org.gradle.api.tasks.*

import com.analog.garage.pygrad.base.PythonExeTaskBase

/**
 * @author Christopher Barber
 */
class PythonUnitTestTask extends PythonExeTaskBase {

	// --- testArgs ---
	
	private List<Object> _testArgs = 
		['-m', 'unittest', 'discover', '-s', { -> testDir}]
		
	@Input
	List<String> getTestArgs() { stringifyList(_testArgs) }
	void setTestArgs(Object ... args) {
		_testArgs.clear()
		addToListFromVarargs(_testArgs, args)
	}
	void testArgs(Object first, Object ... additional) {
		addToListFromVarargs1(_testArgs, first, additional)
	}
	
	// --- testDir ---
	
	private Object _testDir = "$project.rootDir"
	
	/**
	 * Root directory for discovering Python unit tests
	 * <p>
	 * Defaults to project's {@link Project.getRootDir rootDir}
	 */
	@InputDirectory
	File getTestDir() { project.file(_testDir) }
	void setTestDir(Object path) { _testDir = path }
	
	PythonUnitTestTask() {
		description = 'Run python unit tests'
	}
	
	@TaskAction
	runTests() {
		project.exec {
			executable = pythonExe
			args = testArgs
		}
	}

}
