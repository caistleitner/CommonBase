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

package com.seleritycorp.common.base.coreservices;

import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import com.seleritycorp.common.base.config.ApplicationConfig;
import com.seleritycorp.common.base.config.Config;
import com.seleritycorp.common.base.config.ConfigUtils;

import java.io.IOException;
import javax.inject.Inject;
import javax.inject.Singleton;

@Singleton
public class AuthenticationClient {
  private RawCoreServiceClient client;
  private final String user;
  private final String password;

  /**
   * Creates a CoreService client for the AuthenticationHandler.
   * 
   * @param appConfig The application config to use
   * @param client The client to use for CoreServices calls.
   */
  @Inject
  public AuthenticationClient(@ApplicationConfig Config appConfig, RawCoreServiceClient client) {
    this.client = client;

    Config config = ConfigUtils.subconfig(appConfig, "CoreServices");
    this.user = config.get("user");
    this.password = config.get("password");
  }

  /**
   * Authenticates the user and yields the authentication token.
   *
   * <p>User and password for the authentication are taken from the application's config.
   * 
   * @return the authentication token
   * @throws IOException for network or other IO issues occur..
   * @throws CallErrorException for server and semantics errors.
   */
  public String getAuthThoken() throws IOException, CallErrorException {
    final JsonObject params = new JsonObject();
    params.addProperty("user", user);
    params.addProperty("password", password);

    final JsonElement result = client.call("AuthenticationHandler.authenticate", params, 10000);

    final String token;
    try {
      token = result.getAsJsonObject().get("id").getAsString();
    } catch (NullPointerException e) {
      throw new CallErrorException("Failed to extract token id from response", e);
    }
    return token;
  }
}
