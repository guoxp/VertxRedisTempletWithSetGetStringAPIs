package com.yizhao;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;

import org.apache.commons.io.IOUtils;
import org.vertx.java.core.Handler;
import org.vertx.java.core.Vertx;
import org.vertx.java.core.buffer.Buffer;
import org.vertx.java.core.http.HttpServerFileUpload;
import org.vertx.java.core.http.HttpServerRequest;
import org.vertx.java.core.json.JsonObject;

import redis.clients.jedis.Jedis;

public class SetStringAPI extends PingVerticle {
	public SetStringAPI() {

	}

	public void setString(final Vertx vertx, final HttpServerRequest bridge_between_server_and_client) {
		// Connecting to Redis on localhost
		Jedis jedis = new Jedis("localhost");
		JsonObject response = new JsonObject();
		response.putString("status", "0");
		response.putString("result", jedis.set(bridge_between_server_and_client.params().get("key"), bridge_between_server_and_client.params().get("value")));
		bridge_between_server_and_client.response().end(response.encodePrettily());
	}
}
