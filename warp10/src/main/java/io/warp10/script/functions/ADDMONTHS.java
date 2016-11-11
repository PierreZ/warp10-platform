//
//   Copyright 2016  Cityzen Data
//
//   Licensed under the Apache License, Version 2.0 (the "License");
//   you may not use this file except in compliance with the License.
//   You may obtain a copy of the License at
//
//     http://www.apache.org/licenses/LICENSE-2.0
//
//   Unless required by applicable law or agreed to in writing, software
//   distributed under the License is distributed on an "AS IS" BASIS,
//   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
//   See the License for the specific language governing permissions and
//   limitations under the License.
//

package io.warp10.script.functions;

import io.warp10.continuum.store.Constants;
import io.warp10.script.NamedWarpScriptFunction;
import io.warp10.script.WarpScriptException;
import io.warp10.script.WarpScriptStack;
import io.warp10.script.WarpScriptStackFunction;

import org.joda.time.DateTime;

/**
 * Adds a month to a timestamp
 */
public class ADDMONTHS extends NamedWarpScriptFunction implements WarpScriptStackFunction {
  
  public ADDMONTHS(String name) {
    super(name);
  }
  
  @Override
  public Object apply(WarpScriptStack stack) throws WarpScriptException {
  
    Object top = stack.pop();
    
    if (!(top instanceof Long)) {
      throw new WarpScriptException(getName() + " expects a number of months on top of the stack.");
    }
    
    int months = ((Number) top).intValue();
    
    top = stack.pop();
    
    if (!(top instanceof Long)) {
      throw new WarpScriptException(getName() + " operates on a timestamp expressed in time units.");
    }
    
    long instant = ((Number) top).longValue();
    
    DateTime dt = new DateTime(instant / Constants.TIME_UNITS_PER_MS);
    
    dt = dt.plusMonths(months);
    
    long ts = dt.getMillis() * Constants.TIME_UNITS_PER_MS + (instant % Constants.TIME_UNITS_PER_MS);
    
    stack.push(ts);
        
    return stack;
  }
}
