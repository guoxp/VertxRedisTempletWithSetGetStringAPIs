package com.yizhao;

/*
 * Copyright 2013 Red Hat, Inc.
 *
 * Red Hat licenses this file to you under the Apache License, version 2.0
 * (the "License"); you may not use this file except in compliance with the
 * License.  You may obtain a copy of the License at:
 *
 *    http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.  See the
 * License for the specific language governing permissions and limitations
 * under the License.
 *
 * @author <a href="http://tfox.org">Tim Fox</a>
 */

import java.io.IOException;
import java.util.Arrays;

import org.vertx.java.core.Handler;
import org.vertx.java.core.eventbus.Message;
import org.vertx.java.core.http.HttpServer;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.http.RouteMatcher;
import org.vertx.java.core.json.JsonObject;
import org.vertx.java.platform.Verticle;

/*
 This is a simple Java verticle which receives `ping` messages on the event bus and sends back `pong` replies
 */
public class MainVerticle extends Verticle {
	SetStringAPI mUploadBinaryDataAPI;
	GetStringAPI mDownloadBinaryDatAPI;

	private void init() {
		// vertx.eventBus().registerHandler("ping-address",
		// new Handler<Message<String>>() {
		// @Override
		// public void handle(Message<String> message) {
		// message.reply("pong!");
		// container.logger().info("Sent back pong");
		// }
		// });
		container.deployVerticle(MainVerticle.class.getCanonicalName(), 1);
		// container.logger().info("PingVerticle started");
		// vertx.createHttpServer()
		// .requestHandler(new Handler<HttpServerRequest>() {
		// public void handle(HttpServerRequest req) {
		// String file = req.path().equals("/") ? "index.html"
		// : req.path();
		// req.response().sendFile("webroot/" + file);
		// }
		// }).listen(8080);

	}

	public void start() {
		init();
		RouteMatcher httpRouteMatcher = new RouteMatcher();
		HttpServer httpServer = vertx.createHttpServer();
		httpServer.requestHandler(httpRouteMatcher);
		httpServer.listen(8080, "0.0.0.0");

		// curl -v -X POST http://localhost:8080/setstring/apple/mouse
		httpRouteMatcher.post("/setstring/:key/:value", new Handler<HttpServerRequest>() {
			@Override
			public void handle(final HttpServerRequest bridge_between_server_and_client) {
				mUploadBinaryDataAPI = new SetStringAPI();
				container.logger().info("mUploadBinaryDataAPI");
				mUploadBinaryDataAPI.setString(vertx, bridge_between_server_and_client);

			}
		});

		// curl -v -X GET http://localhost:8080/getstring/apple
		httpRouteMatcher.get("/getstring/:key", new Handler<HttpServerRequest>() {
			@Override
			public void handle(final HttpServerRequest bridge_between_server_and_client) {
				container.logger().info("mDownloadBinaryDatAPI");
				mDownloadBinaryDatAPI = new GetStringAPI();
				mDownloadBinaryDatAPI.getString(vertx, bridge_between_server_and_client);
			}
		});

	}
}
