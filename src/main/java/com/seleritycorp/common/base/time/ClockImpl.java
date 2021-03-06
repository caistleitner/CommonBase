/*
 * Copyright (C) 2016 Selerity, Inc. (support@seleritycorp.com)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 * http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.seleritycorp.common.base.time;

public class ClockImpl implements Clock {
  long nanoOffsetEpoch;

  public ClockImpl() {
    nanoOffsetEpoch = getMillisEpoch() * 1000000L - System.nanoTime();
  }

  @Override
  public long getSecondsEpoch() {
    return getMillisEpoch() / 1000;
  }

  @Override
  public long getMillisEpoch() {
    return System.currentTimeMillis();
  }

  @Override
  public long getNanosEpoch() {
    return nanoOffsetEpoch + System.nanoTime();
  }
}
